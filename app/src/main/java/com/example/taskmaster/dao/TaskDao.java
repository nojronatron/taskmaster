package com.example.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskmaster.models.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {
    // CRUD: Create
    @Insert
    public void insertSingleTask(TaskModel task);

    // CRUD: READ
    @Query("SELECT * FROM Task")
    public List<TaskModel> findAll();

    // CRUD: READ
    @Query("SELECT * FROM Task ORDER BY name ASC")
    public List<TaskModel> findAllSortedByName();

    // CRUD: READ
    @Query("SELECT * FROM Task WHERE id = :id")
    public TaskModel findByAnId(Long id);
}
