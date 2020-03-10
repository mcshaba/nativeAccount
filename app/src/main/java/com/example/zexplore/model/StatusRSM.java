package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Ignore;

public class StatusRSM implements Serializable {


    //Request Variable
    @Expose
    @SerializedName("RSMID")
    private String rsmId;

    @Expose
    @SerializedName("Status")
    private String status;

    public String getRsmId() {
        return rsmId;
    }

    public void setRsmId(String rsmId) {
        this.rsmId = rsmId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
