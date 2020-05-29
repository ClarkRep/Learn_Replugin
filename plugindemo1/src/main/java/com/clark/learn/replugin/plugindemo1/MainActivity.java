package com.clark.learn.replugin.plugindemo1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.qihoo360.replugin.RePlugin;

/**
 * 插件的主Activity
 */
public class MainActivity extends FragmentActivity {

    public static final String PLUGIN_DEMO1_NAME = "plugindemo1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //需要使用这种方式去加载View。
        Context context = RePlugin.getPluginContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.plugin_activity_main, null);
        setContentView(inflate);

        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(context, PluginFragmentActivity.class);
        if (null != packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)) {
            Log.d("haha", "匹配上了");
        } else {
            Log.d("haha", "没有匹配上");
        }
    }
}
