package com.foxconn.matthew.gatherapp;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Matthew on 2017/11/20.
 */

public class MyApp extends Application {

    private static Context sContext;
    public static int HEIGHT,WIDTH;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=getApplicationContext();
        getScreen(sContext);
    }

    public static Context getContext(){
        return sContext;
    }

    public void getScreen(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        HEIGHT=dm.heightPixels;
        WIDTH=dm.widthPixels;
    }
}
