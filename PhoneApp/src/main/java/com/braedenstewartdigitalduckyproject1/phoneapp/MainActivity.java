package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.braedenstewartdigitalduckyproject1.api.Verify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Verify.verifyPhoneApp();
    }
}