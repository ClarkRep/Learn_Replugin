# 从启动插件Activity的角度来分析Replugin的核心：HooK ClassLoader
Replugin的核心点就是Hook住了宿主的ClassLoader，它会使用自己的 RePluginClassLoader 替换了宿主的 ClassLoader，在loadClass的时候，就会优先去取插件里面的Class，则会调用原来的ClassLoader取加载Class。  
这里会用宿主启动插件Activity的流程，来分析一下Hook ClassLoader的具体原理。  
我们看一下replugin-host-library 的源码，从Replugin启动插件的Activity分析原理：
* 宿主是如何 Hook 默认的ClassLoader；
* 将宿主在 Manifest 注册的 坑位Activity 和 插件的Activity 进行绑定；
* 启动 插件Activity 的时候，宿主 RePluginClassLoader 怎么将 坑位Activity 替换成 插件Activity 进行展示。

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








