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

        // prepare to consume from an Intent Extra
        Intent intent = getIntent();
        // get intent extra task_title
        String selectedTaskName = intent.getStringExtra(HomeActivity.TASK_TITLE);
        // get the text field reference
        TextView taskDetailTitle = TaskDetailActivity.this.findViewById(R.id.taskDetailTitleText);
        // update the text field reference
        taskDetailTitle.setText(selectedTaskName);
    }
}