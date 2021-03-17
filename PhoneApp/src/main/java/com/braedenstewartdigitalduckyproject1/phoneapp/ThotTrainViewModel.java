package com.braedenstewartdigitalduckyproject1.phoneapp;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.braedenstewartdigitalduckyproject1.api.ThoughtTrain;

import java.time.LocalDateTime;

public class ThotTrainViewModel extends BaseObservable {
    private ThoughtTrain thotTrain;

    public ThotTrainViewModel(ThoughtTrain thotTrain){
        this.thotTrain = thotTrain;
    }

    public void setUp(){

    }
    public void tearDown(){

    }

    @Bindable
    public String getTitle(){
        String title = thotTrain.getTitle();

        if (TextUtils.isEmpty(title)){
            return "";
        }
        else{
            return title;
        }
    }

    @Bindable
    public LocalDateTime getPublishDate(){
        LocalDateTime pubDate = thotTrain.getPublishDate();

        if (pubDate == null){
            return LocalDateTime.now();
        }
        else{
            return pubDate;
        }
    }
}
