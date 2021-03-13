package com.braedenstewartdigitalduckyproject1.api;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    private String TAG = "ChatActivity";
    private FirebaseHelper helper;
    private ThoughtTrain thotTrain;
    MutableLiveData<ArrayList<Message>> messageLiveData;

    public ChatViewModel(){
        messageLiveData = new MutableLiveData<>();
        helper = new FirebaseHelper(FirebaseDatabase.getInstance().getReference());
        init();
    }

    public MutableLiveData<ArrayList<Message>> getMessageLiveData(){
        return messageLiveData;
    }

    public void init(){
        // grab values from firebase for ThoughtTrain
        // ThoughtTrain thotTrain = new ThoughtTrain(title, publishDate);
        //thotTrain.setMessages();
        //messageLiveData.setValue(ThoughtTrain.getMessages());
    }

    public boolean submitMessage(EditText textField){
        String content = textField.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String author = user.getDisplayName();
        LocalDateTime publishDate = LocalDateTime.now();

        addMessage(author, content, publishDate);
        addMessage("Ducky", "TODO", LocalDateTime.now());

        if (helper.saveThotTrain(TAG, thotTrain, user)){
            return true;
        }
        else{
            return false;
        }
    }

    public void addMessage(String author, String content, LocalDateTime publishDate){
        Message message = new Message(author, content, publishDate);

        thotTrain.addMessage(message);
    }

    public ArrayList<Message> getMessages(){
        return thotTrain.getMessages();
    }
}
