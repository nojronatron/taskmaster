package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import com.example.taskmaster.activites.AddTask;
import com.example.taskmaster.activites.AllTasks;
import com.example.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.example.taskmaster.database.TaskMasterDatabase;
import com.example.taskmaster.fragments.UserSettings;
import com.example.taskmaster.models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // declare public static final type fields to create keys to associate with preference editor store
    public static final String TASK_TITLE = null;
    public static final String TASK_STATE = null;
    public static final String TASK_BODY = null;
    public static final String SELECTED_TASK_DETAILS = null;

    // set a reference to the Room Database
    TaskMasterDatabase taskMasterDatabase;
    public static final String DATABASE_NAME = "task_master";

    // reference to store DB contents
    List<TaskModel> tasks = null;

    // declare shared preferences for storing data
    SharedPreferences preferences;

    // RecyclerView adapter reference
    TaskListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initialize shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // database builder
        taskMasterDatabase = Room.databaseBuilder(
                getApplicationContext(), // single db for all Activities
                TaskMasterDatabase.class,
                DATABASE_NAME
        )
                .allowMainThreadQueries() // for exploratory purposes only NOT production
                .fallbackToDestructiveMigration() // toss old DB and all data, start again not good for prod
                .build();

        // assign results of Dao call to findAll method to local tasks field
        tasks = taskMasterDatabase.taskDao().findAll();

        setupAddTaskButton();

        setupLoadAllTasksActivityButton();

        setUpUserSettingsButton();

        setUpTasksRecyclerView();
    }

    private void setupAddTaskButton() {
        // Set onclick button event handling to add a task
        Button addTaskButton = HomeActivity.this.findViewById(R.id.homeAddTaskButton);

        addTaskButton.setOnClickListener(view -> {
            Intent goToAddTaskActivity = new Intent(HomeActivity.this, AddTask.class);
            startActivity(goToAddTaskActivity);
        });
    }

    private void setupLoadAllTasksActivityButton() {
        // Set onclick button event handling to all tasks
        Button allTasksButton = HomeActivity.this.findViewById(R.id.homeAllTasksButton);

        allTasksButton.setOnClickListener(view -> {
            Intent goToAllTasksActivity = new Intent(HomeActivity.this, AllTasks.class);
            startActivity(goToAllTasksActivity);
        });
    }

    private void setUpUserSettingsButton() {
        // Set onclick button event handling to UserSettings Activity
        Button userSettingsButton = HomeActivity.this.findViewById(R.id.homeUserSettingsButton);

        userSettingsButton.setOnClickListener(view -> {
            Intent goToUserSettingsActivity = new Intent(HomeActivity.this, UserSettings.class);
            startActivity(goToUserSettingsActivity);
        });
    }

    /**
     * Sets up RecyclerView element, sets LayoutManager to LinearLayoutManager, adds some
     * static tasks, creates and attaches RecyclerView.Adapter, supplies the Task Array,
     * provides context to the RecyclerView.Adapter, and sets the adapter to the
     * RecyclerView instance.
     */
    private void setUpTasksRecyclerView() {
        RecyclerView tasksRecyclerView = findViewById(R.id.homeTaskListRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tasksRecyclerView.setLayoutManager(layoutManager);
        
        // TODO: Remove this hard coded tasks list?
        // create Task list
        ArrayList<TaskModel> tasks = new ArrayList<>();
        tasks.add(new TaskModel("Buy Groceries", "Milk, Juice, Eggs, and a stick of butter."));
        tasks.add(new TaskModel("Do Laundry", "Wash, dry, fold, put away."));
        tasks.add(new TaskModel("Vacuum", "The floors are covered in dog hair. Deal with it."));

        // create and attach the recyclerview adapter and set the adapter recyclerview
        TaskListRecyclerViewAdapter adapter = new TaskListRecyclerViewAdapter(tasks, this);
        tasksRecyclerView.setAdapter(adapter);
    }

}