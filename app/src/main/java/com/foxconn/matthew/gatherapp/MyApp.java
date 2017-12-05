package com.foxconn.matthew.gatherapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Matthew on 2017/11/20.
 */

public class MyApp extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext=getApplicationContext();
    }

    public static Context getContext(){
        return sContext;
    }
}
