package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import com.braedenstewartdigitalduckyproject1.api.FirebaseHelper;
import com.braedenstewartdigitalduckyproject1.api.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class ChatViewModel extends BaseObservable {
    private static final String TAG = "ChatViewModel";
    private String chatTitle;
    private String thotId;
    private FirebaseHelper helper;
    private ChatAdapter adapter;
    private String [] duckyMesses = {"Did you forget a semi-colon?", "Interesting... Tell me more.",
            "Why do you think that?", "Speak nerdy to me.",
            "Was the variable cast to the wrong data type?", "Reply hazy, try again.",
            "Let's walk and talk as you explain this.",
            "Explain it to me like you would for a toddler, or a college student; they're basically the same.",
            "Think about coding like catching a slippery weasel.",
            "This looks worse than I did before plastic surgery",
            "Got any... kernels?", "Don't worry, just put it on my bill."};

    public ChatViewModel(){
        helper = new FirebaseHelper();
        adapter = new ChatAdapter(helper.getLocalMesses());
    }

    public void setUp(String title, String id){
        chatTitle = title;
        thotId = id;

        notifyPropertyChanged(BR.chatTitle);
        populateData();
    }

    public void tearDown(){
        // remove listeners?
    }

    public String submitMessage(String content){
        DateTimeFormatter pubDateFormater = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        LocalDateTime pubDate = LocalDateTime.now();
        String pubDateStr = pubDate.format(pubDateFormater);

        Message message = new Message();
        message.setAuthor(helper.getUsername());
        message.setContent(content);
        message.setPublishDate(pubDateStr);

        if (!helper.saveMessage(TAG, message, thotId)){
            return "user message failed to reach server";
        }
        int randIndex = new Random().nextInt(duckyMesses.length);
        String duckyContent = duckyMesses[randIndex];

        pubDate = LocalDateTime.now();
        pubDateStr = pubDate.format(pubDateFormater);

        Message duckyMess = new Message();
        duckyMess.setAuthor("Ducky");
        duckyMess.setContent(duckyContent);
        duckyMess.setPublishDate(pubDateStr);

        if(!helper.saveMessage(TAG, duckyMess, thotId)){
            return "Ducky failed to respond";
        }
        return "";
    }

    @Bindable
    public ObservableArrayList<Message> getMessageData(){
        return this.helper.getLocalMesses();
    }

    @Bindable
    public ChatAdapter getAdapter(){
        return this.adapter;
    }

    @Bindable
    public String getChatTitle(){
        return this.chatTitle;
    }

    private void populateData(){
        helper.retrieveMesses(()->adapter.notifyDataSetChanged(), thotId);
    }
}
