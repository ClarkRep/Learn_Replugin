# 从启动插件Activity的角度来分析Replugin的核心：HooK ClassLoader
Replugin的核心点就是Hook住了宿主的ClassLoader，它会使用自己的 RePluginClassLoader 替换了宿主的 ClassLoader，在loadClass的时候，就会优先去取插件里面的Class，则会调用原来的ClassLoader取加载Class。  
这里会用宿主启动插件Activity的流程，来分析一下Hook ClassLoader的具体原理。  
我们分析一下replugin-host-library 的源码，Replugin启动插件的Activity可以从两个角度来分析：
* 将宿主在 Manifest 注册的 坑位Activity 和 插件的Activity 进行绑定；
* 启动 插件Activity 的时候，宿主 RePluginClassLoader 怎么将 坑位Activity 替换成 插件Activity 进行展示。

## 宿主的 坑位Activity 和 插件Activity 的绑定


```
  Intent intent = new Intent();
  intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.TestFragmentActivity"));
  RePlugin.startActivity(MainActivity.this, intent);
```







