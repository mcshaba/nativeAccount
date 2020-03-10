package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class Occupation extends ZenithBaseResponse{

    @Expose
    @SerializedName("Menu")
    @Ignore
    private List<String> menu;

    public Occupation() {
    }

    @Ignore
    public Occupation(String name){
        super(name);
    }

    @Ignore
    public Occupation(int id, String name){
        super(id, name);
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

}
