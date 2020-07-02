package com.clark.learn.replugin.plugindemo1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.qihoo360.replugin.RePlugin;

/**
 * 插件里的Activity，用来提供给插件及宿主进行调用。
 */
public class TestFragment extends Fragment {

    public TestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * 需要注意不能使用inflater及container因为他们的Context是宿主的
         */
        Log.d("haha", "加载插件的Fragment成功了");

        Log.d("haha", "RePlugin.getPluginContext()==" + RePlugin.getPluginContext());
        Log.d("haha", "RePlugin.fetchContext(\"plugindemo1\")==" + RePlugin.fetchContext("plugindemo1"));

        Context context = RePlugin.getPluginContext();
        return LayoutInflater.from(context).inflate(R.layout.fragment_test, container, false);
    }
}
