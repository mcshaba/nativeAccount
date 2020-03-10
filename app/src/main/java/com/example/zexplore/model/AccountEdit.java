package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AccountEdit {


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
    @SerializedName("TIN")
    private String tin;
    @Expose
    @SerializedName("RegistrationNumber")
    private String registrationNumber;
    @Expose
    @SerializedName("Sex")
    private String sex;
    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @Expose
    @SerializedName("DateOfIncorporation")
    private String dateOfIncorporation;
    @Expose
    @SerializedName("BusinessNature")
    private String businessNature;
    @Expose
    @SerializedName("Sector")
    private String sector;
    @Expose
    @SerializedName("Industry")
    private String industry;
    @Expose
    @SerializedName("RiskRank")
    private String riskRank;
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
    @SerializedName("CountryOfOrigin")
    private String countryOfOrigin;
    @Expose
    @SerializedName("AlertZRequest")
    private String alertZRequest;
    @Expose
    @SerializedName("MasterCardRequest")
    private String masterCardRequest;
    @Expose
    @SerializedName("VisaCardRequest")
    private String visaCardRequest;
    @Expose
    @SerializedName("VerveCardRequest")
    private String verveCardRequest;
    @Expose
    @SerializedName("TokenRequest")
    private String tokenRequest;
    @Expose
    @SerializedName("IbankRequest")
    private String ibankRequest;
    @Expose
    @SerializedName("SignatoryDetails")
    List<SignatoryDetail> signatoryDetails;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderType() {
        return accountHolderType;
    }

    public void setAccountHolderType(String accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getRsmId() {
        return rsmId;
    }

    public void setRsmId(String rsmId) {
        this.rsmId = rsmId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(String dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRiskRank() {
        return riskRank;
    }

    public void setRiskRank(String riskRank) {
        this.riskRank = riskRank;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCityOfResidence() {
        return cityOfResidence;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }

    public String getStateOfResidence() {
        return stateOfResidence;
    }

    public void setStateOfResidence(String stateOfResidence) {
        this.stateOfResidence = stateOfResidence;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getAlertZRequest() {
        return alertZRequest;
    }

    public void setAlertZRequest(String alertZRequest) {
        this.alertZRequest = alertZRequest;
    }

    public String getMasterCardRequest() {
        return masterCardRequest;
    }

    public void setMasterCardRequest(String masterCardRequest) {
        this.masterCardRequest = masterCardRequest;
    }

    public String getVisaCardRequest() {
        return visaCardRequest;
    }

    public void setVisaCardRequest(String visaCardRequest) {
        this.visaCardRequest = visaCardRequest;
    }

    public String getVerveCardRequest() {
        return verveCardRequest;
    }

    public void setVerveCardRequest(String verveCardRequest) {
        this.verveCardRequest = verveCardRequest;
    }

    public String getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(String tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    public String getIbankRequest() {
        return ibankRequest;
    }

    public void setIbankRequest(String ibankRequest) {
        this.ibankRequest = ibankRequest;
    }

    public List<SignatoryDetail> getSignatoryDetails() {
        return signatoryDetails;
    }

    public void setSignatoryDetails(List<SignatoryDetail> signatoryDetails) {
        this.signatoryDetails = signatoryDetails;
    }
}
