package org.spendwise.coinkeep;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import org.spendwise.coinkeep.data.dao.DaoManager;

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