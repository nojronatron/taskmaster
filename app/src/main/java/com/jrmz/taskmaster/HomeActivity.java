package com.jrmz.taskmaster;

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

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.jrmz.taskmaster.activites.AddTask;
import com.jrmz.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.jrmz.taskmaster.fragments.UserSettings;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public static final String USER_NAME_KEY = "currentUsername";
    public static final String TITLE_TEXT_SUFFIX = "'s Task List";
    public static final String ACTIVITY_NAME = "HomeActivity";
    public static final String SELECTED_TEAM_KEY = "selectedTeam";
    public static final String SELECTED_TASK_DETAILS = "selectedTaskDetails";

    String selectedTeamName = "";
    String userNicknamePrefix = "";
    SharedPreferences preferences;
    TaskListRecyclerViewAdapter adapter;
    List<Task> tasks = null;
    List<Team> teams = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tasks = new ArrayList<>();
        teams = new ArrayList<>();
        loadExistingPreferences();
        getTeamsFromDb();
        getTaskItemsFromDb();
        setTitleText();
        setupAddTaskButton();
        setUpUserSettingsButton();
        setUpTasksRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExistingPreferences();
        setTitleText();
        getTaskItemsFromDb();
    }

    /**
     * Loads and initializes existing current user name and selected Team.
     */
    private void loadExistingPreferences() {
        // initialize shared preferences
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userNicknamePrefix = preferences.getString(HomeActivity.USER_NAME_KEY, "Current User");
        selectedTeamName = preferences.getString(HomeActivity.SELECTED_TEAM_KEY, "");
    }

    /**
     * Loads a default set of Teams into the database.
     */
    private void createTeams() {
        teams.clear();

        String teamAlphaName = "Team Alpha";
        String teamBravoName = "Team Bravo";
        String teamCharlieName = "Team Charlie";

        Team teamAlpha = Team.builder()
                .name(teamAlphaName)
                .build();

        Team teamBravo = Team.builder()
                .name(teamBravoName)
                .build();

        Team teamCharlie = Team.builder()
                .name(teamCharlieName)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(teamAlpha),
                successResponse -> {
                    Log.i(ACTIVITY_NAME, "Successfully created instance " +
                            teamAlphaName + ". getName() returned: " + teamAlpha.getName());
                    teams.add(teamAlpha);
                },
                failureResponse -> Log.e(ACTIVITY_NAME, "Failed creating instance " +
                        teamAlphaName)
        );

        Amplify.API.mutate(
                ModelMutation.create(teamBravo),
                successResponse -> {
                    Log.i(ACTIVITY_NAME, "Successfully created instance " +
                            teamBravoName + ". getName() returned: " + teamBravo.getName());
                    teams.add(teamBravo);
                },
                failureResponse -> Log.e(ACTIVITY_NAME, "Failed creating instance " +
                        teamBravoName)
        );

        Amplify.API.mutate(
                ModelMutation.create(teamCharlie),
                successResponse -> {
                    Log.i(ACTIVITY_NAME, "Successfully created instance " +
                            teamCharlieName + ". getName() returned: " + teamCharlie.getName());
                    teams.add(teamCharlie);
                },
                failureResponse -> Log.e(ACTIVITY_NAME, "Failed creating instance " +
                        teamCharlieName)
        );
    }

    /**
     * Sets up and executes Amplify Query for the list of existing Teams.
     */
    private void getTeamsFromDb() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                successResponse -> {
                    Log.i(ACTIVITY_NAME, "Successfully queried DB for teams.");
                    for (Team team: successResponse.getData()) {
                        teams.add(team);
                    }
                },
                failureResponse -> Log.e(ACTIVITY_NAME, "Failed to query Teams in DB")
        );
    }

    /**
     * Sets up and executes Amplify Query for the list of existing Tasks.
     * Filters Tasks by Team Name unless no Team Name is stored from user selection.
     */
    private void getTaskItemsFromDb() {
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(ACTIVITY_NAME, "Successfully loaded Tasks from GraphQL.");
                    tasks.clear();

                    for (Task task : success.getData()) {
                        if (task.getTeam() == null ||
                                selectedTeamName.equals("")) {
                            tasks.add(task);
                            continue;
                        }
                        // todo: debug throw happening in following IF statement
                        if (task.getTeam().getName().equals(selectedTeamName)) {
                            tasks.add(task);
                        }
                    }

                    int tasksCount = tasks.size();
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                },

                failure -> Log.e(ACTIVITY_NAME, "Read from GraphQL failed.")
        );
    }

    /**
     * Get user-supplied name and set to the View. Default is Current User.
     */
    private void setTitleText() {
        String userNickname = String.format("%1s%2s", userNicknamePrefix, TITLE_TEXT_SUFFIX);
        TextView userCustomNameText = findViewById(R.id.homeTasksListTitleTextVIew);
        userCustomNameText.setText(userNickname);
    }

    /**
     * Set onclick button event handling to add a new Task.
     */
    private void setupAddTaskButton() {
        Button addTaskButton = HomeActivity.this.findViewById(R.id.homeAddTaskButton);

        addTaskButton.setOnClickListener(view -> {
            Intent goToAddTaskActivity = new Intent(HomeActivity.this, AddTask.class);
            startActivity(goToAddTaskActivity);
        });
    }

//    private void setupLoadAllTasksActivityButton() {
//        // Set onclick button event handling to all tasks
//        Button allTasksButton = HomeActivity.this.findViewById(R.id.homeAllTasksButton);
//
//        allTasksButton.setOnClickListener(view -> {
//            Intent goToAllTasksActivity = new Intent(HomeActivity.this, AllTasks.class);
//            startActivity(goToAllTasksActivity);
//        });
//    }

    /**
     * Set onClick button event handling to load UserSettings Activity.
     */
    private void setUpUserSettingsButton() {
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
        adapter = new TaskListRecyclerViewAdapter(tasks, this);
        tasksRecyclerView.setAdapter(adapter);
    }
}