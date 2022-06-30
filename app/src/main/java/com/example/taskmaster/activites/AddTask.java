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

import com.example.taskmaster.R;
import com.example.taskmaster.models.TaskModel;

public class AddTask extends AppCompatActivity {

    public static final String DATABASE_NAME = "task_master";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//        taskMasterDatabase = Room.databaseBuilder(
//                        getApplicationContext(),
//                        TaskMasterDatabase.class,
//                        DATABASE_NAME
//                )
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
//
//        setupAddThisButton(taskMasterDatabase);
        this.setUpSpinner();
    }

    private void setUpSpinner() {
        Spinner taskStatusSpinner = findViewById(R.id.addtaskTaskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                TaskModel.StateCategoryEnum.values()
        ));
    }

    private void setupAddThisButton(){//TaskMasterDatabase db) {
        // Add event driven textbox change on button press
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);
        Spinner addTasksSpinner = AddTask.this.findViewById(R.id.addtaskTaskStatusSpinner);
        EditText newTaskTitleEditText = findViewById(R.id.addTaskTaskTitleText);
        EditText newTaskDescriptionEditText = findViewById(R.id.addTaskTaskDescriptionText);

        addThisTaskButton.setOnClickListener(view -> {
            String newTaskDescription = newTaskDescriptionEditText.getText().toString();
            String newTaskStatus = addTasksSpinner.getSelectedItem().toString();
            String newTaskTitle = newTaskTitleEditText.getText().toString();
            TaskModel newTask = new TaskModel(newTaskTitle, newTaskDescription, newTaskStatus);
            Log.i("", "Entered incrementTaskCounter lambda.");
            TextView successText = AddTask.this.findViewById(R.id.submittedText);
            // execute TaskDao Insert method e.g. db.taskDao.insert(task)
//            db.taskDao().insertSingleTask(newTask);
            successText.setVisibility(View.VISIBLE);
            finish();
        });
    }

}