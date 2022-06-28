

package com.example.taskmaster.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class TaskMasterConverters {

    @TypeConverter
    public static Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
        // FYI: there are two Date classes: one Java, another SQL
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
