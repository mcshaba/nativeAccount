package com.example.zexplore;

import android.app.Application;

/**
 * Created by Ehigiator David on 12/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
public class ZexploreApp extends Application {

    private static ZexploreApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }

    public static Application getInstance(){
        return app;
    }


}
