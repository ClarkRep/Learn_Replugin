package com.clark.learn.replugin.plugindemo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qihoo360.replugin.loader.a.PluginFragmentActivity;

public class TestPluginFragmentActivity extends PluginFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //使用compileOnly fragment.jar，会导致插件编译期的时候，由于找不到FragmentActivity，从而无法修改继承关系为继承 PluginFragmentActivity.
        //使用compileOnly fragment.jar 无法直接使用 setContentView()，很坑爹。
        //我们这里直接继承了 PluginFragmentActivity，所以没有这方面顾虑。
        setContentView(R.layout.activity_test_plugin_fragment_activity);

        findViewById(R.id.btn_click1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestPluginFragmentActivity.this, TestPluginFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
