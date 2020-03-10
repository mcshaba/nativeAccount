package com.example.zexplore.model;

import com.example.zexplore.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Data<T> implements Serializable {

    @Expose
    @SerializedName("User")
    private User user;

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("Description")
    private String description;

    @Expose
    @SerializedName("ResponseCode")
    private String responseCode;


    @Expose
    @SerializedName("ResponseMessage")
    private String responseMessage;

    @Expose
    @SerializedName("AccountClassCodes")
    @Ignore
    private T result;

    @Expose
    @SerializedName("AccountNumber")
    @Ignore
    private String accountNumber;

    @Expose
    @SerializedName("BatchId")
    private String batchId;

    @Expose
    @SerializedName("Status")
    @Ignore
    private String statuss;

    public Data() {
    }

    @Ignore
    public Data(int id){
        this.id = id;
    }

    @Ignore
    public Data(String name) {
        this.name = name;
    }

    @Ignore
    public Data(int id, String name) {
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


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public User getUser() {
        return user;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStatuss() {
        return statuss;
    }

}
