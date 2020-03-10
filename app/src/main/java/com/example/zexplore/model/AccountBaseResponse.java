package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AccountBaseResponse<T> {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("Status")
    private boolean status;

    @Expose
    @SerializedName("data")
    private T responseData;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public T getResponseData() {
        return responseData;
    }
}
