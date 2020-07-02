package com.clark.learn.replugin.host;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.clark.learn.replugin.R;
import com.clark.learn.replugin.library.ITestInterface;
import com.clark.learn.replugin.library.ToastBean;
import com.qihoo360.replugin.RePlugin;

/**
 * 测试插件实现宿主接口的类的调用
 */
public class TestPluginInterfaceImplActivity extends FragmentActivity {

    private static final String TEST_INTERFACE_IMPL = "com.clark.learn.replugin.plugindemo1.TestInterfaceImpl";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add_plugin_view);

        FrameLayout frameLayout = findViewById(R.id.fl_container);

        findViewById(R.id.btn_click1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPluginView(frameLayout);
            }
        });

        findViewById(R.id.btn_click2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPluginToast();
            }
        });

        findViewById(R.id.btn_click3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testContextIsFragmentActivity();
            }
        });

    }

    private void addPluginView(ViewGroup container) {
        //先加载一下插件的context
        RePlugin.fetchContext(MainActivity.PLUGIN_DEMO1_NAME);

        //代码使用插件Fragment
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(MainActivity.PLUGIN_DEMO1_NAME);//获取插件的ClassLoader
        try {
            //hook住插件的这个类，转换成宿主对应的接口进行使用
            Class<?> fragmentClass = d1ClassLoader.loadClass(TEST_INTERFACE_IMPL);
            ITestInterface testInterface = fragmentClass.asSubclass(ITestInterface.class).newInstance();//使用插件的Classloader获取指定Fragment实例
            testInterface.addPluginView(container);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showPluginToast() {
        //先加载一下插件的context
        RePlugin.fetchContext(MainActivity.PLUGIN_DEMO1_NAME);

        ToastBean toastBean = new ToastBean("我是在host里面声明的实体");
        //代码使用插件Fragment
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(MainActivity.PLUGIN_DEMO1_NAME);//获取插件的ClassLoader
        try {
            //hook住插件的这个类，转换成宿主对应的接口进行使用
            Class<?> fragmentClass = d1ClassLoader.loadClass(TEST_INTERFACE_IMPL);
            ITestInterface testInterface = fragmentClass.asSubclass(ITestInterface.class).newInstance();//使用插件的Classloader获取指定Fragment实例
            testInterface.showToast(toastBean);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void testContextIsFragmentActivity() {
        //先加载一下插件的context
        RePlugin.fetchContext(MainActivity.PLUGIN_DEMO1_NAME);

        //代码使用插件Fragment
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(MainActivity.PLUGIN_DEMO1_NAME);//获取插件的ClassLoader
        try {
            //hook住插件的这个类，转换成宿主对应的接口进行使用
            Class<?> fragmentClass = d1ClassLoader.loadClass(TEST_INTERFACE_IMPL);
            ITestInterface testInterface = fragmentClass.asSubclass(ITestInterface.class).newInstance();//使用插件的Classloader获取指定Fragment实例
            testInterface.testContextIsFragmentActivity(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
