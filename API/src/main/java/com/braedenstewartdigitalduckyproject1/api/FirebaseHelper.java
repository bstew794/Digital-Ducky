package com.braedenstewartdigitalduckyproject1.api;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

public class FirebaseHelper {
    private DatabaseReference myDB;
    private boolean saved;

    public FirebaseHelper(DatabaseReference db){
        this.myDB = db;
    }

    public boolean saveThotTrain(String TAG, ThoughtTrain thotTrain, FirebaseUser user){
        if (thotTrain == null){
            return false;
        }
        else{
            try {
                myDB.child("Users").child(user.getUid()).push().setValue(thotTrain);
                return true;
            }
            catch (DatabaseException e){
                Log.e(TAG, "Thought Train was not saved successfully");
                return false;
            }
        }
    }
}
