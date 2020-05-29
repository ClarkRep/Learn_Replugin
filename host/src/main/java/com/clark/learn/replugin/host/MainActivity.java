package com.clark.learn.replugin.host;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.clark.learn.replugin.R;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.qihoo360.replugin.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends FragmentActivity {


    public static final String PLUGIN_DEMO1_NAME = "plugindemo1";
    public static final String PLUGIN_DEMO1_APK = "plugindemo1.apk";//assets 下面插件的apk名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_click1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
                if (isSuccess) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.MainActivity"));
                    RePlugin.startActivity(MainActivity.this, intent);
                } else {
                    Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        findViewById(R.id.btn_click2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
                if (isSuccess) {
                    Intent intent = new Intent(MainActivity.this, TestPluginFragmentActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_click3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
                if (isSuccess) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(PLUGIN_DEMO1_NAME, "com.clark.learn.replugin.plugindemo1.PluginFragmentActivity"));
                    RePlugin.startActivity(MainActivity.this, intent);
                } else {
                    Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_click4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = simulateInstallExternalPlugin(PLUGIN_DEMO1_APK);
                if (isSuccess) {
                    Intent intent = new Intent(MainActivity.this, TestAddPluginViewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "install external plugin failed", Toast.LENGTH_SHORT).show();
                }
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
//            RePlugin.startActivity(MainActivity.this, RePlugin.createIntent(info.getName(), "com.clark.learn.replugin.plugindemo1.MainActivity"));
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
