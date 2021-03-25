package com.braedenstewartdigitalduckyproject1.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;

public class FirebaseHelper {
    private DatabaseReference myDB;
    private FirebaseAuth myAuth;
    private FirebaseUser user;
    private ObservableArrayList<ThoughtTrain> localThots;
    private ObservableArrayList<Message> localMesses;
    private ObservableArrayList<String> thotKeys;
    private ObservableArrayList<String> messKeys;

    public FirebaseHelper(){
        this.myDB = FirebaseDatabase.getInstance().getReference();
        this.myAuth = FirebaseAuth.getInstance();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.localThots = new ObservableArrayList<>();
        this.localMesses = new ObservableArrayList<>();
        this.thotKeys = new ObservableArrayList<>();
        this.messKeys = new ObservableArrayList<>();
    }

    public boolean saveThotTrain(String TAG, ThoughtTrain thotTrain){
        if (thotTrain == null){
            return false;
        }
        else{
            try {
                myDB.child("Users").child(user.getUid()).push().child("ThotTrain")
                        .setValue(thotTrain);

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

    public void login(Activity activity, Class goTo, String email, String password){
        myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()){
                        Log.d("loginViewModel", "Login succeeded");
                        user = FirebaseAuth.getInstance().getCurrentUser();

                        Intent intent = new Intent(activity, goTo);

                        activity.startActivity(intent);
                    }
                    else{
                        Log.d("loginViewModel", "Login failed");

                        Toast.makeText(activity, "Email or Password was incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void signUp(Activity activity, Class goTo, String email, String myUsername, String password){
        myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                   if (task.isSuccessful()){
                       Log.d("signUpViewModel", "Sign up succeeded");

                       user = FirebaseAuth.getInstance().getCurrentUser();

                       UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                               .Builder().setDisplayName(myUsername).build();

                       user.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                           if (task1.isSuccessful()){
                               Log.d("signUpViewModel", "Username set");

                               Intent intent = new Intent(activity, goTo);

                               activity.startActivity(intent);
                           }
                           else{
                               Log.d("signUpViewModel", "Username not set");

                               Toast.makeText(activity, "Sign Up failed",
                                       Toast.LENGTH_LONG).show();
                           }
                       });
                   }
                   else{
                       Log.d("signUpViewModel", "Sign up failed");

                       Toast.makeText(activity, "Sign Up failed",
                               Toast.LENGTH_LONG).show();
                   }
                });
    }

    public void logout(){
        myAuth.signOut();
    }

    public interface OnDataChangedCallback{
        void onDataChanged();
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
                        snapshot.child("ThotTrain").getValue(ThoughtTrain.class)
                                .setPublishDate(LocalDateTime.now().toString());

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

    public ObservableArrayList<ThoughtTrain> getLocalThots(){
        return localThots;
    }

    public ObservableArrayList<Message> getLocalMesses(){
        return localMesses;
    }

    public ObservableArrayList<String> getThotKeys(){
        return thotKeys;
    }

    public ObservableArrayList<String> getMessKeys(){
        return messKeys;
    }

    private void fetchThotData(DataSnapshot snapshot){
        localThots.clear();
        thotKeys.clear();

        for (DataSnapshot ds : snapshot.getChildren()){
            ThoughtTrain thotTrain = ds.child("ThotTrain").getValue(ThoughtTrain.class);

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
