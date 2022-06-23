package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {

//    private int totalTaskCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//        TextView totalTaskCount = AddTask.this.findViewById(R.id.totalTaskCount);
//        totalTaskCount.setText(totalTaskCounter);

        // Add event driven textbox change on button press
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);

        addThisTaskButton.setOnClickListener(view -> {
            Log.i("", "Entered incrementTaskCounter lambda.");
            TextView successText = AddTask.this.findViewById(R.id.submittedText);
            successText.setVisibility(View.VISIBLE);
//            incrementTaskCounter();
//            setTaskCounterText();
        });
    }

//    private void incrementTaskCounter() {
//        this.totalTaskCounter++;
//    }

//    private void setTaskCounterText() {
//        TextView totalTaskCount = AddTask.this.findViewById(R.id.totalTaskCount);
//        totalTaskCount.setText(totalTaskCounter);
//    }
}