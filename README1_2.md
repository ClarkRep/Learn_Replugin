# 从启动插件Activity的角度来分析Replugin的核心：Hook ClassLoader
Replugin的核心点就是Hook住了宿主的ClassLoader，它会使用自己的 RePluginClassLoader 替换了宿主的 ClassLoader，在loadClass的时候，就会优先去取插件里面的Class，当插件里没有找到该Class的时候，则会调用原来的ClassLoader取加载Class。  
这里会用宿主启动插件Activity的流程，来分析一下Hook ClassLoader的具体原理。  
带着下面的问题，我们来看一下replugin-host-library 的源码：
* 宿主是如何 Hook ClassLoader？
* 宿主在 Manifest 注册的 坑位Activity 和 插件的Activity 如何绑定的？
* 启动 插件Activity 的时候，宿主 RePluginClassLoader 怎么将 坑位Activity 替换成 插件Activity 进行展示？

## Hook 宿主 ClassLoader
在宿主继承 Application 的 attachBaseContext()方法，我们需要添加 Replugin 的注入：
```
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePluginConfig rePluginConfig = new RePluginConfig();
        rePluginConfig.setUseHostClassIfNotFound(true);
        RePlugin.App.attachBaseContext(this, rePluginConfig);
    }
```
我们进入 RePlugin.App.attachBaseContext() 方法看看里面做了什么事情：

```
        public static void attachBaseContext(Application app, RePluginConfig config) {
        
            ...
        
            //看到这个方法么，点进去
            PMF.init(app);
            
            ...
        }

```

再到 PMF.init() 方法里面看一下：
```
    /**
     * @param application
     */
    public static final void init(Application application) {
        
        ...
        
        //看到这个方法了么，再点进去
        PatchClassLoaderUtils.patch(application);
    }

```

最后来到 PatchClassLoaderUtils.patch()：
```
    public static boolean patch(Application application) {
        try {
            // 获取Application的BaseContext （来自ContextWrapper）
            Context oBase = application.getBaseContext();
            if (oBase == null) {
                if (LOGR) {
                    LogRelease.e(PLUGIN_TAG, "pclu.p: nf mb. ap cl=" + application.getClass());
                }
                return false;
            }

            // 获取mBase.mPackageInfo
            // 1. ApplicationContext - Android 2.1
            // 2. ContextImpl - Android 2.2 and higher
            // 3. AppContextImpl - Android 2.2 and higher
            Object oPackageInfo = ReflectUtils.readField(oBase, "mPackageInfo");
            if (oPackageInfo == null) {
                if (LOGR) {
                    LogRelease.e(PLUGIN_TAG, "pclu.p: nf mpi. mb cl=" + oBase.getClass());
                }
                return false;
            }

            // mPackageInfo的类型主要有两种：
            // 1. android.app.ActivityThread$PackageInfo - Android 2.1 - 2.3
            // 2. android.app.LoadedApk - Android 2.3.3 and higher
            if (LOG) {
                Log.d(TAG, "patch: mBase cl=" + oBase.getClass() + "; mPackageInfo cl=" + oPackageInfo.getClass());
            }

            // 获取mPackageInfo.mClassLoader
            ClassLoader oClassLoader = (ClassLoader) ReflectUtils.readField(oPackageInfo, "mClassLoader");
            if (oClassLoader == null) {
                if (LOGR) {
                    LogRelease.e(PLUGIN_TAG, "pclu.p: nf mpi. mb cl=" + oBase.getClass() + "; mpi cl=" + oPackageInfo.getClass());
                }
                return false;
            }

            // 外界可自定义ClassLoader的实现，但一定要基于RePluginClassLoader类
            ClassLoader cl = RePlugin.getConfig().getCallbacks().createClassLoader(oClassLoader.getParent(), oClassLoader);

            // 将新的ClassLoader写入mPackageInfo.mClassLoader
            ReflectUtils.writeField(oPackageInfo, "mClassLoader", cl);

            // 设置线程上下文中的ClassLoader为RePluginClassLoader
            // 防止在个别Java库用到了Thread.currentThread().getContextClassLoader()时，“用了原来的PathClassLoader”，或为空指针
            Thread.currentThread().setContextClassLoader(cl);

            if (LOG) {
                Log.d(TAG, "patch: patch mClassLoader ok");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

```

从上面的部分源码可以看到，Replugin Hook ClassLoader就是通过：
* 获取 Application 的 BaseContext；
* 通过反射，获取 BaseContext（实际上是Context的实现类：ContextImpl） 的私有成员变量 mPackageInfo；
* 通过反射，获取 mPackageInfo.mClassLoader；
* 将 mPackageInfo.mClassLoader 作为参数，生成 Replugin 自定义的 RePluginClassLoader 对象；
* 将新生成的 RePluginClassLoader 写入mPackageInfo.mClassLoader。   

通过上面这些步骤，就实现了 Hook 住应用默认 ClassLoader，这样 Replugin 就可以通过使用自己的 RePluginClassLoader 去实现插件化方案了。

## RePluginClassLoader 原理
上面我们看到了Replugin是如何Hook ClassLoader的，现在我们来看一下RePluginClassLoader是如何进行类加载的，下面是RePluginClassLoader的loadClass()方法：
```
    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        ...
        
        //先让PMF尝试去加载类
        c = PMF.loadClass(className, resolve);
        if (c != null) {
            return c;
        }
        
        //PMF如果没有加载到的情况，交给我们Hook住持有的默认的ClassLoader。
        try {
            c = mOrig.loadClass(className);
            // 只有开启“详细日志”才会输出，防止“刷屏”现象
            if (LogDebug.LOG && RePlugin.getConfig().isPrintDetailLog()) {
                LogDebug.d(TAG, "loadClass: load other class, cn=" + className);
            }
            return c;
        } catch (Throwable e) {
            //
        }
        
        //都没有加载到的情况，交给父类。
        return super.loadClass(className, resolve);
    }

```

上面我们看到了，RePluginClassLoader.loadClass()又会调用 PMF.loadClass()方法，我们再看一下这个方法：
```
    public static final Class<?> loadClass(String className, boolean resolve) {
        return sPluginMgr.loadClass(className, resolve);
    }

```

PMF.loadClass()又调用了 PmBase.loadClass()，我们再进去看一下：
```
    final Class<?> loadClass(String className, boolean resolve) {
        
        // 加载Service中介坑位
        if (className.startsWith(PluginPitService.class.getName())) {
            if (LOG) {
                LogDebug.i(TAG, "loadClass: Loading PitService Class... clz=" + className);
            }
            return PluginPitService.class;
        }

        // 如果是坑位Activity里的className，那么就尝试去解析坑位Activity所对应的插件Activity。
        if (mContainerActivities.contains(className)) {
            Class<?> c = mClient.resolveActivityClass(className);
            if (c != null) {
                return c;
            }
            //解析失败返回一个默认模型Activity，防止崩溃。
            return DummyActivity.class;
        }

        // 和坑位Activity同理，解析坑位Service。
        if (mContainerServices.contains(className)) {
            Class<?> c = loadServiceClass(className);
            if (c != null) {
                return c;
            }
            return DummyService.class;
        }

        //  和坑位Activity同理，解析坑位Provider。
        if (mContainerProviders.contains(className)) {
            Class<?> c = loadProviderClass(className);
            if (c != null) {
                return c;
            }
            return DummyProvider.class;
        }

        // 尝试去插件定制表里面自定义的，需要跳转到插件类的映射类，可以参考 RePlugin.registerHookingClass() 方法。
        DynamicClass dc = mDynamicClasses.get(className);
        ...
        
        //去加载默认插件的class（默认插件和配置文件的位置，一般默认是在 assets 的 plugins-builtin.json 和 "plugins" 文件夹下）。
        return loadDefaultClass(className);
    }
    
```
```
    //去默认插件的加载类
    private final Class<?> loadDefaultClass(String className) {
        //获取默认插件
        Plugin p = mDefaultPlugin;
        if (p == null) {
            if (PluginManager.isPluginProcess()) {
                if (LOG) {
                    LogDebug.d(PLUGIN_TAG, "plugin class loader: not found default plugin,  in=" + className);
                }
            }
            return null;
        }

        ClassLoader cl = p.getClassLoader();
        if (LOG) {
            LogDebug.d(PLUGIN_TAG, "plugin class loader: in=" + className);
        }
        Class<?> c = null;
        try {
            c = cl.loadClass(className);
        } catch (Throwable e) {
            if (LOG) {
                if (e != null && e.getCause() instanceof ClassNotFoundException) {
                    if (LOG) {
                        LogDebug.d(PLUGIN_TAG, "plugin classloader not found className=" + className);
                    }
                } else {
                    if (LOG) {
                        LogDebug.d(PLUGIN_TAG, e.getMessage(), e);
                    }
                }
            }
        }
        if (LOG) {
            LogDebug.d(PLUGIN_TAG, "plugin class loader: c=" + c + ", loader=" + cl);
        }
        return c;
    }
```

从上面的源码，我们可以分析出 RePluginClassLoader 加载插件的具体流程：
1. 首先判断加载的类如果是坑位Activity、Service、Provider的时候，会去解析查到对应匹配的插件类；
2. 如果是自定义的映射类，则去自定义注册的插件映射关系里面寻找所要映射的插件类；
3. 去默认插件里面加载对应的类；
4. 上面步骤都没加载到的情况，让 Hook 的 ClassLoader 去加载该类；
5. 还没加载到的情况让父类去加载。


## PluginDexClassLoader 原理
对比 RePluginClassLoader，PluginDexClassLoader的逻辑则相对简单很多
```
    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        // 插件自己的Class。从自己开始一直到BootClassLoader，采用正常的双亲委派模型流程，读到了就直接返回
        Class<?> pc = null;
        ClassNotFoundException cnfException = null;
        try {
            pc = super.loadClass(className, resolve);
            if (pc != null) {
                // 只有开启“详细日志”才会输出，防止“刷屏”现象
                if (LogDebug.LOG && RePlugin.getConfig().isPrintDetailLog()) {
                    LogDebug.d(TAG, "loadClass: load plugin class, cn=" + className);
                }
                return pc;
            }
        } catch (ClassNotFoundException e) {
            // Do not throw "e" now
            cnfException = e;
            //判断该类是否需要从宿主加载（OkHttp3和Apache的网络库需要去宿主加载）
            if (PluginDexClassLoaderPatch.need2LoadFromHost(className)) {
                try {
                    return loadClassFromHost(className, resolve);
                } catch (ClassNotFoundException e1) {
                    // Do not throw "e1" now
                    cnfException = e1;
                }
            } else {
                if (LogDebug.LOG) {
                    LogDebug.e(TAG, "loadClass ClassNotFoundException, from PluginClassLoader, cn=" + className + ", pluginName=" + mPluginName);
                }
            }
        }

        // 若插件里没有此类，则会从宿主ClassLoader中找，找到了则直接返回
        // 注意：需要读取isUseHostClassIfNotFound开关。默认为关闭的。可参见该开关的说明
        if (RePlugin.getConfig().isUseHostClassIfNotFound()) {
            try {
                return loadClassFromHost(className, resolve);
            } catch (ClassNotFoundException e) {
                // Do not throw "e" now
                cnfException = e;
            }
        }

        // At this point we can throw the previous exception
        if (cnfException != null) {
            throw cnfException;
        }
        return null;
    }
```

## 宿主启动插件Activity的流程

先看一下在宿主中启动插件Activity的调用方式：
```
  Intent intent = new Intent();
  intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.MainActivity"));
  RePlugin.startActivity(MainActivity.this, intent);
```

点击跳转插件Activity的按钮，然后再看看我们在 replugin-host-library 的一些关键位置打印的日志：

```
//1. 调用启动 插件MainActivity 的API，执行一系列启动方法； 

RePlugin.startActivity() -> className = com.clark.learn.replugin.plugindemo1.MainActivity
Factory.startActivityWithNoInjectCN() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginCommImpl.startActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginLibraryInternalProxy.startActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginCommImpl.loadPluginActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginCommImpl.getActivityInfo() -> plugin = plugindemo1，activity = com.clark.learn.replugin.plugindemo1.MainActivity
//===============================================================================================================================================

//2. 加载插件信息，然后加载插件所有Activity的信息保存到：Loader.mComponents.mActivities；

PmBase.loadPlugin(Plugin p, int loadType, boolean useCache)
Plugin.load()
Plugin.loadLocked()
Plugin.doLoad()
Loader.loadDex()
Loader.loadDex() -> 将插件所有的Activity信息存储到：Loader.mComponents.mActivities
Loader.adjustPluginProcess() -> 调整插件组件的进程名称，appInfo = ApplicationInfo{802d2a3 com.clark.learn.replugin.plugindemo1}
RePluginClassLoader.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PMF.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PmBase.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
RePluginClassLoader.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PMF.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PmBase.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
//===============================================================================================================================================

//3. 从 Loader.mComponents.mActivities 获取 插件MainActivity 的所有信息，然后和宿主的 坑位Activity 进行绑定，在 PluginContainers.allocLocked() 方法中会将映射关系存储到PluginContainers.mStates；

PluginCommImpl.getActivityInfo() -> 从 Loader.mComponents.mActivities 获取Activity信息
PluginProcessPer.allocActivityContainer() -> target = com.clark.learn.replugin.plugindemo1.MainActivity
PluginProcessPer.bindActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PmBase.loadPlugin(Plugin p, int loadType, boolean useCache)
Plugin.load()
Plugin.loadLocked()
PluginContainers.alloc() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginContainers.allocLocked() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity，这里将宿主的坑位Activity和真正要启动的插件Activity做了一个绑定，并返回坑位Activity信息，让ClassLoader去启动坑位Activity。
PluginProcessPer.bindActivity() -> 用插件的ClassLoader预加载真正要跳转的Activity：com.clark.learn.replugin.plugindemo1.MainActivity
//===============================================================================================================================================

//4. 当绑定完成之后，会返回宿主的 坑位Activity，然后启动 坑位Activity；

PluginProcessPer.bindActivity() -> 返回插件Activity com.clark.learn.replugin.plugindemo1.MainActivity 对应的宿主坑位Activity：com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PluginLibraryInternalProxy.startActivity() -> 启动坑位Activity = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
//===============================================================================================================================================

//5. RePluginClassLoader 加载该 坑位Activity 的时候，会调用 PluginContainers.lookupByContainer() 查询 PluginContainers.mStates 里面存储的 坑位Activity 映射关系，找到映射的 插件MainActivity；

RePluginClassLoader.loadClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PMF.loadClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PmBase.loadClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PmBase.loadClass() -> mContainerActivities.contains()
PluginProcessPer.resolveActivityClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PluginContainers.lookupByContainer() -> 查询是否有绑定了坑位的Activity信息，className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
//===============================================================================================================================================

//6. 从映射表中找到了宿主 坑位Activity 所对应的 插件MainActivity，这样真正启动的Activity就是 插件的MainActivity了；

PluginContainers.lookupByContainer() -> 找到的绑定坑位ActivityState，activityState = ActivityState {container=com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5 state=occupied plugin=plugindemo1 activity=com.clark.learn.replugin.plugindemo1.MainActivity size=0}
PluginProcessPer.resolveActivityClass() -> 找到坑位com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5映射到的插件Activity：com.clark.learn.replugin.plugindemo1.MainActivity
PmBase.loadPlugin(Plugin p, int loadType, boolean useCache)
Plugin.load()
Plugin.loadLocked()
PluginProcessPer.resolveActivityClass() -> 加载插件信息：plugin = plugindemo1
PluginProcessPer.resolveActivityClass() -> 使用插件 plugindemo1 的ClassLoader加载插件中Activity的Class：com.clark.learn.replugin.plugindemo1.MainActivity

```
从上面的日志，我们可以看到启动插件MainActivity的过程，可以分为以下几个步骤：
1. 调用启动 插件MainActivity 的API，执行一系列启动方法；
2. 加载插件信息，然后加载插件所有Activity的信息保存到：Loader.mComponents.mActivities；
3. 从 Loader.mComponents.mActivities 获取 插件MainActivity 的所有信息，然后和宿主的 坑位Activity 进行绑定，在 PluginContainers.allocLocked() 方法中会将映射关系存储到PluginContainers.mStates；
4. 当绑定完成之后，会返回宿主的 坑位Activity，然后启动 坑位Activity；
5. RePluginClassLoader 加载该 坑位Activity 的时候，会调用 PluginContainers.lookupByContainer() 查询 PluginContainers.mStates 里面存储的 坑位Activity 映射关系，找到映射的 插件MainActivity；
6. 从映射表中找到了宿主 坑位Activity 所对应的 插件MainActivity，这样真正启动的Activity就是 插件的MainActivity了；





