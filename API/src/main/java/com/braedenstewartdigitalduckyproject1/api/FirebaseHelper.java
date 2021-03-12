package com.braedenstewartdigitalduckyproject1.api;

import com.google.firebase.database.DatabaseReference;

public class FirebaseHelper {
    private DatabaseReference myDB;
    private boolean saved;

    public FirebaseHelper(DatabaseReference db){
        this.myDB = db;
    }
}
