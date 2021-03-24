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

    public String login(Activity activity, String email, String password){
        helper.login(activity, email, password);

        if (helper.isLoginSuccess()){
            return "";
        }
        else{
            return "password and/or email was incorrect";
        }
    }
}
