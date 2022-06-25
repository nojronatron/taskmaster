package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter to manage RecyclerView.
 */
public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder>{

    // data items stored in this field
    ArrayList<TaskModel> tasks;
    Context callingActivity; // allows this class to accept context from the calling method

    public TaskListRecyclerViewAdapter(ArrayList<TaskModel> tasks, Context callingActivity) {
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
        String taskTitle = tasks.get(position).getTitle();
        taskFragmentTextView.setText(position + ". " + taskTitle);

        // make an OnClick handler to interact with RecyclerView items
        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(view -> {
            Intent goToTaskDetailView = new Intent(callingActivity, TaskDetailActivity.class);
            goToTaskDetailView.putExtra(HomeActivity.TASK_TITLE, taskTitle);
            callingActivity.startActivity(goToTaskDetailView);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
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
