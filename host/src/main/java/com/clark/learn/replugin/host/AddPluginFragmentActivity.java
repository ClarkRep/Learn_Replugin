package com.clark.learn.replugin.host;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.clark.learn.replugin.R;
import com.qihoo360.replugin.RePlugin;

public class AddPluginFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册相关Fragment的类
        //注册一个全局Hook用于拦截系统对XX类的寻找定向到Demo1中的XX类主要是用于在xml中可以直接使用插件中的类
        //提前注册需要hook住的插件里面的 Fragment。
        RePlugin.registerHookingClass(MainActivity.PLUGIN2_TEST_FRAGMENT, RePlugin.createComponentName(MainActivity.PLUGIN2_NAME, MainActivity.PLUGIN2_TEST_FRAGMENT), null);

        setContentView(R.layout.activity_add_plugin_fragment);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPluginFragment();
            }
        });
    }

    private void addPluginFragment() {
        //先加载一下插件的context
        RePlugin.fetchContext(MainActivity.PLUGIN2_NAME);

        //代码使用插件Fragment
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(MainActivity.PLUGIN2_NAME);//获取插件的ClassLoader
        try {
            Class<?> fragmentClass = d1ClassLoader.loadClass(MainActivity.PLUGIN2_TEST_FRAGMENT);
            Fragment fragment = fragmentClass.asSubclass(Fragment.class).newInstance();//使用插件的Classloader获取指定Fragment实例
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();//添加Fragment到UI
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
