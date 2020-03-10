package com.example.zexplore.model;

import java.io.Serializable;

import androidx.room.Ignore;

public class Identification extends BaseResponse implements Serializable {
    @Ignore
    public Identification(int id, String name) {
        super(id, name);
    }

}
