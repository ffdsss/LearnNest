package com.pacepal.walk;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.pacepal.walk.data.dao.DaoManager;

public class MyApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DaoManager.getInstance();
    }



}