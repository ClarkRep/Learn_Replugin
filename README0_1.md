# Replugin的AndroidX适配

由于官方的Replugin项目不是基于AndroidX开发的，所以对于已经AndroidX的应用不兼容，所以我们这个项目是针对Replugin进行了AndroidX的改造，使基于AndroidX开发的项目也可以使用Replugin进行插件化。
* 本Demo基于Replugin2.3.3版本改造，将Replugin的host的gradle插件库、host依赖库、plugin的gradle插件库、plugin依赖库进行了AndroidX的适配。
* 本Demo的host的gradle插件库、host依赖库、plugin的gradle插件库、plugin依赖库是拷贝了Replugin 2.3.3版本的对应库，并使用本地依赖进行编译。

对Replugin进行AndroidX改造的点：
* 将 replugin-host-library 和 replugin-plugin-library 库中的 build.gradle 对 support 包的引用修改成对应AndroidX的包引用，并且修改里面所有的导包；
* 修改 replugin-plugin-gradle 中 LoaderActivityInjector，使其能够匹配Androidx包下的FragmentAcvitiy和AppCompatActivity，并进行替换；
* 修改 replugin-plugin-gradle 中 LocalBroadcastExprEditor 和 LocalBroadcastInjector 里的 LocalBroadcastManager 为对应AndroidX类路径；
* 修改 replugin-plugin-library 中的 PluginFragmentActivity 和 PluginAppCompatActivity 继承AndroidX包中的对应Activity；
* 修改 replugin-plugin-library 中的 PluginLocalBroadcastManager 中反射获取 LocalBroadcastManager 时使用AndroidX包中的对应的类名；
* 修改 replugin-host-library 中各处对 LocalBroadcastManager 的引用，改为引用AndroidX包中的类。
