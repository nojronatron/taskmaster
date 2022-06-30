

package com.example.taskmaster.database;


import java.util.Date;

public class TaskMasterConverters {

    public static Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
        // FYI: there are two Date classes: one Java, another SQL
    }

    public static Long toTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
