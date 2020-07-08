# Learn_Replugin

360的Replugin框架研究案例 
* 由于官方的Replugin项目不是基于AndroidX开发的，所以对于已经AndroidX的应用不兼容，所以我们这个项目是针对Replugin进行了AndroidX的改造，使AndroidX的项目也可以使用Replugin进行插件化。  
* 本Demo基于Replugin2.3.3版本改造，将Replugin的**host的gradle插件库**、**host依赖库**、**plugin的gradle插件库**、**plugin依赖库**进行了AndroidX的适配。  
* 本Demo的**host的gradle插件库**、**host依赖库**、**plugin的gradle插件库**、**plugin依赖库**是拷贝了Replugin 2.3.3版本的SDK，使用本地依赖进行编译。

本地库：
* host 插件库：replugin-host-gradle，编译后的本地gradle插件库：RepluginHostLocalRepo
* host 依赖库：replugin-host-lib
* plugin 插件库：replugin-plugin-gradle，编译后的本地gradle插件库：RepluginPluginLocalRepo
* plugin 依赖库：replugin-plugin-lib

Demo结构：
* host：宿主project，用来加载插件、调试插件
* plugindemo1：插件project，用来生成给宿主使用的插件apk，该apk无法独立运行
* library：用来生成test.jar的module，生成的test.jar作为宿主和插件的中间桥梁，实现宿主和插件的通信。

参考资料：
* 官方项目：[RePlugin](https://github.com/Qihoo360/RePlugin)
* 参考项目：[RePlugin-AndroidX](https://github.com/froyohuang/RePlugin-AndroidX)


## 一、原理分析

* [1.1  Replugin插件编译期做了什么？](README1_1.md)
* [1.2  Replugin的核心：HooK ClassLoader](README1_2.md)

## Demo讲解
