package com.braedenstewartdigitalduckyproject1.api;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;

public class Message {
    @Nullable
    String author, content;
    String publishDate;

    public Message(){
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
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(@Nullable String publishDate){
        this.publishDate = publishDate;
    }
}
