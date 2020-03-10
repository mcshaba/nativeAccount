package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SignatoryDetails implements Serializable {
    @Expose
    @SerializedName("FirstName")
    private String firstName;
    @Expose
    @SerializedName("MiddleName")
    private String middleName;
    @Expose
    @SerializedName("LastName")
    private String lastName;
    @Expose
    @SerializedName("Sex")
    private String sex;
    @Expose
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @Expose
    @SerializedName("MotherMaidenName")
    private String motherMaidenName;
    @Expose
    @SerializedName("Title")
    private String title;
    @Expose
    @SerializedName("StateOfOrigin")
    private String stateOfOrigin;
    @Expose
    @SerializedName("CountryOfOrigin")
    private String countryOfOrigin;
    @Expose
    @SerializedName("MeansOfId")
    private String meansOfId;
    @Expose
    @SerializedName("IdNumber")
    private String idNumber;
    @Expose
    @SerializedName("IdIssuer")
    private String idIssuer;
    @Expose
    @SerializedName("IdPlaceOfIssue")
    private String idPlaceOfIssue;
    @Expose
    @SerializedName("IdIssueDate")
    private String idIssueDate;
    @Expose
    @SerializedName("IdExpiryDate")
    private String idExpiryDate;
    @Expose
    @SerializedName("Occupation")
    private String occupation;
    @Expose
    @SerializedName("AddressLine1")
    private String address1;
    @Expose
    @SerializedName("AddressLine2")
    private String address2;
    @Expose
    @SerializedName("City")
    private String cityOfResidence;
    @Expose
    @SerializedName("State")
    private String stateOfResidence;
    @Expose
    @SerializedName("EmailAddress")
    private String emailAddress;
    @Expose
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @Expose
    @SerializedName("Fax")
    private String fax;
    @Expose
    @SerializedName("TIN")
    private String tin;
    @Expose
    @SerializedName("PermitType")
    private String permitType;
    @Expose
    @SerializedName("CerpacRPIdNo")
    private String cerpacRPIdNo;
    @Expose
    @SerializedName("CerpacRPplaceofIssue")
    private String cerpacRPplaceofIssue;
    @Expose
    @SerializedName("CerpacRPIssueAuth")
    private String cerpacRPIssueAuth;
    @Expose
    @SerializedName("VisaNo")
    private String visaNo;
    @Expose
    @SerializedName("ArrivalDate")
    private String arrivalDate;
    @Expose
    @SerializedName("PermitValidFrom")
    private String permitValidFrom;
    @Expose
    @SerializedName("PermitValidTo")
    private String permitValidTo;
    @Expose
    @SerializedName("ForeignAddress1")
    private String foreignAddress1;
    @Expose
    @SerializedName("ForeignAddress2")
    private String foreignAddress2;
    @Expose
    @SerializedName("StudentLevel")
    private String studentLevel;
    @Expose
    @SerializedName("YearOfGraduation")
    private String yearOfGraduation;
    @Expose
    @SerializedName("Faculty")
    private String faculty;
    @Expose
    @SerializedName("Department")
    private String department;
    @Expose
    @SerializedName("AmlCustType")
    private int amlCustType;
    @Expose
    @SerializedName("AmlCustNature")
    private int amlCustNature;
    @Expose
    @SerializedName("AmlCustNatureBusiness")
    private int amlCustNatureBusiness;
    @Expose
    @SerializedName("UseEmailForStatement")
    private String useEmailForStatement;
    @Expose
    @SerializedName("PassportUrl")
    private String passportUrl;
    @Expose
    @SerializedName("SignatureUrl")
    private String signatureUrl;
    @Expose
    @SerializedName("UtilityUrl")
    private String utilityUrl;
    @Expose
    @SerializedName("Bvn")
    private String bvn;
    @Expose
    @SerializedName("MaritalStatus")
    private String maritalStatus;
    @Expose
    @SerializedName("NextOfKin")
    private String nextOfKin;
    @Expose
    @SerializedName("Attachments")
    List<Attachment> attachments;

    public SignatoryDetails(Form form) {
        this.firstName = form.getFirstName();
        this.middleName = form.getMiddleName();
        this.lastName = form.getLastName();
        this.sex = form.getSex();
        this.dateOfBirth = form.getDateOfBirth();
        this.motherMaidenName = form.getMotherMaidenName();
        this.title = form.getTitle();
        this.stateOfOrigin = form.getStateOfOrigin();
        this.countryOfOrigin = form.getCountryOfOrigin();
        this.meansOfId = form.getMeansOfId();
        this.idNumber = form.getIdNumber();
        this.idIssuer = form.getIdIssuer();
        this.idPlaceOfIssue = form.getIdPlaceOfIssue();
        this.idIssueDate = form.getIdIssueDate();
        this.idExpiryDate = form.getIdExpiryDate();
        this.occupation = form.getOccupation();
        this.address1 = form.getAddress1();
        this.address2 = form.getAddress2();
        this.cityOfResidence = form.getCityOfResidence();
        this.stateOfResidence = form.getStateOfResidence();
        this.emailAddress = form.getEmailAddress();
        this.phoneNumber = form.getPhoneNumber();
        this.fax = form.getFax();
        this.tin = form.getTin();
        this.permitType = form.getPermitType();
        this.cerpacRPIdNo = form.getCerpacRPIdNo();
        this.cerpacRPplaceofIssue = form.getCerpacRPplaceofIssue();
        this.cerpacRPIssueAuth = form.getCerpacRPIssueAuth();
        this.visaNo = form.getVisaNo();
        this.arrivalDate = form.getArrivalDate();
        this.permitValidFrom = form.getPermitValidFrom();
        this.permitValidTo = form.getPermitValidTo();
        this.foreignAddress1 = form.getForeignAddress1();
        this.foreignAddress2 = form.getForeignAddress2();
        this.studentLevel = form.getStudentLevel();
        this.yearOfGraduation = form.getYearOfGraduation();
        this.faculty = form.getFaculty();
        this.department = form.getDepartment();
        this.amlCustType = form.getAmlCustType();
        this.amlCustNature = form.getAmlCustNature();
        this.amlCustNatureBusiness = form.getAmlCustNatureBusiness();
        this.useEmailForStatement = form.getUseEmailForStatement();
        this.passportUrl = form.getPassportUrl();
        this.signatureUrl = form.getSignatureUrl();
        this.utilityUrl = form.getUtilityUrl();
        this.bvn = form.getBvn();
        this.maritalStatus = form.getMaritalStatus();
        this.nextOfKin = form.getNextOfKin();
        this.attachments = form.getAttachments();
    }

}
