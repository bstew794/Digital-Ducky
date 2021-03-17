package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import com.braedenstewartdigitalduckyproject1.api.FirebaseHelper;
import com.braedenstewartdigitalduckyproject1.api.ThoughtTrain;

import java.time.LocalDateTime;

public class LibViewModel extends BaseObservable {
    private static final String TAG = "LibViewModel";
    private String welMess;
    private FirebaseHelper helper;
    private LibAdapter adapter;
    private ObservableArrayList<ThoughtTrain> thotData;
    private ObservableArrayList<String> thotIdData;

    public LibViewModel(){
        thotData = new ObservableArrayList<>();
        thotIdData = new ObservableArrayList<>();
        helper = new FirebaseHelper();
        adapter = new LibAdapter(thotData, thotIdData);

        notifyPropertyChanged(BR.adapter);
    }

    public void setUp(){
        welMess = "Welcome " + helper.getUsername();

        notifyPropertyChanged(BR.welMess);
        populateData();
    }

    public void tearDown(){
        // remove listeners?
    }

    public String submitThot(String title){
        ThoughtTrain thotTrain = new ThoughtTrain();
        thotTrain.setTitle(title);
        thotTrain.setPublishDate(LocalDateTime.now());

        if (!helper.saveThotTrain(TAG, thotTrain)){
            return "user thought train failed to reach server";
        }
        notifyPropertyChanged(BR.thotData);
        return "";
    }

    @Bindable
    public ObservableArrayList<ThoughtTrain> getThotData(){
        return this.thotData;
    }

    @Bindable
    public LibAdapter getAdapter(){
        return this.adapter;
    }

    @Bindable
    public String getWelMess(){
        return this.welMess;
    }

    private void populateData(){
        helper.retrieveThots(()->adapter.notifyDataSetChanged());
        thotData = (ObservableArrayList<ThoughtTrain>) helper.getLocalThots();
        thotIdData = (ObservableArrayList<String>) helper.getThotKeys();

        notifyPropertyChanged(BR.thotData);
    }
}
