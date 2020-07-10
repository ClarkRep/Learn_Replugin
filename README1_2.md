# 从启动插件Activity的角度来分析Replugin的核心：HooK ClassLoader
Replugin的核心点就是Hook住了宿主的ClassLoader，它会使用自己的 RePluginClassLoader 替换了宿主的 ClassLoader，在loadClass的时候，就会优先去取插件里面的Class，则会调用原来的ClassLoader取加载Class。  
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



## 宿主启动插件Activity的流程

先看一下在宿主中启动插件Activity的调用方式：
```
  Intent intent = new Intent();
  intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.TestFragmentActivity"));
  RePlugin.startActivity(MainActivity.this, intent);
```

点击跳转插件Activity的按钮，然后再看看我们在 replugin-host-library 的一些关键位置打印的日志：

```
//启动 插件的MainActivity，以下是调用顺序
RePlugin.startActivity() -> className = com.clark.learn.replugin.plugindemo1.MainActivity
Factory.startActivityWithNoInjectCN() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginCommImpl.startActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginLibraryInternalProxy.startActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginCommImpl.loadPluginActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity

//当启动插件的MainActivity的时候，回去获取加载 插件MainActivity 的具体信息，这时候回去加载插件并将插件的信息缓存。
PmBase.loadPlugin(Plugin p, int loadType, boolean useCache)
Plugin.load()
Plugin.loadLocked()
Plugin.doLoad()
Loader.loadDex()
Loader.adjustPluginProcess() -> 调整插件组件的进程名称，appInfo = ApplicationInfo{802d2a3 com.clark.learn.replugin.plugindemo1}
RePluginClassLoader.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PMF.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PmBase.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
RePluginClassLoader.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PMF.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry
PmBase.loadClass() -> className = com.qihoo360.plugin.plugindemo1.Entry

//将获取到的 插件MainActivity 和宿主的坑位Activity进行绑定。
PluginProcessPer.allocActivityContainer() -> target = com.clark.learn.replugin.plugindemo1.MainActivity
PluginProcessPer.bindActivity() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PmBase.loadPlugin(Plugin p, int loadType, boolean useCache)
Plugin.load()
Plugin.loadLocked()
PluginContainers.alloc() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity
PluginContainers.allocLocked() -> activity = com.clark.learn.replugin.plugindemo1.MainActivity，这里将宿主的坑位Activity和真正要启动的插件Activity做了一个绑定，并返回坑位Activity信息，让ClassLoader去启动坑位Activity。
PluginProcessPer.bindActivity() -> 用插件的ClassLoader预加载真正要跳转的Activity：com.clark.learn.replugin.plugindemo1.MainActivity

//当绑定完成之后，会去启动宿主的 坑位Activity，然后 RePluginClassLoader 会加载该坑位Activity真正映射到的插件MainActivity。
PluginLibraryInternalProxy.startActivity() -> 启动坑位Activity = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
RePluginClassLoader.loadClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PMF.loadClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PmBase.loadClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PmBase.loadClass() -> mContainerActivities.contains()
PluginProcessPer.resolveActivityClass() -> className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5
PluginContainers.lookupByContainer() -> 查询是否有绑定了坑位的Activity信息，className = com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5

//找到了宿主坑位Activity所对应的插件MainActivity，这样真正启动的Activity就是 插件的MainActivity了。
PluginContainers.lookupByContainer() -> 找到的绑定坑位ActivityState，activityState = ActivityState {container=com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5 state=occupied plugin=plugindemo1 activity=com.clark.learn.replugin.plugindemo1.MainActivity size=0}
PluginProcessPer.resolveActivityClass() -> 找到坑位com.clark.learn.replugin.host.loader.a.ActivityN1NRNTS5映射到的插件Activity：com.clark.learn.replugin.plugindemo1.MainActivity

```






