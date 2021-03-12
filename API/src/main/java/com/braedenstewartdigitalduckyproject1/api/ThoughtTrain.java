package com.braedenstewartdigitalduckyproject1.api;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ThoughtTrain {
    @Nullable
    private LocalDateTime publishDate;
    private List<Message> messageList;
    private MutableLiveData<List<Message>> messages;

    public ThoughtTrain(LocalDateTime publishDate){
        this.publishDate = publishDate;
        this.messageList = new ArrayList<>();
        this.messages = new MutableLiveData<>();
    }

    @Nullable
    public LocalDateTime getPublishDate(){
        return publishDate;
    }

    public void setPublishDate(@Nullable LocalDateTime publishDate){
        this.publishDate = publishDate;
    }

    public void addMessage(@Nullable Message message){
        messageList.add(message);
    }

    @Nullable
    public MutableLiveData<List<Message>> getMessages(){
        return messages;
    }
}
