package com.example.zexplore.util;

import android.content.Context;

import com.example.zexplore.model.Login;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by mishael.harry on 3/21/2018.
 */

public class AppSettings implements Serializable{
    private static final long serialVersionUID = 1L;
    String FILENAME = "userconfig";
    private LoginResponse login;
    private User user;
    private String token;

    public AppSettings() {
    }

    public LoginResponse getLogin() {
        return login;
    }

    public void setLogin(LoginResponse login) {
        this.login = login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private static AppSettings ourInstance;
    public static AppSettings getInstance(Context context){
        if(ourInstance==null)ourInstance = AppSettings.Fetch(context);
        return ourInstance;
    }

    public void removeUser(Context context){
        login = null;
        Save(context);
    }

    public void Save(Context context)
    {
        Save(context, this);
    }

    private void Save(Context context, AppSettings appSettings)
    {
        try{
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(appSettings);
            oos.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static AppSettings Fetch(Context context)
    {
        AppSettings appSettings = null;
        try
        {
            FileInputStream fis = context.openFileInput("appsettings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            appSettings = (AppSettings) ois.readObject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if (appSettings == null){
            appSettings =  new AppSettings();
        }
        return appSettings;
    }

}
