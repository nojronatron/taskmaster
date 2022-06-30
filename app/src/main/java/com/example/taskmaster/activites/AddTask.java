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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.R;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        this.setUpSpinner();
        this.setupAddThisButton();
    }

    private void setUpSpinner() {
        Spinner taskStatusSpinner = findViewById(R.id.addtaskTaskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
//                Task.StateCategoryEnum.values()
                new String[] {"New", "Assigned", "In Progress", "Closed"}
        ));
    }

    private void setupAddThisButton() {//TaskMasterDatabase db) {
        // Add event driven textbox change on button press
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);
        Spinner addTasksSpinner = AddTask.this.findViewById(R.id.addtaskTaskStatusSpinner);
        EditText newTaskTitleEditText = findViewById(R.id.addTaskTaskTitleText);
        EditText newTaskDescriptionEditText = findViewById(R.id.addTaskTaskDescriptionText);

        addThisTaskButton.setOnClickListener(view -> {
            String newTaskDescription = newTaskDescriptionEditText.getText().toString();
            String newTaskStatus = addTasksSpinner.getSelectedItem().toString();
            String newTaskTitle = newTaskTitleEditText.getText().toString();

            Task task = Task.builder()
                    .title(newTaskTitle)
                    .body(newTaskDescription)
                    .state(newTaskStatus)
                    .build();

            Log.i("", "Entered incrementTaskCounter lambda.");
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