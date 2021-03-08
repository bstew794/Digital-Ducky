package com.braedenstewartdigitalduckyproject1.api;

import com.google.firebase.database.FirebaseDatabase;

public class Verify {
    public static void verifyPhoneApp() {
        FirebaseDatabase.getInstance().getReference().child("phoneAppVerified").setValue(true);
    }
}
