package com.clark.learn.replugin.plugindemo1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

/**
 * 插件的主Activity
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(this, TestPendingIntentActivity.class);
        if (null != packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)) {
            Log.d("haha", "匹配上了");
        } else {
            Log.d("haha", "没有匹配上");
        }
    }
}
