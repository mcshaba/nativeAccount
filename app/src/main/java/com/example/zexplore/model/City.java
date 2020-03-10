package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class City extends ZenithBaseResponse {
    @Expose
    @SerializedName("Menu")
    @Ignore
    private List<String> menu;

    public City() {
    }

    @Ignore
    public City(String name){
        super(name);
    }

    @Ignore
    public City(int id, String name){
        super(id, name);
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

}
