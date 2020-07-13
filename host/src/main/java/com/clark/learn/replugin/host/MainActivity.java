package com.clark.learn.replugin.host;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clark.learn.replugin.R;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.qihoo360.replugin.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Learn_Plugin";

    //以下是插件1的配置信息 ============================================================================

    //插件1 名称和文件名
    public static final String PLUGIN1_NAME = "plugindemo1";
    public static final String PLUGIN1_APK = "plugindemo1-debug.apk";//assets 下面插件的apk名

    //插件1 的MainActivity
    public static final String PLUGIN1_MAIN_ACTIVITY = "com.clark.learn.replugin.plugindemo1.MainActivity";
    //插件1 用来测试PendingIntent跳转的Activity
    public static final String PLUGIN_TEST_PENDING_INTENT_ACTIVITY = "com.clark.learn.replugin.plugindemo1.TestPendingIntentActivity";
    //插件1 的IBridgeInterface实现类，用来让宿主调用插件的功能
    public static final String PLUGIN_BRIDGE_IMPL = "com.clark.learn.replugin.plugindemo1.impl.BridgeInterfaceImpl";
    //插件1 的IProxyFragmentView实现类，用来代理宿主的Fragment功能
    public static final String PLUGIN_PROXY_FRAGMENT_VIEW_IMPL = "com.clark.learn.replugin.plugindemo1.impl.ProxyFragmentImpl";


    //以下是插件2的配置信息 ============================================================================
    //插件2 名称和文件名
    public static final String PLUGIN2_NAME = "plugindemo2";
    public static final String PLUGIN2_APK = "plugindemo2-debug.apk";//assets 下面插件的apk名

    //插件2 的Fragment，用来让宿主加载
    public static final String PLUGIN2_TEST_FRAGMENT = "com.clark.learn.replugin.plugindemo2.fragment.TestFragment";
    //插件2 用来加载Fragment的Activity
    public static final String PLUGIN2_TEST_FRAGMENT_ACTIVITY = "com.clark.learn.replugin.plugindemo2.TestFragmentActivity";
    //插件2 的Activity，继承了Replugin的PluginFragmentActivity，用来测试 complieOnly androidx-fragment-1.1.0.jar，插件Activity能否正常启动插件的Activity。
    public static final String PLUGIN2_TEST_PLUGIN_FRAGMENT_ACTIVITY = "com.clark.learn.replugin.plugindemo2.TestPluginFragmentActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //在宿主中，测试使用PendingIntent启动宿主的Activity
        findViewById(R.id.host_btn).setOnClickListener(v -> {
            try {
                String className = "com.clark.learn.replugin.host.MainActivity";
                Class<?> aClass = Class.forName(className);
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, className);
                PendingIntent activity = PendingIntent.getActivity(MainActivity.this, 0, intent, 134217728);
                activity.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        initPlugin1();
        initPlugin2();
    }


    private void initPlugin1() {
        //安装插件1，跳转插件的MainActivity
        findViewById(R.id.plugin1_btn1).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN1_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN1_NAME, PLUGIN1_MAIN_ACTIVITY));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转宿主TestBridgeInterfaceImplActivity，测试链接宿主和插件的接口功能
        findViewById(R.id.plugin1_btn2).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN1_APK);
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, TestBridgeInterfaceImplActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转插件TestPendingIntentActivity，测试在该Activity中使用PendingIntent启动插件Activity能否成功
        findViewById(R.id.plugin1_btn3).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN1_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN1_NAME, PLUGIN_TEST_PENDING_INTENT_ACTIVITY));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转宿主TestPluginFragmentViewActivity，在该Activity中加载HostFragment，该Fragment使用插件的代理类实现功能
        findViewById(R.id.plugin1_btn4).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN1_APK);
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, TestPluginProxyFragmentViewActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPlugin2() {
        //安装插件2，跳转宿主AddPluginFragmentActivity，在该Activity加载插件2的Fragment
        findViewById(R.id.plugin2_btn1).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN2_APK);
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, AddPluginFragmentActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件2，跳转插件的TestFragmentActivity，在该Activity添加插件的Fragment
        findViewById(R.id.plugin2_btn2).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN2_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN2_NAME, PLUGIN2_TEST_FRAGMENT_ACTIVITY));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件2，跳转插件TestPluginFragmentActivity，测试在该Activity中能否正常启动插件Activity
        findViewById(R.id.plugin2_btn3).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN2_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN2_NAME, PLUGIN2_TEST_PLUGIN_FRAGMENT_ACTIVITY));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 模拟安装或升级（覆盖安装）外置插件
     * 注意：为方便演示，外置插件临时放置到Host的assets/external目录下，具体说明见README</p>
     */
    private boolean simulateInstallExternalPlugin(String pluginApk) {
        String demoApk = pluginApk;
        String demoApkPath = demoApk;

        // 文件是否已经存在？直接删除重来
        String pluginFilePath = getFilesDir().getAbsolutePath() + File.separator + demoApkPath;
        File pluginFile = new File(pluginFilePath);
        if (pluginFile.exists()) {
            FileUtils.deleteQuietly(pluginFile);
        }

        // 开始复制
        copyAssetsFileToAppFiles(demoApk, demoApkPath);
        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(pluginFilePath);
        }

        if (info != null) {
            return true;
        }

        return false;
    }

    /**
     * 从assets目录中复制某文件内容
     *
     * @param assetFileName assets目录下的Apk源文件路径
     * @param newFileName   复制到/data/data/package_name/files/目录下文件名
     */
    private void copyAssetsFileToAppFiles(String assetFileName, String newFileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {
            is = this.getAssets().open(assetFileName);
            fos = this.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
