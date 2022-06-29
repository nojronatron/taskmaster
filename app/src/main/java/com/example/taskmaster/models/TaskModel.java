package com.example.taskmaster.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String title;
    private String body;
    private String state; // New, Assigned, In Progress, or Completed

    @Ignore
    public TaskModel() {
    } // TODO: Decide if @Ignore is needed here or at other CTOR for Room.

    public TaskModel(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
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
        if (this.state.equals("New")) {
            this.state = "Assigned";
        }
        if (this.state.equals("")) {
            this.state = "New";
        }
    }

    @NonNull
    @Override
    public String toString() {
        // TODO: Confirm this is a good thing to actually do.
        return String.format("%1$s - %2$s", this.title, this.body);
    }

    public enum StateCategoryEnum {
        NEW("New"),
        ASSIGNED("Assigned"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed");

        private final String stateText;

        StateCategoryEnum(String statusText) {
            this.stateText = statusText;
        }

        public String getStateText() {
            return stateText;
        }

        public static StateCategoryEnum fromString(String inputStatusText) {
            for (StateCategoryEnum state : StateCategoryEnum.values()) {
                if (state.stateText.equals(inputStatusText)) {
                    return state;
                }
            }

            return null;
        }
    }
}
