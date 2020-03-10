package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class ZenithBaseResponse<T> {

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("Status")
    private boolean status;


    @Expose
    @SerializedName("ResponseCode")
    @Ignore
    private String responseCode;


    @Expose
    @SerializedName("ResponseMessage")
    @Ignore
    private String responseMessage;

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("Description")
    private String description;

    @Expose
    @SerializedName("AccountClassCodes")
    @Ignore
    private T result;

    @Expose
    @SerializedName("AccountNumber")
    @Ignore
    private String accountNumber;


    public ZenithBaseResponse() {
    }

    @Ignore
    public ZenithBaseResponse(int id){
        this.id = id;
    }

    @Ignore
    public ZenithBaseResponse(String name) {
        this.name = name;
    }

    @Ignore
    public ZenithBaseResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public T getResult() {
        return result;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
