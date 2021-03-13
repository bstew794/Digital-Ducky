package com.braedenstewartdigitalduckyproject1.api;

import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    private ThoughtTrain thotTrain;
    MutableLiveData<ArrayList<Message>> messageLiveData;

    public ChatViewModel(){
        messageLiveData = new MutableLiveData<>();
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

        if (content != null && content.length() > 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            String author = user.getDisplayName();
            LocalDateTime publishDate = LocalDateTime.now();

            addMessage(author, content, publishDate);
            addMessage("Ducky", "TODO", LocalDateTime.now());

            return true;
        }
        else{
            return false;
        }
    }

    public void addMessage(String author, String content, LocalDateTime publishDate){

    }
}
