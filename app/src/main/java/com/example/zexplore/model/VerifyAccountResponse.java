package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Ignore;

/**
 * Created by Ehigiator David on 08/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
public class VerifyAccountResponse {

    @Expose
    @SerializedName("status")
    @Ignore
    private String status;

    @Expose
    @SerializedName("message")
    @Ignore
    private String message;

    @Expose
    @SerializedName("data")
    @Ignore
    private Data data;


    public VerifyAccountResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}


