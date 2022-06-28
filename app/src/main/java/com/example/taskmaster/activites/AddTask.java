package com.example.taskmaster.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taskmaster.R;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Add event driven textbox change on button press
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);

        addThisTaskButton.setOnClickListener(view -> {
            Log.i("", "Entered incrementTaskCounter lambda.");
            TextView successText = AddTask.this.findViewById(R.id.submittedText);
            successText.setVisibility(View.VISIBLE);
        });
    }

}