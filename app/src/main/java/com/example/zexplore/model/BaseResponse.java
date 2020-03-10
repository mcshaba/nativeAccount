package com.example.zexplore.model;

import com.google.gson.annotations.Expose;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class BaseResponse {

    @PrimaryKey
    private int id;
    @Expose
    private String name;

    @Ignore
    @Expose
    private boolean succeeded;
    @Ignore
    @Expose
    private Object results;
    @Ignore
    @Expose
    private String message;

    public BaseResponse(){
    }

    @Ignore
    public BaseResponse(String name) {
        this.name = name;
    }

    @Ignore
    public BaseResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }


}
