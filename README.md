# Learn_Replugin

360的Replugin框架研究案例 

由于官方Replugin是没有适配AndroidX项目的，所以此Demo对Replugin2.3.3的Release版本进行了AndroidX的改造，具体改造点参考：[Replugin适配AndroidX](README0_1.md)

Demo结构：
* host：宿主project，用来加载插件、调试插件；
* library：用来生成test.jar的module，生成的test.jar作为宿主和插件的中间桥梁，实现宿主和插件的通信；
* plugindemo1：插件project，用来生成给宿主使用的插件apk；
* plugindemo2：插件project，用来生成给宿主使用的插件apk；
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

## 二、Demo讲解
前面的原理分析已经讲述了 replugin-host-gradle、replugin-host-library、replugin-plugin-gradle、replugin-plugin-library的一些原理，下面会结合本demo去实践一下Replugin的具体使用方式。

1. host 
> host 是本项目的宿主project，集成了Replugin的 replugin-host-gradle插件库、replugin-host-library依赖库，是用来调试各个插件的功能。   
> 将**plugindemo1**和**plugindemo2**打包生成的apk作为插件放置在**host**的**assets**文件夹中，作为**host**的插件进行使用。


2. library
> library 是用来生成 test.jar 的库，本身和其他几个库没有任何依赖关系，只是提供 test.jar 给host和plugindemo1作为中间桥梁。


3. plugindemo1
> 编译生成的APK作为host的插件进行使用，集成了Replugin的replugin-plugin-gradle插件库、replugin-plugin-library依赖库。  
> 

4. plugindemo2
> 编译生成的APK作为host的插件进行使用，集成了Replugin的replugin-plugin-gradle插件库、replugin-plugin-library依赖库。  
> 使用 complieOnly 依赖了 supportV4 库，为了骗过编译期，从而加载宿主的 Fragment.class ，这样就保证了宿主和插件的 Fragment.class 是同一个，从而让宿主可以加载插件的Fragment。



