package com.example.zexplore.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        try
//        {
//            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) ||
//                    "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction()) ||
//                    "com.htc.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {
//
//                Intent serviceIntent = new Intent(context, ZxploreService.class);
//                context.startService(serviceIntent);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }
}
