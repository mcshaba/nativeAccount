package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Attachment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("Id")
    private int id;
    @SerializedName("FormId")
    private long formId;
    @SerializedName("Image")
    private String image;
    @Expose
    @SerializedName("EncodedImage")
    private String encodedImage;
    @Expose
    @SerializedName("Type")
    private String type;

    @Ignore
    public Attachment(String image, String type) {
        this.image = image;
        this.type = type;
    }

    public Attachment() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

}
