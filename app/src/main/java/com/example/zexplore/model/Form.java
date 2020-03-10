package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Form implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
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
    @SerializedName("RiskRank")
    private String riskRank;
    @Expose
    @SerializedName("ClassCode")
    private int classCode;
    @Expose
    @SerializedName("AccountClass")
    private String accountClass;
    @Expose
    @SerializedName("BranchNumber")
    private String branchNumber;
    @Expose
    @SerializedName("Ref_Id")
    private String refId;
    @Expose
    @SerializedName("RSMId")
    private String rsmId;
    @Expose
    @SerializedName("Bvn")
    private String bvn;
    @Expose
    @SerializedName("Title")
    private String title;
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
    @SerializedName("MotherMaidenName")
    private String motherMaidenName;
    @Expose
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @Expose
    @SerializedName("Sex")
    private String sex;
    @Expose
    @SerializedName("StateOfOrigin")
    private String stateOfOrigin;
    @Expose
    @SerializedName("CountryOfOrigin")
    private String countryOfOrigin;
    @Expose
    @SerializedName("EmailAddress")
    private String emailAddress;
    @Expose
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @Expose
    @SerializedName("Address1")
    private String address1;
    @Expose
    @SerializedName("Address2")
    private String address2;
    @Expose
    @SerializedName("City")
    private String cityOfResidence;
    @Expose
    @SerializedName("State")
    private String stateOfResidence;
    @Expose
    @SerializedName("Country")
    private String countryOfResidence;
    @Expose
    @SerializedName("Occupation")
    private String occupation;
    @Expose
    @SerializedName("MaritalStatus")
    private String maritalStatus;
    @Expose
    @SerializedName("NextOfKin")
    private String nextOfKin;
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
    @SerializedName("PassportUrl")
    private String passportUrl;
    @Expose
    @SerializedName("SignatureUrl")
    private String signatureUrl;
    @Expose
    @SerializedName("UtilityUrl")
    private String utilityUrl;
    @Expose
    @SerializedName("AccountName")
    private String accountName;
    @Expose
    @SerializedName("BatchId")
    private String    batchId;
    @Expose
    @SerializedName("TIN")
    private String tin;
    @Expose
    @SerializedName("RegistrationNumber")
    private String registrationNumber;
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
    @SerializedName("Fax")
    private String fax;
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
    @Ignore
    @Expose
    @SerializedName("Attachments")
    private List<Attachment> attachments;

    private int    sync;
    private int    status;
    private Date createdOn;
    private long imageIdentifier;
    private String accountTypeName;
    private int accountTypePosition;
    private int accountHolderPosition;
    private int riskRankPosition;
    private int accountClassPosition;
    private int titlePosition;
    private int sexPosition;
    private int stateOfOriginPosition;
    private int countryOfOriginPosition;
    private int cityOfResidencePosition;
    private int stateOfResidencePosition;
    private int countryOfResidencePosition;
    private int occupationPosition;
    private int maritalStatusPosition;
    private int meansOfIdPosition;
    private int idPlaceOfIssuePosition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getRiskRank() {
        return riskRank;
    }

    public void setRiskRank(String riskRank) {
        this.riskRank = riskRank;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public String getAccountClass() {
        return accountClass;
    }

    public void setAccountClass(String accountClass) {
        this.accountClass = accountClass;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getRsmId() {
        return rsmId;
    }

    public void setRsmId(String rsmId) {
        this.rsmId = rsmId;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return (middleName != null) ? middleName : "";
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

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return (sex != null) ? sex : "";
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getEmailAddress() {
        return (emailAddress != null) ? emailAddress : "";
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

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public long getImageIdentifier() {
        return imageIdentifier;
    }

    public void setImageIdentifier(long imageIdentifier) {
        this.imageIdentifier = imageIdentifier;
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

    public String getAlertZRequest() {
        return alertZRequest != null ? alertZRequest : "";

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

        return tokenRequest != null ? tokenRequest : "";

    }

    public void setTokenRequest(String tokenRequest) {
        this.tokenRequest = tokenRequest;
    }

    public String getIbankRequest() {
        return ibankRequest != null ? ibankRequest : "";

    }

    public void setIbankRequest(String ibankRequest) {
        this.ibankRequest = ibankRequest;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public int getAccountTypePosition() {
        return accountTypePosition;
    }

    public void setAccountTypePosition(int accountTypePosition) {
        this.accountTypePosition = accountTypePosition;
    }

    public int getAccountHolderPosition() {
        return accountHolderPosition;
    }

    public void setAccountHolderPosition(int accountHolderPosition) {
        this.accountHolderPosition = accountHolderPosition;
    }

    public int getRiskRankPosition() {
        return riskRankPosition;
    }

    public void setRiskRankPosition(int riskRankPosition) {
        this.riskRankPosition = riskRankPosition;
    }

    public int getAccountClassPosition() {
        return accountClassPosition;
    }

    public void setAccountClassPosition(int accountClassPosition) {
        this.accountClassPosition = accountClassPosition;
    }

    public int getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(int titlePosition) {
        this.titlePosition = titlePosition;
    }

    public int getSexPosition() {
        return sexPosition;
    }

    public void setSexPosition(int sexPosition) {
        this.sexPosition = sexPosition;
    }

    public int getStateOfOriginPosition() {
        return stateOfOriginPosition;
    }

    public void setStateOfOriginPosition(int stateOfOriginPosition) {
        this.stateOfOriginPosition = stateOfOriginPosition;
    }

    public int getCountryOfOriginPosition() {
        return countryOfOriginPosition;
    }

    public void setCountryOfOriginPosition(int countryOfOriginPosition) {
        this.countryOfOriginPosition = countryOfOriginPosition;
    }

    public int getCityOfResidencePosition() {
        return cityOfResidencePosition;
    }

    public void setCityOfResidencePosition(int cityOfResidencePosition) {
        this.cityOfResidencePosition = cityOfResidencePosition;
    }

    public int getStateOfResidencePosition() {
        return stateOfResidencePosition;
    }

    public void setStateOfResidencePosition(int stateOfResidencePosition) {
        this.stateOfResidencePosition = stateOfResidencePosition;
    }

    public int getCountryOfResidencePosition() {
        return countryOfResidencePosition;
    }

    public void setCountryOfResidencePosition(int countryOfResidencePosition) {
        this.countryOfResidencePosition = countryOfResidencePosition;
    }

    public int getOccupationPosition() {
        return occupationPosition;
    }

    public void setOccupationPosition(int occupationPosition) {
        this.occupationPosition = occupationPosition;
    }

    public int getMaritalStatusPosition() {
        return maritalStatusPosition;
    }

    public void setMaritalStatusPosition(int maritalStatusPosition) {
        this.maritalStatusPosition = maritalStatusPosition;
    }

    public int getMeansOfIdPosition() {
        return meansOfIdPosition;
    }

    public void setMeansOfIdPosition(int meansOfIdPosition) {
        this.meansOfIdPosition = meansOfIdPosition;
    }

    public int getIdPlaceOfIssuePosition() {
        return idPlaceOfIssuePosition;
    }

    public void setIdPlaceOfIssuePosition(int idPlaceOfIssuePosition) {
        this.idPlaceOfIssuePosition = idPlaceOfIssuePosition;
    }

    public List<Attachment> getAttachments() {
        return (attachments != null) ? attachments :new ArrayList<>();
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Form() {
    }
}
