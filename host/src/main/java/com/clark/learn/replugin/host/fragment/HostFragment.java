package com.clark.learn.replugin.host.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.clark.learn.replugin.host.MainActivity;
import com.clark.learn.replugin.library.IProxyFragment;
import com.qihoo360.replugin.RePlugin;

public class HostFragment extends Fragment {

    private IProxyFragment mIFragmentView;

    public HostFragment() {
        //先加载一下插件的context
        RePlugin.fetchContext(MainActivity.PLUGIN1_NAME);

        //代码使用插件Fragment
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(MainActivity.PLUGIN1_NAME);//获取插件的ClassLoader
        try {
            Class<?> fragmentClass = d1ClassLoader.loadClass(MainActivity.PLUGIN_PROXY_FRAGMENT_VIEW_IMPL);
            mIFragmentView = fragmentClass.asSubclass(IProxyFragment.class).newInstance();//使用插件的Classloader获取指定Fragment实例
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        if (mIFragmentView != null) {
            mIFragmentView.setArguments(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mIFragmentView != null) {
            mIFragmentView.onAttach(context);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mIFragmentView != null) {
            return mIFragmentView.onCreateView(inflater, container, savedInstanceState);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (mIFragmentView != null) {
            mIFragmentView.onActivityCreated(bundle);
        }
    }

    @Override
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (mIFragmentView != null) {
            mIFragmentView.onViewStateRestored(bundle);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIFragmentView != null) {
            mIFragmentView.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIFragmentView != null) {
            mIFragmentView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (mIFragmentView != null) {
            mIFragmentView.onSaveInstanceState(bundle);
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean b) {
        super.onMultiWindowModeChanged(b);
        if (mIFragmentView != null) {
            mIFragmentView.onMultiWindowModeChanged(b);
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean b) {
        super.onPictureInPictureModeChanged(b);
        if (mIFragmentView != null) {
            mIFragmentView.onPictureInPictureModeChanged(b);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (mIFragmentView != null) {
            mIFragmentView.onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mIFragmentView != null) {
            mIFragmentView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mIFragmentView != null) {
            mIFragmentView.onStop();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mIFragmentView != null) {
            mIFragmentView.onLowMemory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mIFragmentView != null) {
            mIFragmentView.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIFragmentView != null) {
            mIFragmentView.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mIFragmentView != null) {
            mIFragmentView.onDetach();
        }
    }

    @Override
    public void setUserVisibleHint(boolean b) {
        super.setUserVisibleHint(b);
        if (mIFragmentView != null) {
            mIFragmentView.setUserVisibleHint(b);
        }
    }
}
