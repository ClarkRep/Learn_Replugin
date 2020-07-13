package com.clark.learn.replugin.plugindemo1;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.qihoo360.replugin.RePlugin;

/**
 * 用来调试宿主通过使用反射class的字符串调用插件的Activity
 */
public class TestPendingIntentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = RePlugin.getPluginContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_test_pending_intent, null);
        setContentView(inflate);

        //使用compileOnly fragment.jar，会导致插件编译期的时候，由于找不到FragmentActivity，从而无法修改继承关系为继承 PluginFragmentActivity.
        //使用compileOnly fragment.jar 无法直接使用 setContentView()，很坑爹。
//        setContentView(R.layout.activity_test_pending_intent);

        inflate.findViewById(R.id.btn_click1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestPendingIntentActivity.this, TestPendingIntentActivity.class);
                startActivity(intent);
            }
        });

        inflate.findViewById(R.id.btn_click2).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                try {
                    String className = "com.clark.learn.replugin.plugindemo1.TestClassNameActivity";
                    Class<?> aClass = Class.forName(className);
                    Intent intent = new Intent();
                    intent.setClassName(TestPendingIntentActivity.this, className);
                    PendingIntent activity = PendingIntent.getActivity(TestPendingIntentActivity.this, 0, intent, 134217728);
                    activity.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
