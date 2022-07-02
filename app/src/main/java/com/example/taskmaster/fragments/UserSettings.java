package com.example.taskmaster.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserSettings extends AppCompatActivity {

    public static final String USER_NAME_KEY = "currentUsername";
    private static final String ACTIVITY_NAME = "UserSettings Activity";
    SharedPreferences preferences;

    List<Team> teams = null;
    Spinner teamNamesSpinner = null;
    CompletableFuture<List<Team>> teamsFuture = null;
    ArrayList<String> teamNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        // create shared prefs instance
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // use prefs to get existing entry or get empty string if none found
        String currentUsername = preferences.getString(USER_NAME_KEY, "");

        // deal with empty or full currentUsername
        if (!currentUsername.isEmpty()) {
            // get reference to username edittext element
            EditText usernameText = findViewById(R.id.settingsEditTextPersonName);
            usernameText.setText(currentUsername);
        }

        this.getTeamsFromDB();
        this.setUpTaskStatusSpinner();

        // get reference to the submit button
        Button settingsSubmitButton = findViewById(R.id.settingsSubmitButton);

        // set up event listener to store user-defined entry into Shared Prefs
        settingsSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set up the editor
                SharedPreferences.Editor preferenceEditor = preferences.edit();

                // get the text from the edittext element
                EditText usernameText = findViewById(R.id.settingsEditTextPersonName);
                String currentUsername = usernameText.getText().toString();

                // store string in Shared Prefs AND APPLY CHANGES
                preferenceEditor.putString(USER_NAME_KEY, currentUsername);
                preferenceEditor.apply();

                Toast.makeText(
                        UserSettings.this, "Your changes have been saved.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

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

    private void setUpTeamNameSpinner() {
        teamNamesSpinner = findViewById(R.id.userConfigSelectTeamSpinner);

        // add runOnUiThread to manage a/sync calls?
        runOnUiThread(()-> {
            teamNamesSpinner.setAdapter(new ArrayAdapter<>(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    teamNames
            ));

        });
    }

    private void setUpTaskStatusSpinner() {
        Spinner taskStatusSpinner = findViewById(R.id.addtaskTaskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
//                Task.StateCategoryEnum.values()
                new String[] {"New", "Assigned", "In Progress", "Closed"}
        ));
    }

}