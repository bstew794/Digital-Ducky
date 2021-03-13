package com.braedenstewartdigitalduckyproject1.api;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ThoughtTrain {
    @Nullable
    private String title;
    private LocalDateTime publishDate;
    private ArrayList<Message> messages;

    public ThoughtTrain(String title, LocalDateTime publishDate){
        this.title = title;
        this.publishDate = publishDate;
        this.messages = new ArrayList<>();
    }
    @Nullable
    public String getTitle(){
        return title;
    }

    public void setTitle(@Nullable String title){
        this.title = title;
    }

    @Nullable
    public LocalDateTime getPublishDate(){
        return publishDate;
    }

    public void setPublishDate(@Nullable LocalDateTime publishDate){
        this.publishDate = publishDate;
    }

    public void addMessage(@Nullable Message message){
        messages.add(message);
    }

    @Nullable
    public ArrayList<Message> getMessages(){
        return messages;
    }
}
