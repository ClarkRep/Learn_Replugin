package com.clark.learn.replugin.library;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用来接管Fragment的接口，插件实现这个接口去替换宿主的Fragment逻辑
 */
public interface IFragmentView {

    //设置Fragment的参数 ================================================================================

    public void setArguments(Bundle args);

    //以下是Fragment的生命周期方法，使用这些方法，可以让插件实现类去代理Fragment的功能 ==========================

    public void setUserVisibleHint(boolean isVisibleToUser);

    public void onAttach(Context context);

    public void onCreate(Bundle savedInstanceState);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void onActivityCreated(Bundle savedInstanceState);

    public void onViewStateRestored(Bundle savedInstanceState);

    public void onStart();

    public void onResume();

    public void onSaveInstanceState(Bundle outState);

    public void onMultiWindowModeChanged(boolean isInMultiWindowMode);

    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode);

    public void onConfigurationChanged(Configuration newConfig);

    public void onPause();

    public void onStop();

    public void onLowMemory();

    public void onDestroyView();

    public void onDestroy();

    public void onDetach();

}
