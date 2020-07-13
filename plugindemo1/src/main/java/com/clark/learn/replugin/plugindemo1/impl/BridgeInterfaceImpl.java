package com.clark.learn.replugin.plugindemo1.impl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.clark.learn.replugin.library.IBridgeInterface;
import com.clark.learn.replugin.library.ToastBean;
import com.clark.learn.replugin.plugindemo1.R;
import com.qihoo360.replugin.RePlugin;

public class BridgeInterfaceImpl implements IBridgeInterface {

    @Override
    public void addPluginView(ViewGroup viewGroup) {
        Context pluginContext = RePlugin.getPluginContext();

        View inflate = LayoutInflater.from(pluginContext).inflate(R.layout.view_test_interface, null);
        viewGroup.addView(inflate);
    }

    @Override
    public void showToast(ToastBean toastBean) {
        Context pluginContext = RePlugin.getPluginContext();

        Toast.makeText(pluginContext, toastBean.getToastStr(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void testContextIsFragmentActivity(Context context) {
        Context pluginContext = RePlugin.getPluginContext();
        boolean isActivity = context instanceof Activity;
        boolean isFragmentActivity = context instanceof FragmentActivity;
        Toast.makeText(pluginContext, "宿主的Context是否可以转换成插件的Activity：" + isActivity + "\n宿主的Context是否可以转换成插件的FragmentActivity：" + isFragmentActivity, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void testHostFragmentActivity(FragmentActivity fragmentActivity) {
//        Context pluginContext = RePlugin.getPluginContext();
//
//        boolean isFragmentActivity = fragmentActivity instanceof FragmentActivity;
//        Toast.makeText(pluginContext, "宿主的fragmentActivity是否可以转换成插件的FragmentActivity：" + isFragmentActivity, Toast.LENGTH_SHORT).show();
//    }

}
