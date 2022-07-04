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

    private static final String USER_NAME_KEY = "currentUsername";
    private static final String ACTIVITY_NAME = "UserSettings Activity";
    private static final String SELECTED_TEAM_KEY = "selectedTeam";

    String currentUsername = "";
    String selectedTeamName = "";
    SharedPreferences preferences;
    Spinner teamNamesSpinner = null;
    ArrayList<String> teamNames = new ArrayList<>();
    List<Team> teams = null;
    CompletableFuture<List<Team>> teamsFuture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        Log.i(ACTIVITY_NAME, "Entered onCreate method.");

        // create shared prefs instance
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // use prefs to get existing entries or get empty string if none found
        this.currentUsername = preferences.getString(USER_NAME_KEY, "");

        Log.i(ACTIVITY_NAME, "Test for currentUsername value.");

        // deal with empty or full currentUsername
        if (!currentUsername.isEmpty()) {
            Log.i(ACTIVITY_NAME, "currentUsername is NOT empty.");

            // get reference to username edittext element
            EditText usernameText = findViewById(R.id.settingsEditTextPersonName);
            usernameText.setText(currentUsername);
        }

        Log.i(ACTIVITY_NAME, "currentUsername has value.");

        this.teamsFuture = new CompletableFuture<>();
        this.getTeamsFromDB();

        // get reference to the submit button
        Button settingsSubmitButton = findViewById(R.id.settingsSubmitButton);

        // get reference to the teams list spinner
        teamNamesSpinner = findViewById(R.id.userConfigSelectTeamSpinner);

        Log.i(ACTIVITY_NAME, "Set onclick listener for settingsSubmitButton.");

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

                // take currently selected team name and stuff into SharedPrefs
                selectedTeamName = teamNamesSpinner.getSelectedItem().toString();
                preferenceEditor.putString(SELECTED_TEAM_KEY, selectedTeamName);

                String logMessage = String.format(
                        "Wrote %1s to SELECTED_TEAM_KEY sharedPrefs.", selectedTeamName);
                Log.i(ACTIVITY_NAME, logMessage);

                // save preferences
                preferenceEditor.apply();

                Toast.makeText(
                        UserSettings.this, "Your changes have been saved.",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void getTeamsFromDB() {
        Log.i(ACTIVITY_NAME, "Entered getTeamsFromDB()");

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
                    Log.i(ACTIVITY_NAME, "Calling setUpTeamNameSpinner()");
                    setUpTeamNameSpinner();
                },
                failureResponse -> {
                    Log.e(ACTIVITY_NAME, "Failed to query Teams in DB");
                }
        );
    }

    private void setUpTeamNameSpinner() {
        Log.i(ACTIVITY_NAME, "Entered setUpTeamNameSpinner.");

        teamNamesSpinner = findViewById(R.id.userConfigSelectTeamSpinner);

        // use runOnUiThread to manage a/sync calls
        runOnUiThread(()-> {
            teamNamesSpinner.setAdapter(new ArrayAdapter<>(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    teamNames
            ));
        });
    }
}