package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taskmaster.R;
import com.example.taskmaster.database.TaskMasterDatabase;
import com.example.taskmaster.models.TaskModel;

public class AddTask extends AppCompatActivity {

    TaskMasterDatabase taskMasterDatabase;
    public static final String DATABASE_NAME = "task_master";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskMasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskMasterDatabase.class,
                        DATABASE_NAME
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        setupAddThisButton();
    }

    private void setUpSpinner() {
        Spinner taskStatusSpinner = findViewById(R.id.addtaskTaskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                TaskModel.StateCategoryEnum.values()
        ));
    }

    private void setupAddThisButton() {
        // Add event driven textbox change on button press
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);

        addThisTaskButton.setOnClickListener(view -> {
            Log.i("", "Entered incrementTaskCounter lambda.");
            TextView successText = AddTask.this.findViewById(R.id.submittedText);
            successText.setVisibility(View.VISIBLE);
        });
    }

}