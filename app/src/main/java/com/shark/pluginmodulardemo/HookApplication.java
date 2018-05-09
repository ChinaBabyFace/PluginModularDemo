package com.shark.pluginmodulardemo;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.shark.pluginmodulardemo.hook.AMSHookHelper;
import com.shark.pluginmodulardemo.hook.MyHookHelper;
import com.shark.utils.library.core.FileUtils;
import com.shark.utils.library.core.SLog;

import java.io.IOException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class HookApplication extends Application {
    public static final String PLUGIN_APK_NAME = "PluginDex.apk";
    private AssetManager assetManager;
    private Resources newResource;
    private Resources.Theme mTheme;
    @Override
    public void onCreate() {
        super.onCreate();
//        HookUtils hookAmsUtil = new HookUtils(ProxyActivity.class, this);
//        hookAmsUtil.hookSystemHandler();
//        hookAmsUtil.hookAms();
//        AmsHookUtils hookAmsUtil = new AmsHookUtils(ProxyActivity.class, this);
//        hookAmsUtil.hookAms();
        try {
            FileUtils.copyFile(
                    getAssets().open(PLUGIN_APK_NAME),
                    openFileOutput(PLUGIN_APK_NAME, MODE_PRIVATE)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pluginPath = getFileStreamPath(PLUGIN_APK_NAME).getAbsolutePath();
        SLog.e(this, "pluginPath:" + pluginPath);

        try {


            String mPath = getPackageResourcePath();

            assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);

//            addAssetPathMethod.invoke(assetManager, mPath);
            addAssetPathMethod.invoke(assetManager, pluginPath);


            Method ensureStringBlocks = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            ensureStringBlocks.setAccessible(true);
            ensureStringBlocks.invoke(assetManager);

            Resources supResource = getResources();
            Log.e("Main", "supResource = " + supResource);
            newResource = new Resources(assetManager, supResource.getDisplayMetrics(), supResource.getConfiguration());
            Log.e("Main", "设置 getResource = " + getResources());

            mTheme = newResource.newTheme();
            mTheme.setTo(super.getTheme());
        } catch (Exception e) {
            Log.e("Main", "走了我的callActivityOnCreate 错了 = " + e.getMessage());
            e.printStackTrace();
        }

        MyHookHelper.inject(this,pluginPath);
        try {
            AMSHookHelper.hookActivityManagerNative(this);
            AMSHookHelper.hookActivityThreadHandler();
        } catch (Exception e) {
            SLog.e(this,"Hook AMS Fail!");
            e.printStackTrace();
        }
    }
    @Override
    public AssetManager getAssets() {
        return assetManager == null ? super.getAssets() : assetManager;
    }

    @Override
    public Resources getResources() {
        return newResource == null ? super.getResources() : newResource;
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme == null ? super.getTheme() : mTheme;
    }

}
