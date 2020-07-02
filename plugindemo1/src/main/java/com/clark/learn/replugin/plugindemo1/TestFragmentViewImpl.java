package com.clark.learn.replugin.plugindemo1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clark.learn.replugin.library.IFragmentView;
import com.qihoo360.replugin.RePlugin;

public class TestFragmentViewImpl implements IFragmentView {

    private Context mContext;

    private Bundle mArguments;

    public Context getContext() {
        if (mContext == null) {
            mContext = RePlugin.getPluginContext();
        }
        return mContext;
    }

    public void startActivity(Intent intent) {
        Context context = getContext();
        if (context != null && intent != null) {
            context.startActivity(intent);
        }
    }

    @Override
    public void setArguments(Bundle bundle) {
        mArguments = bundle;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = RePlugin.getPluginContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_fragment_impl, viewGroup, false);
        inflate.findViewById(R.id.btn_click1).setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), TestClassNameActivity.class);
            getContext().startActivity(intent);
        });
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {

    }

    @Override
    public void onViewStateRestored(Bundle bundle) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void onMultiWindowModeChanged(boolean b) {

    }

    @Override
    public void onPictureInPictureModeChanged(boolean b) {

    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void setUserVisibleHint(boolean b) {

    }
}
