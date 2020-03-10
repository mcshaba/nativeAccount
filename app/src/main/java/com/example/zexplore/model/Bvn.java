package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Ignore;

public class Bvn implements Serializable {


    //Request Variable
    @Expose
    @SerializedName("BvnNew")
    private String bvnNew;

    //Response Variable
    @Expose
    @SerializedName("ResponseCode")
    @Ignore
    private String responseCode;

    @Expose
    @SerializedName("ResponseMessage")
    @Ignore
    private String responseMessage;


    @Expose
    @SerializedName("MiddleName")
    private String middleName;
    @Expose
    @SerializedName("Nationality")
    private String nationality;
    @Expose
    @SerializedName("FirstName")
    private String firstName;
    @Expose
    @SerializedName("Email")
    private String email;
    @Expose
    @SerializedName("LastName")
    private String lastName;
    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("Bvn")
    private String bvn;
    @Expose
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @Expose
    @SerializedName("Base64Image")
    private String base64Image;
    @Expose
    @SerializedName("StateOfOrigin")
    private String stateOfOrigin;
    @Expose
    @SerializedName("MaritalStatus")
    private String maritalStatus;
    @Expose
    @SerializedName("ResidentialAddress")
    private String residentialAddress;
    @Expose
    @SerializedName("StateOfResidence")
    private String stateOfResidential;

    public String getMiddleName() {
        return middleName;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getStateOfResidential() {
        return stateOfResidential;
    }

    public void setStateOfResidential(String stateOfResidential) {
        this.stateOfResidential = stateOfResidential;
    }

    public String getGender() {
        return gender;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setBvnNew(String bvnNew) {
        this.bvnNew = bvnNew;
    }


}
