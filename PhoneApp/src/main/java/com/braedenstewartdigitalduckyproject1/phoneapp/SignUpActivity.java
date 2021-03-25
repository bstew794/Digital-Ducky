package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    SignUpViewModel signUpViewModel;
    EditText emailField;
    EditText usernameField;
    EditText passwordField;
    EditText confirmPassField;
    Button addUserButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected void onResume(){
        super.onResume();

        signUpViewModel = new SignUpViewModel();
        emailField = findViewById(R.id.sign_up_email);
        usernameField = findViewById(R.id.sign_up_username);
        passwordField = findViewById(R.id.sign_up_password);
        confirmPassField = findViewById(R.id.sign_up_password_confirm);
        addUserButt = findViewById(R.id.add_user_butt);

        addUserButt.setOnClickListener(view ->{
            String email = emailField.getText().toString();
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            String confirmPass = confirmPassField.getText().toString();

            boolean isValid = true;

            if (TextUtils.isEmpty(email)){
                Toast.makeText(getBaseContext(), "Email field is required", Toast.LENGTH_SHORT)
                        .show();

                isValid = false;
            }
            if (TextUtils.isEmpty(username)){
                Toast.makeText(getBaseContext(), "Username field is required",
                        Toast.LENGTH_SHORT)
                        .show();

                isValid = false;
            }
            if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)){
                Toast.makeText(getBaseContext(), "Password field is required",
                        Toast.LENGTH_SHORT)
                        .show();

                isValid = false;
            }
            else if (!password.equals(confirmPass)){
                Toast.makeText(getBaseContext(),
                        "Confirmation Password did not match orginal password",
                        Toast.LENGTH_SHORT)
                        .show();

                isValid = false;
            }
            if (isValid){
                signUpViewModel.signUp(SignUpActivity.this, LibAcitivity.class, email,
                        username, password);

                emailField.getText().clear();
                usernameField.getText().clear();
                passwordField.getText().clear();
                confirmPassField.getText().clear();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();

        addUserButt.setOnClickListener(null);
    }
}