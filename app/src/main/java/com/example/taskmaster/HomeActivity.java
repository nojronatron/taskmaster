package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    // declare public static final type fields to create keys to associate with preference editor store
    public static final String taskTitle = null;

    // declare shared preferences for storing data
    SharedPreferences preferences;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set onclick button event handling to add a task
        Button addTaskButton = HomeActivity.this.findViewById(R.id.homeAddTaskButton);

        addTaskButton.setOnClickListener(view -> {
            Intent goToAddTaskActivity = new Intent(HomeActivity.this, AddTask.class);
            startActivity(goToAddTaskActivity);
        });

        // Set onclick button event handling to all tasks
        Button allTasksButton = HomeActivity.this.findViewById(R.id.homeAllTasksButton);

        allTasksButton.setOnClickListener(view -> {
            Intent goToAllTasksActivity = new Intent(HomeActivity.this, AllTasks.class);
            startActivity(goToAllTasksActivity);
        });
    }
}