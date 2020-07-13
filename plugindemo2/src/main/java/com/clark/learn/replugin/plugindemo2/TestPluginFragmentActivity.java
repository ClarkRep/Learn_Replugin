package com.clark.learn.replugin.plugindemo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qihoo360.replugin.loader.a.PluginFragmentActivity;

public class TestPluginFragmentActivity extends PluginFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
