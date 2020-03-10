package com.example.zexplore.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class ConnectivityReceiver extends BroadcastReceiver {

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int[] type = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        if (isNetworkAvailable(context, type) == true){
            return;
        } else {
            Toast.makeText(context, "No Internet, you are Offline", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isNetworkAvailable(Context context, int[] typeNetworks){
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            for (int typeNetwork : typeNetworks){
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(typeNetwork);
                if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
