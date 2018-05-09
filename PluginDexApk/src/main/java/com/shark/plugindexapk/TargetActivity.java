package com.shark.plugindexapk;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class TargetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_activity_main);
    }
    @Override
    public Resources getResources() {
        Log.e("Main","getApplicationContext = " + getApplicationContext());
        Log.e("Main","getApplicationContext 2 = " + getApplication());
        Log.e("Main","getApplicationContext 2 = " + super.getResources());
//        Log.e("Main","getApplicationContext 3= " + getApplication().getResources());
        if(getApplication() != null && getApplication().getResources() != null){
            return getApplication().getResources();
        }
        return super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        if(getApplication() != null && getApplication().getAssets() != null){
            return getApplication().getAssets();
        }
        return super.getAssets();
    }

    @Override
    public Resources.Theme getTheme() {
        if(getApplication() != null && getApplication().getTheme() != null){
            return getApplication().getTheme();
        }
        return super.getTheme();
    }
}
