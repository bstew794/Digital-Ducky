package com.braedenstewartdigitalduckyproject1.api;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ThoughtTrain {
    @Nullable
    private String title;
    private String publishDate;

    public ThoughtTrain(){
    }
    @Nullable
    public String getTitle(){
        return title;
    }

    public void setTitle(@Nullable String title){
        this.title = title;
    }

    @Nullable
    public String getPublishDate(){
        return publishDate;
    }

    public void setPublishDate(@Nullable String publishDate){
        this.publishDate = publishDate;
    }
}
