# Learn_Replugin

360的Replugin框架研究案例 

由于官方Replugin是没有适配AndroidX项目的，所以此Demo对Replugin2.3.3的Release版本进行了AndroidX的改造，具体改造点参考：[Replugin适配AndroidX](README0_1.md)

* 修改 replugin-plugin-library 中的 PluginLocalBroadcastManager 中反射获取 LocalBroadcastManager 时使用AndroidX包中的对应的类名；
* 修改 replugin-plugin-library 中的 PluginLocalBroadcastManager 中反射获取 LocalBroadcastManager 时使用AndroidX包中的对应的类名；
本地库的对应关系：
* host 插件库：replugin-host-gradle，编译后的本地gradle插件库：RepluginHostLocalRepo
* host 依赖库：replugin-host-library
* plugin 插件库：replugin-plugin-gradle，编译后的本地gradle插件库：RepluginPluginLocalRepo
* plugin 依赖库：replugin-plugin-library

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
