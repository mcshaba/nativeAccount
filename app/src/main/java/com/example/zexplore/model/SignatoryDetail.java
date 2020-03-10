package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignatoryDetail {

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getMeansOfId() {
        return meansOfId;
    }

    public void setMeansOfId(String meansOfId) {
        this.meansOfId = meansOfId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdIssuer() {
        return idIssuer;
    }

    public void setIdIssuer(String idIssuer) {
        this.idIssuer = idIssuer;
    }

    public String getIdPlaceOfIssue() {
        return idPlaceOfIssue;
    }

    public void setIdPlaceOfIssue(String idPlaceOfIssue) {
        this.idPlaceOfIssue = idPlaceOfIssue;
    }

    public String getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(String idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public String getCerpacRPIdNo() {
        return cerpacRPIdNo;
    }

    public void setCerpacRPIdNo(String cerpacRPIdNo) {
        this.cerpacRPIdNo = cerpacRPIdNo;
    }

    public String getCerpacRPplaceofIssue() {
        return cerpacRPplaceofIssue;
    }

    public void setCerpacRPplaceofIssue(String cerpacRPplaceofIssue) {
        this.cerpacRPplaceofIssue = cerpacRPplaceofIssue;
    }

    public String getCerpacRPIssueAuth() {
        return cerpacRPIssueAuth;
    }

    public void setCerpacRPIssueAuth(String cerpacRPIssueAuth) {
        this.cerpacRPIssueAuth = cerpacRPIssueAuth;
    }

    public String getVisaNo() {
        return visaNo;
    }

    public void setVisaNo(String visaNo) {
        this.visaNo = visaNo;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getPermitValidFrom() {
        return permitValidFrom;
    }

    public void setPermitValidFrom(String permitValidFrom) {
        this.permitValidFrom = permitValidFrom;
    }

    public String getPermitValidTo() {
        return permitValidTo;
    }

    public void setPermitValidTo(String permitValidTo) {
        this.permitValidTo = permitValidTo;
    }

    public String getForeignAddress1() {
        return foreignAddress1;
    }

    public void setForeignAddress1(String foreignAddress1) {
        this.foreignAddress1 = foreignAddress1;
    }

    public String getForeignAddress2() {
        return foreignAddress2;
    }

    public void setForeignAddress2(String foreignAddress2) {
        this.foreignAddress2 = foreignAddress2;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(String studentLevel) {
        this.studentLevel = studentLevel;
    }

    public String getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(String yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAmlCustType() {
        return amlCustType;
    }

    public void setAmlCustType(int amlCustType) {
        this.amlCustType = amlCustType;
    }

    public int getAmlCustNature() {
        return amlCustNature;
    }

    public void setAmlCustNature(int amlCustNature) {
        this.amlCustNature = amlCustNature;
    }

    public int getAmlCustNatureBusiness() {
        return amlCustNatureBusiness;
    }

    public void setAmlCustNatureBusiness(int amlCustNatureBusiness) {
        this.amlCustNatureBusiness = amlCustNatureBusiness;
    }

    public String getUseEmailForStatement() {
        return useEmailForStatement;
    }

    public void setUseEmailForStatement(String useEmailForStatement) {
        this.useEmailForStatement = useEmailForStatement;
    }

    public String getPassportUrl() {
        return passportUrl;
    }

    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public String getUtilityUrl() {
        return utilityUrl;
    }

    public void setUtilityUrl(String utilityUrl) {
        this.utilityUrl = utilityUrl;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
