package com.clark.learn.replugin.host;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.clark.learn.replugin.R;

public class TestPluginFragmentViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册相关Fragment的类
        setContentView(R.layout.activity_test_plugin_fragment_view);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment();
            }
        });
    }

    private void addFragment() {
        Fragment fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();//添加Fragment到UI
    }
}
