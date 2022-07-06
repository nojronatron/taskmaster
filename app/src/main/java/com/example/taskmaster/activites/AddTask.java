package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTask extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "AddTask Activity";
    List<Team> teams = null;
    Spinner teamNamesSpinner = null;
    CompletableFuture<List<Team>> teamsFuture = null;
    ArrayList<String> teamNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_task);
        teamsFuture = new CompletableFuture<>();
        this.getTeamsFromDB();
        this.setUpTaskStatusSpinner();
        this.setupAddThisButton();
    }

    /**
     * Setup up Amplify Query to get list of existing Teams from the database.
     */
    private void getTeamsFromDB() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                successResponse -> {
                    Log.i(ACTIVITY_NAME, "Successfully queried DB for teams.");
                    teams = new ArrayList<>();

                    for (Team team: successResponse.getData()) {
                        teams.add(team);
                        teamNames.add(team.getName());
                    }

                    teamsFuture.complete(teams);
                    setUpTeamNameSpinner();
                },
                failureResponse -> {
                    Log.e(ACTIVITY_NAME, "Failed to query Teams in DB");
                }
        );
    }

    /**
     * Initialize and configure the Team names Spinner.
     */
    private void setUpTeamNameSpinner() {
        teamNamesSpinner = findViewById(R.id.addTaskTeamsSpinner);

        runOnUiThread(()-> {
            teamNamesSpinner.setAdapter(new ArrayAdapter<>(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    teamNames
            ));
        });
    }

    /**
     * Initialize and configure the Spinner than holds the task Status value.
     */
    private void setUpTaskStatusSpinner() {
        Spinner taskStatusSpinner = findViewById(R.id.addtaskTaskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                new String[] {"New", "Assigned", "In Progress", "Closed"}
        ));
    }

    /**
     * Setup the Add button and register onClick listener to gather user supplied inputs and store
     * them as an entity into the Amplify DB.
     */
    private void setupAddThisButton() {
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);
        Spinner addTasksSpinner = AddTask.this.findViewById(R.id.addtaskTaskStatusSpinner);
        EditText newTaskTitleEditText = findViewById(R.id.addTaskTaskTitleText);
        EditText newTaskDescriptionEditText = findViewById(R.id.addTaskTaskDescriptionText);

        addThisTaskButton.setOnClickListener(view -> {
            String newTaskDescription = newTaskDescriptionEditText.getText().toString();
            String newTaskStatus = addTasksSpinner.getSelectedItem().toString();
            String newTaskTitle = newTaskTitleEditText.getText().toString();

            try {
                teams = teamsFuture.get();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            String selectedTeamName = teamNamesSpinner.getSelectedItem().toString();
            Team selectedTeam = teams.stream().filter(team -> team.getName().equals(selectedTeamName)).findAny().orElseThrow(RuntimeException::new);

            Task task = Task.builder()
                    .title(newTaskTitle)
                    .body(newTaskDescription)
                    .state(newTaskStatus)
                    .team(selectedTeam)
                    .build();

            Log.i("AddTaskActivity", "Created a new Task instance.");
            TextView successText = AddTask.this.findViewById(R.id.submittedText);

            // aws amplify graphql insert method
            Amplify.API.mutate(
                    ModelMutation.create(task),
                    response -> Log.i("TaskMaster", "Added Todo with id: " +
                            response.getData().getId()),
                    error -> Log.e("TaskMaster", "Create failed", error)
            );

            successText.setVisibility(View.VISIBLE);
            finish();
        });
    }
}