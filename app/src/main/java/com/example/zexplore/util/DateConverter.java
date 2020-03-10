package com.example.zexplore.util;


import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Created by mishael.harry on 3/23/2018.
 */

public class DateConverter {
    @TypeConverter
    public Date timestampToData(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
