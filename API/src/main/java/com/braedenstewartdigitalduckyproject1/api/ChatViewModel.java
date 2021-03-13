package com.braedenstewartdigitalduckyproject1.api;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    MutableLiveData<ArrayList<Message>> messageLiveData;

    public ChatViewModel(){
        messageLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Message>> getMessageLiveData(){
        return messageLiveData;
    }

    public void init(){
        // grab values from firebase for ThoughtTrain
        // ThoughtTrain thotTrain = new ThoughtTrain(title, publishDate)
        //thotTrain.

        //messageLiveData.setValue(ThoughtTrain.getMessages());
    }
}
