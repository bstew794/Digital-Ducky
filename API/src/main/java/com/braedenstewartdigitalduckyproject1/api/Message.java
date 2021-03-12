package com.braedenstewartdigitalduckyproject1.api;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;

public class Message {
    @Nullable
    String author, content;
    LocalDateTime publishDate;

    public Message(String author, String content){
        this.author = author;
        this.content = content;
        this.publishDate = LocalDateTime.now();
    }

    @Nullable
    public String getAuthor(){
        return author;
    }

    public void setAuthor(@Nullable String author){
        this.author = author;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    public void setContent(@Nullable String content){
        this.content = content;
    }

    @Nullable
    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(){
        this.publishDate = LocalDateTime.now();
    }
}