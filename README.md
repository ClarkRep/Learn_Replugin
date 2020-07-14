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
> complieOnly 依赖了 test.jar，为了骗过编译期，从而让宿主可以使用 test.jar 里面的接口去调用 plugindemo1 里面的实现类，从而实现宿主和插件的交互工作。

4. plugindemo2
> 编译生成的APK作为host的插件进行使用，集成了Replugin的replugin-plugin-gradle插件库、replugin-plugin-library依赖库。  
> complieOnly 依赖了 supportV4 库，为了骗过编译期，从而加载宿主的 Fragment.class ，这样就保证了宿主和插件的 Fragment.class 是同一个，从而让宿主可以加载插件的Fragment。


## 三、Replugin的缺陷

### 问题1：宿主和插件的类隔离
从上面**原理分析**可以看到，Replugin加载插件和宿主的使用的ClassLoader，是不同的ClassLoader，这就导致了使用的是相同的拓展库，并不能在宿主和插件中进行类型转换，会报 ClassCastException。除非插件使用compileOnly进行依赖，欺骗了编译期，从而会从宿主中去寻找该类。所以宿主初始化的一些对象或者属性，如果插件需要使用的话，还需要在插件再初始化一份，也就是将插件也作为一个可以独立运行的APP去打包。  
将插件作为一个独立APP进行打包还是有个问题，就是臃肿，因为作为独立apk打包，依赖的库就比较多了，这样打包出来的插件甚至会和宿主差不多大小，所以需要尽可能的去减小插件的依赖，缩小插件的体积。

### 问题2:宿主加载插件Fragment存在的坑
上面我们说了，插件使用 compileOnly 可以骗过编译期，从而去加载宿主的类。我们想让宿主加载插件的Fragment，则必须保证宿主和插件的Fragment.class是同一个对象，则需要插件使用 compileOnly fragment.jar 包去骗过编译期。这时候又存在一个严重的问题，使用 compileOnly fragment.jar之后，会导致 replugin-plugin-gradle 无法将继承 FragmentActivity 的子类的继承关系修改为继承 PluginFragmentActivity。因为 FragmentActivity.class 和 Fragment.class 是在同一个包里面，使用 compileOnly 编译的时候， 在插件APK里 FragmentActivity.class 就不存在了，所以 replugin-plugin-gradle 没有办法修改继承关系。这就导致了在插件里凡是继承了 FragmentActivity 的子类，都没有办法使用 startActivity() 等一些 PluginFragmentActivity hook住的方法。  
解决办法：让插件的所有要继承FragmentActivity的都继承 PluginFragmentActivity，这样就符合了Replugin所需要修改的默认继承关系，但是耦合性太高。

### 问题3:一些特殊的启动Activity方式在插件中无法使用
我们在插件中，使用 startActivity() 是没问题的，因为 replugin-plugin-gradle 修改了继承关系，这样保证我们可以和宿主一样去启动Activity。但是有一些特殊的Activity启动方式，我在插件里做了实验，是无法正常启动Activity的（宿主中可以）。比如广点通广告SDK启动他们的广告页面，是使用下面这种方式启动的：
```
  String className = "com.clark.learn.replugin.plugindemo1.TestClassNameActivity";
  Class<?> aClass = Class.forName(className);
  Intent intent = new Intent();
  intent.setClassName(TestPendingIntentActivity.this, className);
  PendingIntent activity = PendingIntent.getActivity(TestPendingIntentActivity.this, 0, intent, 134217728);
  activity.send();
```
这种启动方式在插件里去启动插件自身的Activity是行不通的，估计是由于该启动方式越过了Replugin所Hook住的逻辑，默认去开启宿主的Activity了，从而异常崩溃了，暂时没有解决办法。

### 问题4:一些第三方SDK的兼容性问题
如果我们使用的插件里集成里宿主没有的第三方库，这就需要小心一点，因为第三方库的逻辑有可能和Replugin不太兼容，建议这类第三方库都交给宿主去集成，插件compileOnly去使用。











