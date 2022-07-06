package com.jrmz.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.jrmz.taskmaster.HomeActivity;
import com.jrmz.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        String selectedTaskDetails = "Intent Was Not Set";
        Intent intent = getIntent();

        if (intent != null) {
            selectedTaskDetails = intent.getStringExtra(HomeActivity.SELECTED_TASK_DETAILS);
        }

        TextView taskDetailDescriptionText = TaskDetailActivity.this.findViewById(R.id.taskDetailDescriptionText);
        taskDetailDescriptionText.setText(selectedTaskDetails);
        setupExitPageButton();
    }

    /**
     * Enables exiting the current page easily.
     */
    private void setupExitPageButton() {
        Button taskDetailExitButton = TaskDetailActivity.this.findViewById(R.id.taskDetailExitButton);
        taskDetailExitButton.setOnClickListener(view -> {
            finish();
        });
    }
}