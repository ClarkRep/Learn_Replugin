package com.clark.learn.replugin.library;

import android.content.Context;
import android.view.ViewGroup;

public interface ITestInterface {

    void addPluginView(ViewGroup container);

    void showToast(ToastBean toastBean);

    /**
     * 测试传入宿主的Context能否匹配上插件的FragmentActivity。
     *
     * @param context
     */
    void testContextIsFragmentActivity(Context context);

//    /**
//     * 测试传入宿主的Context能否匹配上插件的FragmentActivity。
//     *
//     * @param fragmentActivity
//     */
//    void testHostFragmentActivity(FragmentActivity fragmentActivity);

}
