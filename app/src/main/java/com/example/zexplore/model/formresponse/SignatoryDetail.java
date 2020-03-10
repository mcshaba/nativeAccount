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

public class SignatoryDetail {

    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("MiddleName")
    @Expose
    private String middleName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Sex")
    @Expose
    private String sex;
    @SerializedName("DateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("MotherMaidenName")
    @Expose
    private String motherMaidenName;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("StateOfOrigin")
    @Expose
    private String stateOfOrigin;
    @SerializedName("CountryOfOrigin")
    @Expose
    private String countryOfOrigin;
    @SerializedName("MeansOfId")
    @Expose
    private String meansOfId;
    @SerializedName("IdNumber")
    @Expose
    private String idNumber;
    @SerializedName("IdIssuer")
    @Expose
    private String idIssuer;
    @SerializedName("IdPlaceOfIssue")
    @Expose
    private String idPlaceOfIssue;
    @SerializedName("IdIssueDate")
    @Expose
    private String idIssueDate;
    @SerializedName("IdExpiryDate")
    @Expose
    private String idExpiryDate;
    @SerializedName("Occupation")
    @Expose
    private String occupation;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("AddressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("Fax")
    @Expose
    private Object fax;
    @SerializedName("TIN")
    @Expose
    private Object tIN;
    @SerializedName("PermitType")
    @Expose
    private Object permitType;
    @SerializedName("CerpacRPIdNo")
    @Expose
    private Object cerpacRPIdNo;
    @SerializedName("CerpacRPplaceofIssue")
    @Expose
    private Object cerpacRPplaceofIssue;
    @SerializedName("CerpacRPIssueAuth")
    @Expose
    private Object cerpacRPIssueAuth;
    @SerializedName("VisaNo")
    @Expose
    private Object visaNo;
    @SerializedName("ArrivalDate")
    @Expose
    private String arrivalDate;
    @SerializedName("PermitValidFrom")
    @Expose
    private String permitValidFrom;
    @SerializedName("PermitValidTo")
    @Expose
    private String permitValidTo;
    @SerializedName("ForeignAddress1")
    @Expose
    private Object foreignAddress1;
    @SerializedName("ForeignAddress2")
    @Expose
    private Object foreignAddress2;
    @SerializedName("StudentLevel")
    @Expose
    private String studentLevel;
    @SerializedName("YearOfGraduation")
    @Expose
    private String yearOfGraduation;
    @SerializedName("Faculty")
    @Expose
    private Object faculty;
    @SerializedName("Department")
    @Expose
    private Object department;
    @SerializedName("AmlCustType")
    @Expose
    private String amlCustType;
    @SerializedName("AmlCustNature")
    @Expose
    private String amlCustNature;
    @SerializedName("AmlCustNatureBusiness")
    @Expose
    private String amlCustNatureBusiness;
    @SerializedName("UseEmailForStatement")
    @Expose
    private String useEmailForStatement;
    @SerializedName("PassportUrl")
    @Expose
    private Object passportUrl;
    @SerializedName("SignatureUrl")
    @Expose
    private Object signatureUrl;
    @SerializedName("UtilityUrl")
    @Expose
    private Object utilityUrl;
    @SerializedName("Bvn")
    @Expose
    private String bvn;
    @SerializedName("MaritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("NextOfKin")
    @Expose
    private String nextOfKin;
    @SerializedName("Attachments")
    @Expose
    private List<Attachment> attachments = null;


    @Ignore
    CryptLib _crypt;
    @Ignore
    String key;
    @Ignore
    String iv;


    public SignatoryDetail() {
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

    public String getFirstName() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && firstName != null) {
            return _crypt.decrypt(firstName, key, iv);
        }
        return "";

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }

    public String getMiddleName() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && middleName != null) {
            return _crypt.decrypt(middleName, key, iv);
        }
        return "";
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && lastName != null) {
            return _crypt.decrypt(lastName, key, iv);
        }
        return "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        if (sex == null)
            return "";

        if (sex.equals("M")) {
            return "MALE";
        } else {
            return "FEMALE";
        }
    }

    public void setSex(String sex) {


        this.sex = sex;
    }

    public String getDateOfBirth() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && dateOfBirth != null) {
            return _crypt.decrypt(dateOfBirth, key, iv);
        }
        return "";
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMotherMaidenName() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && motherMaidenName != null) {
            return _crypt.decrypt(motherMaidenName, key, iv);
        }
        return "";
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getTitle() {

        return title != null ? title : "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin != null ? stateOfOrigin : "";

    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin != null ? countryOfOrigin : "";

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
        return occupation != null ? occupation : "";

    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddressLine1() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && addressLine1 != null) {
            return _crypt.decrypt(addressLine1, key, iv);
        }
        return "";
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && addressLine2 != null) {
            return _crypt.decrypt(addressLine2, key, iv);
        }
        return "";
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city != null ? city : "";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state != null ? state : "";

    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmailAddress() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && emailAddress != null) {
            return _crypt.decrypt(emailAddress, key, iv);
        }
        return "";
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && phoneNumber != null) {
            return _crypt.decrypt(phoneNumber, key, iv);
        }
        return "";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public Object getTIN() {
        return tIN;
    }

    public void setTIN(Object tIN) {
        this.tIN = tIN;
    }

    public Object getPermitType() {
        return permitType;
    }

    public void setPermitType(Object permitType) {
        this.permitType = permitType;
    }

    public Object getCerpacRPIdNo() {
        return cerpacRPIdNo;
    }

    public void setCerpacRPIdNo(Object cerpacRPIdNo) {
        this.cerpacRPIdNo = cerpacRPIdNo;
    }

    public Object getCerpacRPplaceofIssue() {
        return cerpacRPplaceofIssue;
    }

    public void setCerpacRPplaceofIssue(Object cerpacRPplaceofIssue) {
        this.cerpacRPplaceofIssue = cerpacRPplaceofIssue;
    }

    public Object getCerpacRPIssueAuth() {
        return cerpacRPIssueAuth;
    }

    public void setCerpacRPIssueAuth(Object cerpacRPIssueAuth) {
        this.cerpacRPIssueAuth = cerpacRPIssueAuth;
    }

    public Object getVisaNo() {
        return visaNo;
    }

    public void setVisaNo(Object visaNo) {
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

    public Object getForeignAddress1() {
        return foreignAddress1;
    }

    public void setForeignAddress1(Object foreignAddress1) {
        this.foreignAddress1 = foreignAddress1;
    }

    public Object getForeignAddress2() {
        return foreignAddress2;
    }

    public void setForeignAddress2(Object foreignAddress2) {
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

    public Object getFaculty() {
        return faculty;
    }

    public void setFaculty(Object faculty) {
        this.faculty = faculty;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
    }

    public String getAmlCustType() {
        return amlCustType;
    }

    public void setAmlCustType(String amlCustType) {
        this.amlCustType = amlCustType;
    }

    public String getAmlCustNature() {
        return amlCustNature;
    }

    public void setAmlCustNature(String amlCustNature) {
        this.amlCustNature = amlCustNature;
    }

    public String getAmlCustNatureBusiness() {
        return amlCustNatureBusiness;
    }

    public void setAmlCustNatureBusiness(String amlCustNatureBusiness) {
        this.amlCustNatureBusiness = amlCustNatureBusiness;
    }

    public String getUseEmailForStatement() {
        return useEmailForStatement;
    }

    public void setUseEmailForStatement(String useEmailForStatement) {
        this.useEmailForStatement = useEmailForStatement;
    }

    public Object getPassportUrl() {
        return passportUrl;
    }

    public void setPassportUrl(Object passportUrl) {
        this.passportUrl = passportUrl;
    }

    public Object getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(Object signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public Object getUtilityUrl() {
        return utilityUrl;
    }

    public void setUtilityUrl(Object utilityUrl) {
        this.utilityUrl = utilityUrl;
    }

    public String getBvn() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && bvn != null) {
            return _crypt.decrypt(bvn, key, iv);
        }
        return "";
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getMaritalStatus() {
        return maritalStatus != null ? maritalStatus : "";

    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNextOfKin() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {
        if (_crypt != null && nextOfKin != null) {
            return _crypt.decrypt(nextOfKin, key, iv);
        }
        return "";
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