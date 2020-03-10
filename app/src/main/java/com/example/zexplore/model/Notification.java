package com.example.zexplore.model;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long formId;
    private String title;
    private String message;
    private int type;
    private long status;
    private Date createdOn;

    @Ignore
    private Form form;

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFormId() {
        return formId;
    }

    public void setFormId(long formId) {
        this.formId = formId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public long getStatus() {
        return status;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public long isStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

}
