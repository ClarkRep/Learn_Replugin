package com.clark.learn.replugin.plugindemo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.clark.learn.replugin.library.ITestInterface;
import com.clark.learn.replugin.library.ToastBean;
import com.qihoo360.replugin.RePlugin;

public class TestAddPluginView implements ITestInterface {

    @Override
    public void addPluginView(ViewGroup viewGroup) {
        Context pluginContext = RePlugin.getPluginContext();

        View inflate = LayoutInflater.from(pluginContext).inflate(R.layout.view_test, null);
        viewGroup.addView(inflate);
    }

    @Override
    public void showToast(ToastBean toastBean) {
        Context pluginContext = RePlugin.getPluginContext();

        Toast.makeText(pluginContext, toastBean.getToastStr(), Toast.LENGTH_SHORT).show();
    }

}
