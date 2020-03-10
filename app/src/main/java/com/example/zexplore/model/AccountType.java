package com.example.zexplore.model;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class AccountType extends BaseResponse implements Serializable {
    private String typeCode;

    @Ignore
    public AccountType(int id, String name, String typeCode) {
        super(id, name);
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

}


