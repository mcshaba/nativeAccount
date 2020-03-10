package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AccountModel implements Serializable {

    @Expose
    @SerializedName("AccountType")
    private String accountType;
    @Expose
    @SerializedName("AccountNumber")
    private String accountNumber;
    @Expose
    @SerializedName("AccountHolderType")
    private String accountHolderType;
    @Expose
    @SerializedName("ClassCode")
    private int classCode;
    @Expose
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @Expose
    @SerializedName("Ref_Id")
    private String refId;
    @Expose
    @SerializedName("BranchNumber")
    private String branchNumber;
    @Expose
    @SerializedName("RSMId")
    private String rsmId;
    @Expose
    @SerializedName("AccountName")
    private String accountName;
    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("AddressLine1")
    private String address1;
    @Expose
    @SerializedName("City")
    private String cityOfResidence;
    @Expose
    @SerializedName("State")
    private String stateOfResidence;
    @Expose
    @SerializedName("Status")
    private String Status;

    @Expose
    @SerializedName("DateCreated")
    private String dateCreated;

    public AccountModel() {
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderType() {
        return accountHolderType;
    }

    public int getClassCode() {
        return classCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRefId() {
        return refId;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public String getRsmId() {
        return rsmId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress1() {
        return address1;
    }

    public String getCityOfResidence() {
        return cityOfResidence;
    }

    public String getStateOfResidence() {
        return stateOfResidence;
    }

    public String getStatus() {
        return Status;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountHolderType(String accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public void setRsmId(String rsmId) {
        this.rsmId = rsmId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }

    public void setStateOfResidence(String stateOfResidence) {
        this.stateOfResidence = stateOfResidence;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
