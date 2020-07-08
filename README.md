# Learn_Replugin

360的Replugin框架研究案例 

由于官方Replugin是没有适配AndroidX项目的，所以此Demo对Replugin2.3.3的Release版本进行了AndroidX的改造，具体改造点参考：[Replugin适配AndroidX](README0_1.md)

Demo结构：
* host：宿主project，用来加载插件、调试插件；
* library：用来生成test.jar的module，生成的test.jar作为宿主和插件的中间桥梁，实现宿主和插件的通信；
* plugindemo1：插件project，用来生成给宿主使用的插件apk，该apk无法直接运行；
* replugin-host-gradle：host插件库
* replugin-host-library：host依赖库
* replugin-plugin-gradle：plugin插件库
* replugin-plugin-library：plugin依赖库
* RepluginHostLocalRepo：replugin-host-gradle编译后生成的本地host插件库，host就依赖该本地库进行编译的；
* RepluginPluginLocalRepo：replugin-plugin-gradle编译后生成的本地plugin插件库，plugindemo1就依赖该本地插件库进行编译的。

参考资料：
* 官方项目：[RePlugin](https://github.com/Qihoo360/RePlugin)
* 参考项目：[RePlugin-AndroidX](https://github.com/froyohuang/RePlugin-AndroidX)


## 一、原理分析

* [1.1  Replugin插件编译期做了什么？](README1_1.md)
* [1.2  从启动插件Activity的角度来分析Replugin的核心：HooK ClassLoader](README1_2.md)

## Demo讲解
