package com.clark.learn.replugin.host;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.clark.learn.replugin.R;
import com.qihoo360.replugin.RePlugin;

public class TestPluginFragmentActivity extends AppCompatActivity {

    //提前注册需要hook住的插件里面的 Fragment。
    private static final String PLUGIN_HOOK_BASE_FRAGMENT = "com.clark.learn.replugin.plugindemo1.TestFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册相关Fragment的类
        //注册一个全局Hook用于拦截系统对XX类的寻找定向到Demo1中的XX类主要是用于在xml中可以直接使用插件中的类
        RePlugin.registerHookingClass(PLUGIN_HOOK_BASE_FRAGMENT, RePlugin.createComponentName(MainActivity.PLUGIN_DEMO1_NAME, PLUGIN_HOOK_BASE_FRAGMENT), null);

        setContentView(R.layout.activity_test_plugin_fragment);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPluginFragment();
            }
        });
    }

    private void addPluginFragment() {
        //先加载一下插件的context
        RePlugin.fetchContext(MainActivity.PLUGIN_DEMO1_NAME);

        //代码使用插件Fragment
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(MainActivity.PLUGIN_DEMO1_NAME);//获取插件的ClassLoader
        try {
            Class<?> fragmentClass = d1ClassLoader.loadClass(PLUGIN_HOOK_BASE_FRAGMENT);
            Fragment fragment = fragmentClass.asSubclass(Fragment.class).newInstance();//使用插件的Classloader获取指定Fragment实例
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();//添加Fragment到UI
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
