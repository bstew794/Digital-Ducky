package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    Button loginButt;
    Button signUpButt;
    EditText emailField;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new LoginViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);
        loginButt = findViewById(R.id.login_butt);
        signUpButt = findViewById(R.id.sign_up_butt);

        loginButt.setOnClickListener(view ->{
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if ((email != null && email.length() > 0)&&(password != null && password.length() > 0)){
                loginViewModel.login(LoginActivity.this, LibAcitivity.class,
                        email, password);

                emailField.getText().clear();
                passwordField.getText().clear();
            }
            else{
                Toast.makeText(getBaseContext(), "Email or password field was empty",
                        Toast.LENGTH_SHORT).show();
            }
        });

        signUpButt.setOnClickListener(view ->{
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause(){
        super.onPause();

        loginButt.setOnClickListener(null);
        signUpButt.setOnClickListener(null);
    }
}