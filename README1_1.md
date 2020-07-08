# Replugin插件编译期做了什么？

这里主要讲解一下Replugin在编译期做了哪些事情，才能使宿主去加载插件的四大组件。Replugin在编译期做的，分别通过replugin-host-gradle、replugin-plugin-gradle这两个gradle插件，对宿主和插件项目进行编译期的改造。  
下面会从宿主和插件两个纬度去分析一下编译期的具体工作。
  
## 宿主的编译期

replugin-host-gradle，针对宿主应用编译期的注入任务：
* ComponentsGenerator - 生成带 RePlugin 插件坑位的 AndroidManifest.xml（允许自定义数量）
* RePluginHostConfigCreator - 生成 RepluginHostConfig 类，方便插件框架读取并自定义其属性
* PluginBuiltinJsonCreator - 生成 plugins-builtin.json，json中含有插件应用的信息，包名，插件名，插件路径等。  

详细内容可以自己阅读源码，或者看下面的参考资料：  
参考资料：[《Replugin源码解析之replugin-host-gradle（宿主的gradle插件）》](https://www.jianshu.com/p/ca3bda0800b6)

## 插件的编译期

replugin-plugin-gradle，针对插件应用编译期的注入任务：  
动态修改插件中的调用代码，改为调用replugin-plugin-lib中的代码（如Activity的继承、Provider的重定向等）
* LoaderActivityInjector - 动态将插件中的 Activity 的继承相关代码修改为 replugin-plugin-lib 中的XXPluginActivity父类
* LocalBroadcastInjector - 替换插件中的 LocalBroadcastManager 调用代码 为 插件库的调用代码。
* ProviderInjector - 替换插件中的 ContentResolver 调用代码 为 插件库的调用代码
* ProviderInjector2 - 替换插件中的 ContentProviderClient 调用代码 为 插件库的调用代码
* GetIdentifierInjector - 替换插件中的 Resource.getIdentifier 调用代码的参数为动态适配的参数

详细内容可以自己阅读源码，或者看下面的参考资料：  
参考资料：[《Replugin源码解析之replugin-plugin-gradle（插件的gradle插件）》](https://www.jianshu.com/p/a9b3aaba8e45)  
