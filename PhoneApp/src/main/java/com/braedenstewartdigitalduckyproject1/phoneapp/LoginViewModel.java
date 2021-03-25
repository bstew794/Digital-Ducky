package com.braedenstewartdigitalduckyproject1.phoneapp;

import android.app.Activity;

import androidx.databinding.BaseObservable;

import com.braedenstewartdigitalduckyproject1.api.FirebaseHelper;

public class LoginViewModel extends BaseObservable {
    private FirebaseHelper helper;

    public LoginViewModel(){
        helper = new FirebaseHelper();
    }

    public void setUp(){

    }

    public void tearDown(){

    }

    public void login(Activity activity, Class goTo, String email, String password){
        helper.login(activity, goTo, email, password);
    }
}
