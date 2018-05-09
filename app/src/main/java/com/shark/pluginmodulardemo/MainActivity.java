package com.shark.pluginmodulardemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shark.utils.library.core.FileUtils;
import com.shark.utils.library.core.SLog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends Activity {
    public static final String PLUGIN_APK_NAME = "PluginDex.apk";
    private DexClassLoader mPluginClassLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        try {
//            SLog.e(this, "DexClassLoader:" + mPluginClassLoader.toString());
//
//            Class<?> activity = mPluginClassLoader.loadClass("com.shark.plugindexapk.TargetActivity");
//            SLog.e(this, "activityClass:" + activity.toString());
//
//            Class<?> beanClass = mPluginClassLoader.loadClass("com.shark.plugindexapk.Target");
//            Object beanObject = beanClass.newInstance();
//            SLog.e(this, "beanObject:" + beanObject.toString());
//
//            Method getNameMethod = beanClass.getDeclaredMethod("showName");
//            getNameMethod.setAccessible(true);
//            String name = (String) getNameMethod.invoke(beanObject);
//            ((TextView) findViewById(R.id.centerTextView)).setText(name);
//
//
//            AssetManager assetManager = AssetManager.class.newInstance();
//            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
//            addAssetPath.invoke(assetManager, dexPath);
//            Resources superRes = super.getResources();
//            Resources mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
//            mResources.newTheme().setTo(super.getTheme());
//
//            ((TextView) findViewById(R.id.centerTextView)).append(mResources.getString(mResources.getIdentifier("plugin_dex_app_name", "string", "com.shark.plugindexapk")));
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.shark.plugindexapk",
//                        "com.shark.plugindexapk.TargetActivity"));
//                startActivity(intent);

//                try {
//
//
//                    Class<?> activity = mPluginClassLoader.loadClass("com.shark.plugindexapk.TargetActivity");
//                    SLog.e(this, "activityClass:" + activity.toString());
//                    startActivity(new Intent(MainActivity.this, activity));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
