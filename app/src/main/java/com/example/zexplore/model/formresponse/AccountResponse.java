package com.example.zexplore.model.formresponse;

/**
 * Created by Ehigiator David on 08/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
