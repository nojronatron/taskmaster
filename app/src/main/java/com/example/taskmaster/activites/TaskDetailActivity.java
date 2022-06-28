package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.taskmaster.HomeActivity;
import com.example.taskmaster.R;

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