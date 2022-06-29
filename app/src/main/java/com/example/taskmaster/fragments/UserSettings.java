package com.example.taskmaster.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmaster.R;

public class UserSettings extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String USER_NAME_KEY = "currentUsername";

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
}