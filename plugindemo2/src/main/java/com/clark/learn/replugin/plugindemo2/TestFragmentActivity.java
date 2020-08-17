package com.clark.learn.replugin.plugindemo2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.clark.learn.replugin.plugindemo2.fragment.TestFragment;
import com.qihoo360.replugin.RePlugin;

/**
 * 用来调试插件里的Activity加载插件里的Fragment是否有问题。
 * 因为FragmentActivity是使用compileOnly依赖，所以需要验证一下。
 */
public class TestFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //使用compileOnly fragment.jar，会导致插件编译期的时候，由于找不到FragmentActivity，从而无法修改继承关系为继承 PluginFragmentActivity.
        //使用compileOnly fragment.jar 无法直接使用 setContentView()，很坑爹。
        Context context = RePlugin.getPluginContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_test_fragment, null);
        setContentView(inflate);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPluginFragment();
            }
        });
    }

    private void addPluginFragment() {
        Fragment fragment = new TestFragment();//使用插件的Classloader获取指定Fragment实例
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.d("haha", "fragmentManager = " + fragmentManager.getClass());
        fragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit();//添加Fragment到UI
    }
}
