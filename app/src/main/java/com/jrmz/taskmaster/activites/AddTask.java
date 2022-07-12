package com.jrmz.taskmaster.activites;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.jrmz.taskmaster.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class AddTask extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "AddTask Activity";
    List<Team> teams = null;
    Spinner teamNamesSpinner = null;
    CompletableFuture<List<Team>> teamsFuture = null;
    ArrayList<String> teamNames = new ArrayList<>();

    ActivityResultLauncher<Intent> activityResultLauncher;
    String s3imageKey = ""; // not null but empty string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityResultLauncher = getImagePickerActivityResultLauncher();

        setContentView(R.layout.activity_add_task);
        teamsFuture = new CompletableFuture<>();

        this.getTeamsFromDB();
        this.setUpTaskStatusSpinner();
        this.setUpAddImageButton();
        this.setupAddThisButton();

//        s3imageKey = task
    }

    /**
     * Sets an onClick listener to capture selected image properties and add the image reference
     * to the currently selected Task.
     */
    public void setUpAddImageButton() {
        Button addImageButton = findViewById(R.id.addTaskAddImageButton);
        addImageButton.setOnClickListener(view -> {
            launchImageSelectorIntent();
        });
    }

    /**
     * Launches File Picker Intent that filters in jpeg and png file types and prepares image for
     * adding to the currently selected Task.
     */
    public void launchImageSelectorIntent() {
        Intent imageFilePickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickerIntent.setType("*/*");
        imageFilePickerIntent.putExtra(Intent.EXTRA_MIME_TYPES,
                new String[]{"image/jpeg", "image/png"});
        activityResultLauncher.launch(imageFilePickerIntent);
    }

    /**
     * Returns a method as an ActivityResultLauncher Intent, as required by
     * ActivityResultCaller interface.
     * @return
     */
    public ActivityResultLauncher<Intent> getImagePickerActivityResultLauncher() {
        // TODO: verify this refactoring works as intended
        return registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            // TODO: verify this lambda works as intended
                            try {
                                assert result.getData() != null; // suggested by IntelliJ IDEA
                                Uri pickedImageUri = result.getData().getData();
                                InputStream pickedImageInputStream = getContentResolver()
                                        .openInputStream(pickedImageUri);
                                String pickedImageFilename = getFileNameFromUri(pickedImageUri);
                                uploadInputStreamToS3(pickedImageInputStream,
                                        pickedImageFilename,
                                        pickedImageUri);
                                Log.i(ACTIVITY_NAME,
                                        "Successfully grabbed input stream from file on this device.");
                            } catch (FileNotFoundException fnfe) {
                                Log.e(ACTIVITY_NAME,
                                        "Could not get file from device " +
                                        fnfe.getMessage(), fnfe);
                            } catch (Exception ex) {
                                Log.e(ACTIVITY_NAME,
                                        "An unexpected exception occurred: " +
                                                ex.getMessage(), ex);
                            }
                        }
                );
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream,
                                       String pickedImageFilename,
                                       Uri pickedImageUri) {
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success -> {
                    Log.i(ACTIVITY_NAME, "Successfully called \"UpdateInputStream\" to S3. " +
                            "Success message: " + success.getKey());
                    s3imageKey = success.getKey();
                    ImageView taskImageView = findViewById(R.id.addTaskImageViewElement);
                    InputStream pickedImageInputStreamCopy = null;
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(ACTIVITY_NAME, "Could not get input stream from designated " +
                                "location: " + fnfe.getMessage(), fnfe);
                    }

                    taskImageView.setImageBitmap(
                            BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> {
                    Log.e(ACTIVITY_NAME, "Failure uploading file to S3 using filename " +
                            pickedImageFilename + ". Failure message: " + failure.getMessage());
                }
        );
    }

    // the following code is from Stack Overflow: https://stackoverflow.com/a/25005243/16889809
    @SuppressLint("Range")
    public String getFileNameFromUri(Uri pickedImageUri) {
        String result = null;

        if (pickedImageUri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver()
                    .query(pickedImageUri, null, null, null, null);

            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                // TODO: verify this code snipped (from IntelliJ) works
                assert cursor != null;
                cursor.close();
            }
        }

        if (result == null) {
            result = pickedImageUri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return result;
    }

    /**
     * Setup up Amplify Query to get list of existing Teams from the database.
     */
    private void getTeamsFromDB() {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                successResponse -> {
                    Log.i(ACTIVITY_NAME, "Successfully queried DB for teams.");
                    teams = new ArrayList<>();

                    for (Team team: successResponse.getData()) {
                        teams.add(team);
                        teamNames.add(team.getName());
                    }

                    teamsFuture.complete(teams);
                    setUpTeamNameSpinner();
                },
                failureResponse -> {
                    Log.e(ACTIVITY_NAME, "Failed to query Teams in DB");
                }
        );
    }

    /**
     * Initialize and configure the Team names Spinner.
     */
    private void setUpTeamNameSpinner() {
        teamNamesSpinner = findViewById(R.id.addTaskTeamsSpinner);

        runOnUiThread(()-> {
            teamNamesSpinner.setAdapter(new ArrayAdapter<>(
                    this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                    teamNames
            ));
        });
    }

    /**
     * Initialize and configure the Spinner than holds the task Status value.
     */
    private void setUpTaskStatusSpinner() {
        Spinner taskStatusSpinner = findViewById(R.id.addtaskTaskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                new String[] {"New", "Assigned", "In Progress", "Closed"}
        ));
    }

    /**
     * Setup the Add button and register onClick listener to gather user supplied inputs and store
     * them as an entity into the Amplify DB.
     */
    private void setupAddThisButton() {
        Button addThisTaskButton = AddTask.this.findViewById(R.id.addThisTaskButton);
        Spinner addTasksSpinner = AddTask.this.findViewById(R.id.addtaskTaskStatusSpinner);
        EditText newTaskTitleEditText = findViewById(R.id.addTaskTaskTitleText);
        EditText newTaskDescriptionEditText = findViewById(R.id.addTaskTaskDescriptionText);

        addThisTaskButton.setOnClickListener(view -> {
            String newTaskDescription = newTaskDescriptionEditText.getText().toString();
            String newTaskStatus = addTasksSpinner.getSelectedItem().toString();
            String newTaskTitle = newTaskTitleEditText.getText().toString();

            try {
                teams = teamsFuture.get();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            String selectedTeamName = teamNamesSpinner.getSelectedItem().toString();
            Team selectedTeam = teams.stream().filter(team -> team.getName()
                    .equals(selectedTeamName)).findAny().orElseThrow(RuntimeException::new);

            Task task = Task.builder()
                    .title(newTaskTitle)
                    .body(newTaskDescription)
                    .state(newTaskStatus)
                    .team(selectedTeam)
                    .build();

            // TODO: Check that this code works as expected or should it block or another solution?
            // https://developer.android.com/reference/java/util/concurrent/atomic/AtomicReference
            AtomicReference<String> toastMessage = new AtomicReference<>("Successfully Created New Task!");

            // aws amplify graphql insert method
            Amplify.API.mutate(
                    ModelMutation.create(task),
                    response -> Log.i(ACTIVITY_NAME, "Added Todo with id: " +
                            response.getData().getId()),
                    error -> {
                        Log.e(ACTIVITY_NAME, "Create failed", error);
                        toastMessage.set("Failed to create new Task! " +
                                "Check if Toast message was synchronized.");
                    }
            );

            Toast.makeText(AddTask.this, toastMessage.get(), Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}