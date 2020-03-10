package com.example.zexplore.model.formModels;

/**
 * Created by Ehigiator David on 08/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
public class PersonalInformationModel {

    private String  bvn;

    private String title;

    private String SurName;

    private String firstName;

    private String otherName;

    private String mothersName;

    private String mothersMaidenName;

    private String dateOfBirth;

    private String stateOrigin;

    private String countryOfOrigin;

    public PersonalInformationModel(String bvn, String title, String surName, String firstName, String otherName, String mothersName, String mothersMaidenName, String dateOfBirth, String stateOrigin, String countryOfOrigin) {
        this.bvn = bvn;
        this.title = title;
        SurName = surName;
        this.firstName = firstName;
        this.otherName = otherName;
        this.mothersName = mothersName;
        this.mothersMaidenName = mothersMaidenName;
        this.dateOfBirth = dateOfBirth;
        this.stateOrigin = stateOrigin;
        this.countryOfOrigin = countryOfOrigin;
    }


    public String getBvn() {
        return bvn;
    }

    public String getTitle() {
        return title;
    }

    public String getSurName() {
        return SurName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getOtherName() {
        return otherName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStateOrigin() {
        return stateOrigin;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
}
