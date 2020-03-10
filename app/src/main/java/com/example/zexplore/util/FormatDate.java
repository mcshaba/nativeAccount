package com.example.zexplore.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Created by mishael.harry on 3/24/2018.
 */

public class FormatDate {
    @TypeConverter
    public String formateDate(Date date){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd/kk:mm:ss");
        return format.format(date);
    }

}
