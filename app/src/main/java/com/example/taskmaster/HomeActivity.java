package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    // declare public static final type fields to create keys to associate with preference editor store
    public static final String TASK_TITLE = null;

    // declare shared preferences for storing data
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initialize shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

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

        // Set onclick button event handling to UserSettings Activity
        Button userSettingsButton = HomeActivity.this.findViewById(R.id.homeUserSettingsButton);

        userSettingsButton.setOnClickListener(view -> {
            Intent goToUserSettingsActivity = new Intent(HomeActivity.this, UserSettings.class);
            startActivity(goToUserSettingsActivity);
        });

        // get a reference to the first home task button
        Button homeTaskButton1 = HomeActivity.this.findViewById(R.id.homeTaskButton1);
        Button homeTaskButton2 = HomeActivity.this.findViewById(R.id.homeTaskButton2);
        Button homeTaskButton3 = HomeActivity.this.findViewById(R.id.homeTaskButton3);

        // Task List Buttons event handling
        homeTaskButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TaskDetailActivity.class);
                String selectedTaskNameText = homeTaskButton1.getText().toString();
                intent.putExtra(TASK_TITLE, selectedTaskNameText);
                startActivity(intent);
            }
        });

        homeTaskButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TaskDetailActivity.class);
                String selectedTaskNameText = homeTaskButton2.getText().toString();
                intent.putExtra(TASK_TITLE, selectedTaskNameText);
                startActivity(intent);
            }
        });

        homeTaskButton3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TaskDetailActivity.class);
                String selectedTaskNameText = homeTaskButton3.getText().toString();
                intent.putExtra(TASK_TITLE, selectedTaskNameText);
                startActivity(intent);
            }
        });
    }
}