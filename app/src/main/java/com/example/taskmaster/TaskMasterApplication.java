package com.example.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

// this will be the new entrypoint for this app
public class TaskMasterApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException ampe) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify: ", ampe);
        }
    }
}