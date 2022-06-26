package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        String selectedTaskDetails = "Intent Was Not Set";

        // prepare to consume from an Intent Extra
        Intent intent = getIntent();

        if (intent != null) {
            // get intent extra: selected task details
            selectedTaskDetails = intent.getStringExtra(HomeActivity.SELECTED_TASK_DETAILS);
        }

        // get the text field reference
        TextView taskDetailDescriptionText = TaskDetailActivity.this.findViewById(R.id.taskDetailDescriptionText);

        // update the text fields by references
        taskDetailDescriptionText.setText(selectedTaskDetails);
    }
}