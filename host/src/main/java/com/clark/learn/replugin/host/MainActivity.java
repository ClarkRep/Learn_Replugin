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


    public static final String PLUGIN_DEMO1_NAME = "plugindemo1";
    public static final String PLUGIN_DEMO1_APK = "plugindemo1-debug.apk";//assets 下面插件的apk名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //安装插件1，跳转宿主Activity，在宿主Activity添加插件的Fragment
        findViewById(R.id.btn_click1).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, TestPluginFragmentActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转宿主Activity，在宿主Activity里添加插件的View
        findViewById(R.id.btn_click2).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, TestPluginInterfaceImplActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //在宿主中，测试使用PendingIntent启动宿主的Activity
        findViewById(R.id.btn_click3).setOnClickListener(v -> {
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

        //安装插件1，跳转插件Activity
        findViewById(R.id.btn_click4).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.MainActivity"));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转插件Activity，在插件Activity添加插件的Fragment
        findViewById(R.id.btn_click5).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.TestFragmentActivity"));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转插件Activity，测试在插件Activity中使用PendingIntent跳转插件Activity能否成功
        findViewById(R.id.btn_click6).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
            if (isSuccess) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.TestClassNameActivity"));
                RePlugin.startActivity(MainActivity.this, intent);
            } else {
                Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
            }
        });

        //安装插件1，跳转宿主Activity，在宿主Activity加载Fragment，该Fragment使用插件的代理类实现功能。
        findViewById(R.id.btn_click7).setOnClickListener(v -> {
            boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
            if (isSuccess) {
                Intent intent = new Intent(MainActivity.this, TestPluginFragmentViewActivity.class);
                startActivity(intent);
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
