package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Entity;

@Entity
public class AccountClass extends ZenithBaseResponse implements Serializable {

    @Expose
    @SerializedName("ClassCode")
    private int classCode;
    @Expose
    @SerializedName("ClassType")
    private String classType;

    public AccountClass(int id, String name, int classCode) {
        super(id, name);
        this.classCode = classCode;
    }

    public int getClassCode() {
        return classCode;
    }

    public void setClassCode(int classCode) {
        this.classCode = classCode;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

}
