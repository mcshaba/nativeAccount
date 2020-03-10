package com.example.zexplore.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mishael.harry on 3/28/2018.
 */

public class Login implements Serializable{

    //Request variables
    @Expose
    @SerializedName("UserName")
    private String username;
    @Expose
    @SerializedName("Password")
    private String password;

//    //Response object
//    @Expose
//    @SerializedName("User")
//    private User user;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
