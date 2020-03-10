package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveAccountResponse extends ZenithBaseResponse implements Serializable {
    //Response object

    @Expose
    @SerializedName("data")
    private AccountEdit data;


    public AccountEdit getData() {
        return data;
    }

    public void setData(AccountEdit data) {
        this.data = data;
    }
}
