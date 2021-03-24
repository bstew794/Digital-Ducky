package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.databinding.BaseObservable;

import com.braedenstewartdigitalduckyproject1.api.FirebaseHelper;

public class SignUpViewModel extends BaseObservable {
    private FirebaseHelper helper;

    public SignUpViewModel(){
        helper = new FirebaseHelper();
    }

    public void setUp(){

    }

    public void tearDown(){

    }

    public String signUp(String email, String username, String password){
        return email;
    }
}
