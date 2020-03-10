package com.example.zexplore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class State extends ZenithBaseResponse{

    @Expose
    @SerializedName("Menu")
    @Ignore
    private List<String> menu;

    public State() {
    }

    @Ignore
    public State(String name){
        super(name);
    }

    @Ignore
    public State(int id, String name){
        super(id, name);
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

}
