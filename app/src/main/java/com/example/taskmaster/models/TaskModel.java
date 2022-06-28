package com.example.taskmaster.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskModel {
    @PrimaryKey(autoGenerate=true)
    private Long id;

    private String title;
    private String body;
    private String state; // New, Assigned, In Progress, or Completed

    public TaskModel(){}

    public TaskModel(String title, String body) {
        this.title = title;
        this.body = body;
        this.state = "New";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Increments State to four specific strings, one direction, toward completion.
     */
    public void incrementState() {
        if (this.state.equals("In Progress")) {
            this.state = "Completed";
        }
        if (this.state.equals("Assigned")) {
            this.state = "In Progress";
        }
        if(this.state.equals("New")) {
            this.state = "Assigned";
        }
        if (this.state.equals("")){
            this.state = "New";
        }
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: Confirm this is a good thing to actually do.
        return String.format("%1$s: %2$s", this.title, this.body);
    }
}
