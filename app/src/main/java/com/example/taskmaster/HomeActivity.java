package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.activites.AddTask;
import com.example.taskmaster.activites.AllTasks;
import com.example.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.example.taskmaster.fragments.UserSettings;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // declare public static final type fields to create keys to associate with preference editor store
    public static final String SELECTED_TASK_DETAILS = null;
    public static final String TITLE_TEXT_SUFFIX = "'s Task List";
    public static final String ACTIVITY_NAME = "HomeActivity";

    // reference to store DB contents
    List<Task> tasks = null;

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

        // launch common methods to perform required tasks
        getTaskItemsFromDb();
        setTitleText();
        setupAddTaskButton();
        setupLoadAllTasksActivityButton();
        setUpUserSettingsButton();
        setUpTasksRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleText();
        getTaskItemsFromDb();
    }

    private void getTaskItemsFromDb() {
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(ACTIVITY_NAME, "Successully loaded Tasks from GraphQL.");
                    tasks.clear();

                    for (Task task : success.getData()) {
                        tasks.add(task);
                    }

                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },

                failure -> Log.i(ACTIVITY_NAME, "Read from GraphQL failed.")
        );
    }

    private void setTitleText() {
        // get users custom entered name but if not set use Current User as default
        String userNicknamePrefix = preferences.getString(UserSettings.USER_NAME_KEY, "Current User");
        String userNickname = String.format("%1s%2s", userNicknamePrefix, TITLE_TEXT_SUFFIX);

        // set users custom name to the View
        TextView userCustomNameText = findViewById(R.id.homeTasksListTitleTextVIew);
        userCustomNameText.setText(userNickname);
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
            Intent goToUserSettingsActivity = new Intent(
                    HomeActivity.this, UserSettings.class);
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

        // create and attach the recyclerview adapter and set the adapter recyclerview
        // Note: Had to cast tasks to ArrayList<T> from List<T> for adapter to accept the return
        adapter = new TaskListRecyclerViewAdapter((ArrayList<Task>) tasks, this);
        tasksRecyclerView.setAdapter(adapter);
    }
}