package com.example.zexplore.model.formresponse;

/**
 * Created by Ehigiator David on 08/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import androidx.room.Ignore;

public class Data {

    @SerializedName("AccountType")
    @Expose
    private String accountType;
    @SerializedName("AccountNumber")
    @Expose
    private Object accountNumber;
    @SerializedName("AccountHolderType")
    @Expose
    private String accountHolderType;
    @SerializedName("ClassCode")
    @Expose
    private String classCode;
    @SerializedName("BranchNumber")
    @Expose
    private String branchNumber;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("RSMId")
    @Expose
    private String rSMId;
    @SerializedName("AccountName")
    @Expose
    private String accountName;
    @SerializedName("TIN")
    @Expose
    private Object tIN;
    @SerializedName("RegistrationNumber")
    @Expose
    private Object registrationNumber;
    @SerializedName("Sex")
    @Expose
    private String sex;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("DateOfIncorporation")
    @Expose
    private Object dateOfIncorporation;
    @SerializedName("BusinessNature")
    @Expose
    private Object businessNature;
    @SerializedName("Sector")
    @Expose
    private Object sector;
    @SerializedName("Industry")
    @Expose
    private Object industry;
    @SerializedName("RiskRank")
    @Expose
    private String riskRank;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("CountryOfOrigin")
    @Expose
    private String countryOfOrigin;
    @SerializedName("SignatoryDetails")
    @Expose
    private List<SignatoryDetail> signatoryDetails = null;
    @SerializedName("Ref_Id")
    @Expose
    private String refId;
    @SerializedName("AlertZRequest")
    @Expose
    private String alertZRequest;
    @SerializedName("MasterCardRequest")
    @Expose
    private String masterCardRequest;
    @SerializedName("VisaCardRequest")
    @Expose
    private String visaCardRequest;
    @SerializedName("VerveCardRequest")
    @Expose
    private String verveCardRequest;
    @SerializedName("TokenRequest")
    @Expose
    private String tokenRequest;
    @SerializedName("IbankRequest")
    @Expose
    private String ibankRequest;


    @Ignore
    CryptLib _crypt;
    @Ignore
    String key;
    @Ignore
    String iv;


    public Data() {
        try {
            _crypt = new CryptLib();
            key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
            iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Object getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Object accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderType() {
        return accountHolderType;
    }

    public void setAccountHolderType(String accountHolderType) {
        this.accountHolderType = accountHolderType;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRSMId() {
        return rSMId;
    }

    public void setRSMId(String rSMId) {
        this.rSMId = rSMId;
    }

    //decrypt.
    public String getAccountName() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {

        if (_crypt != null && accountName != null) {
            return _crypt.decrypt(accountName, key, iv);
        }
        return "";
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Object getTIN() {
        return tIN;
    }

    public void setTIN(Object tIN) {
        this.tIN = tIN;
    }

    public Object getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Object registrationNumber) {
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

    public Object getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    public void setDateOfIncorporation(Object dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }

    public Object getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(Object businessNature) {
        this.businessNature = businessNature;
    }

    public Object getSector() {
        return sector;
    }

    public void setSector(Object sector) {
        this.sector = sector;
    }

    public Object getIndustry() {
        return industry;
    }

    public void setIndustry(Object industry) {
        this.industry = industry;
    }

    public String getRiskRank() {
        return riskRank;
    }

    public void setRiskRank(String riskRank) {
        this.riskRank = riskRank;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public List<SignatoryDetail> getSignatoryDetails() {
        return signatoryDetails;
    }

    public void setSignatoryDetails(List<SignatoryDetail> signatoryDetails) {
        this.signatoryDetails = signatoryDetails;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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

}