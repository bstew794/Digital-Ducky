package com.braedenstewartdigitalduckyproject1.phoneapp;

import android.app.Activity;
import android.util.Log;

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

    public void signUp(Activity activity, Class goTo, String email, String username, String password){
        helper.signUp(activity, goTo, email, username, password);
    }
}
