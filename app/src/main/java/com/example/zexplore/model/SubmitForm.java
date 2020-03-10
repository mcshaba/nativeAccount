package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitForm {

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
    List<SignatoryDetails> signatoryDetails;


    public SubmitForm(Form form, List<SignatoryDetails> signatoryDetails) {
        this.accountType = form.getAccountType();
        this.accountNumber = form.getAccountNumber();
        this.accountHolderType = form.getAccountHolderType();
        this.classCode = form.getClassCode();
        this.branchNumber = form.getBranchNumber();
        this.phoneNumber = form.getPhoneNumber();
        this.refId = form.getRefId();
        this.rsmId = form.getRsmId();
        this.accountName = form.getAccountName();
        this.tin = form.getTin();
        this.registrationNumber = form.getRegistrationNumber();
        this.sex = form.getSex();
        this.title = form.getTitle();
        this.dateOfBirth = form.getDateOfBirth();
        this.dateOfIncorporation = form.getDateOfIncorporation();
        this.businessNature = form.getBusinessNature();
        this.sector = form.getSector();
        this.industry = form.getIndustry();
        this.riskRank = form.getRiskRank();
        this.address1 = form.getAddress1();
        this.cityOfResidence = form.getCityOfResidence();
        this.stateOfResidence = form.getStateOfResidence();
        this.countryOfOrigin = form.getCountryOfOrigin();
        this.alertZRequest = form.getAlertZRequest();
        this.masterCardRequest = form.getMasterCardRequest();
        this.visaCardRequest = form.getVisaCardRequest();
        this.verveCardRequest = form.getVerveCardRequest();
        this.tokenRequest = form.getTokenRequest();
        this.ibankRequest = form.getIbankRequest();
        this.signatoryDetails = signatoryDetails;
    }
}
