package com.example.taskmaster.database;

import androidx.room.Database;
import androidx.room.TypeConverters;

import com.example.taskmaster.dao.TaskDao;
import com.example.taskmaster.models.TaskModel;

@Database(entities= {TaskModel.class}, version=1) // remember version update replaces DB
@TypeConverters({dbConverters.class})
public abstract class TaskMasterDatabase {
    public abstract TaskDao taskDao();
}
