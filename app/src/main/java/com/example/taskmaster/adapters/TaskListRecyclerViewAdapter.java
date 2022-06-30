package com.example.taskmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.HomeActivity;
import com.example.taskmaster.R;
import com.example.taskmaster.activites.TaskDetailActivity;

import java.util.ArrayList;

/**
 * Adapter to manage RecyclerView.
 */
public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder>{

    // data items stored in this field
    ArrayList<Task> tasks;
    Context callingActivity; // allows this class to accept context from the calling method

    public TaskListRecyclerViewAdapter(ArrayList<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskListRecyclerViewAdapter.TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);
        return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListRecyclerViewAdapter.TaskListViewHolder holder, int position) {
        TextView taskFragmentTextView = holder.itemView.findViewById(R.id.fragTaskListFragment);
        Task selectedTask = tasks.get(position);

        // humans don't number their task lists starting with zero
        int itemNumber = position + 1;

        // format data for sending as an Intent Extra
        String taskFullDetailsOutput = String.format(
                "%1$s [%2$s]\n%3$s",
                selectedTask.getTitle(),
                selectedTask.getState(),
                selectedTask.getBody()
        );

        // sets formatted data to TextView for display
        taskFragmentTextView.setText(String.format(
                "%1$s:\n %2$s [%3$s]: \n%4$s",
                itemNumber,
                selectedTask.getTitle(),
                selectedTask.getState(),
                selectedTask.getBody()
        ));

        // make an OnClick handler to interact with RecyclerView items
        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(view -> {
            Intent goToTaskDetailView = new Intent(callingActivity, TaskDetailActivity.class);
            goToTaskDetailView.putExtra(HomeActivity.SELECTED_TASK_DETAILS, taskFullDetailsOutput);
            callingActivity.startActivity(goToTaskDetailView);
        });
    }

    @Override
    public int getItemCount() {
//        return tasks.size();
        return 0;
    }

    /**
     * Nested Class ViewHolder that will hold a Fragment
     */
    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(View fragmentItemView) {
            super(fragmentItemView);
        }
    }
}
