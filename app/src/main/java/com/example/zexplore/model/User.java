package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @Expose
    @SerializedName("UserName")
    private String username;
    @Expose
    @SerializedName("AdUsername")
    private String adUsername;
    @Expose
    @SerializedName("EmployeeId")
    private String employeeId;
    @Expose
    @SerializedName("Branch")
    private String branch;
    @Expose
    @SerializedName("BranchNumber")
    private String branchNumber;
    @Expose
    @SerializedName("IsActive")
    private boolean isActive;
    @Expose
    @SerializedName("Role")
    private String role;
    @Expose
    @SerializedName("Token")
    private String token;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getToken() {
        return token;
    }

}
