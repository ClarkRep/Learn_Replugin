# Learn_Replugin

360的Replugin框架研究案例，这个Demo基于Replugin2.3.3版本改造，将Replugin的**host的gradle插件库**、**host依赖库**、**plugin的gradle插件库**、**plugin依赖库**进行了AndroidX的适配。  
这个demo的**host的gradle插件库**、**host依赖库**、**plugin的gradle插件库**、**plugin依赖库**是拷贝了Replugin 2.3.3版本的SDK，使用本地依赖进行编译。

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


## 原理分析

* [Replugin插件编译期做了什么？](https://github.com/Qihoo360/RePlugin)
* [Replugin的核心：HooK ClassLoader](https://github.com/Qihoo360/RePlugin)

## Demo讲解
