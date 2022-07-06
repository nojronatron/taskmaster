package com.jrmz.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.jrmz.taskmaster.activites.AddTask;
import com.jrmz.taskmaster.fragments.UserSettings;

public class LoginRegisterActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // TODO: make things invisible based on whether user is a logger-iner or signing-upper
        setupVerifyButton();
        setupRegisterButton();
        setupLoginButton();

        // get registered user login creds
        EditText loginEmailEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegUserLoginEmail);
        EditText loginPasswordEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegLoginPasswdPasswd);
    }

    private void setupLoginButton() {
        Button loginUserButton = LoginRegisterActivity.this.findViewById(R.id.loginRegLoginButton);

        loginUserButton.setOnClickListener(view -> {
            EditText userEmail = LoginRegisterActivity.this.findViewById(R.id.loginRegUserLoginEmail);
            EditText userPasswd = LoginRegisterActivity.this.findViewById(R.id.loginRegLoginPasswdPasswd);

            Amplify.Auth.signIn(
                    userEmail.getText().toString(),
                    userPasswd.getText().toString(),
                    result -> {
                        // TODO redirect user to MainActivity
                        Log.i(ACTIVITY_NAME, result.isSignInComplete() ? "Sign in succeeded" : "Sign in FAILED");
                    },
                    error -> Log.e(ACTIVITY_NAME, "Sign-in Process resturned error: " + error.toString())
            );
        });

        Intent goToHomeActivity = new Intent(LoginRegisterActivity.this, HomeActivity.class);
        startActivity(goToHomeActivity);
    }

    private void setupVerifyButton() {
        Button verifyButton = LoginRegisterActivity.this.findViewById(R.id.loginRegVerifyCodeButton);

        verifyButton.setOnClickListener(view -> {
            EditText loginEmailEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegUserLoginEmail);
            EditText userInputVerifyCode = LoginRegisterActivity.this.findViewById(R.id.loginRegVerifyCodeUserInput);

            Amplify.Auth.confirmSignUp(loginEmailEntry.getText().toString(), userInputVerifyCode.getText().toString(),
                    success -> {
                        Log.i(ACTIVITY_NAME, "Verification succeeded for email " + loginEmailEntry);
                        // TODO: activate the password entry box for user to login
                    },
                    failure -> Log.e(ACTIVITY_NAME, "Verification failed: " + failure.toString()));
        });
    }

    private void setupRegisterButton() {
        Button registerUserButton = LoginRegisterActivity.this.findViewById(R.id.loginRegRegisterButton);

        registerUserButton.setOnClickListener(view -> {
            // call AUTH registration process and fill-in user email and password if successful
            // if not successful put a message in loginRegStatusTextView

            // get new users entered email and password and send through Amplify Auth signUp method
            EditText registrationEmailEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegEmailRegistrationEntry);
            EditText registrationPasswdEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegPasswdRegistrationEntry);

            String userEmail = registrationEmailEntry.getText().toString();

            Amplify.Auth.signUp(userEmail, registrationPasswdEntry.getText().toString(),
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), userEmail)
                            .build(),
                    success -> {
                        Log.i(ACTIVITY_NAME, "User Email " + userEmail + " successfully registered: " + success.toString());
                        EditText loginEmailEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegUserLoginEmail);
                        EditText loginPasswordEntry = LoginRegisterActivity.this.findViewById(R.id.loginRegLoginPasswdPasswd);
                        loginEmailEntry.setText(userEmail);

                        // tell the user to re-enter their password
                        Toast.makeText(
                                LoginRegisterActivity.this, "You have been sent a verification code.",
                                Toast.LENGTH_SHORT).show();

                        Toast.makeText(
                                LoginRegisterActivity.this,
                                "After verifying, enter your password above to login.",
                                Toast.LENGTH_LONG).show();

                    },
                    failure -> {
                        Log.i(ACTIVITY_NAME, "Signup failed: " + failure.toString());
                    });
        });
    }
}