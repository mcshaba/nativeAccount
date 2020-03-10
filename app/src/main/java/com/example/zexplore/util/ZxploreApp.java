package com.example.zexplore.util;

import android.app.Application;
import android.content.Context;

public class ZxploreApp extends Application {
    private static ZxploreApp instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static ZxploreApp getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }
}
