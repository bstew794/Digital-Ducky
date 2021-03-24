package com.braedenstewartdigitalduckyproject1.api;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FirebaseHelper {
    private DatabaseReference myDB;
    private FirebaseAuth myAuth;
    private FirebaseUser user;
    private ArrayList<ThoughtTrain> localThots;
    private ArrayList<Message> localMesses;
    private ArrayList<String> thotKeys;
    private ArrayList<String> messKeys;
    private boolean loginSuccess;

    public FirebaseHelper(){
        this.myDB = FirebaseDatabase.getInstance().getReference();
        this.myAuth = FirebaseAuth.getInstance();
        this.user = myAuth.getCurrentUser();
        this.localThots = new ArrayList<>();
        this.localMesses = new ArrayList<>();
        this.thotKeys = new ArrayList<>();
        this.messKeys = new ArrayList<>();
        this.loginSuccess = false;
    }

    public boolean saveThotTrain(String TAG, ThoughtTrain thotTrain){
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

    public boolean saveMessage(String TAG, Message message, String thotId){
        if (message == null){
            return false;
        }
        else{
            try {
                myDB.child("Users").child(user.getUid()).child(thotId).child("Messages").push()
                        .setValue(message);

                return true;
            }
            catch (DatabaseException e){
                Log.e(TAG, "Message was not saved successfully");
                return false;
            }
        }
    }

    public void login(Activity activity, String email, String password){
        myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()){
                        Log.d("loginViewModel", "Login succeeded");
                        user = myAuth.getCurrentUser();
                        loginSuccess = true;
                    }
                    else{
                        Log.d("loginViewModel", "Login failed");
                        loginSuccess = false;
                    }
                });
    }

    public void signOut(){
        myAuth.signOut();
    }

    public interface OnDataChangedCallback{
        public void onDataChanged();
    }

    public void retrieveThots(OnDataChangedCallback callback){
        myDB.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fetchThotData(snapshot);
                callback.onDataChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void retrieveMesses(OnDataChangedCallback callback, String thotId){
        myDB.child("Users").child(user.getUid()).child(thotId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getValue(ThoughtTrain.class).setPublishDate(LocalDateTime.now());
                        fetchMessData(snapshot.child("Messages"));
                        callback.onDataChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public String getUsername(){
        return this.user.getDisplayName();
    }

    public ArrayList<ThoughtTrain> getLocalThots(){
        return localThots;
    }

    public ArrayList<Message> getLocalMesses(){
        return localMesses;
    }

    public ArrayList<String> getThotKeys(){
        return thotKeys;
    }

    public ArrayList<String> getMessKeys(){
        return messKeys;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    private void fetchThotData(DataSnapshot snapshot){
        localThots.clear();
        thotKeys.clear();

        for (DataSnapshot ds : snapshot.getChildren()){
            ThoughtTrain thotTrain = ds.getValue(ThoughtTrain.class);

            if (thotTrain != null){
                localThots.add(thotTrain);
                thotKeys.add(ds.getKey());
            }
        }
    }

    private void fetchMessData(DataSnapshot snapshot){
        localMesses.clear();
        messKeys.clear();

        for (DataSnapshot ds : snapshot.getChildren()){
            Message message = ds.getValue(Message.class);

            if (message != null){
                localMesses.add(message);
                messKeys.add(ds.getKey());
            }
        }
    }
}
