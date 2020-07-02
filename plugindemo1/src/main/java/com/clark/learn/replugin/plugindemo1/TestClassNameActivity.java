package com.clark.learn.replugin.plugindemo1;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

/**
 * 用来调试宿主通过使用反射class的字符串调用插件的Activity
 */
public class TestClassNameActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Context context = RePlugin.getPluginContext();
//        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_test_class_name, null);
//        setContentView(inflate);

        setContentView(R.layout.activity_test_class_name);

        findViewById(R.id.btn_click1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestClassNameActivity.this, TestClassNameActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_click2).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                try {
                    String className = "com.clark.learn.replugin.plugindemo1.TestClassNameActivity";
                    Class<?> aClass = Class.forName(className);
                    Intent intent = new Intent();
                    intent.setClassName(TestClassNameActivity.this, className);
                    PendingIntent activity = PendingIntent.getActivity(TestClassNameActivity.this, 0, intent, 134217728);
                    activity.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
