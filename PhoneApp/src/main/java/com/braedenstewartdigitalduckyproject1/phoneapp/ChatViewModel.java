package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import com.braedenstewartdigitalduckyproject1.api.FirebaseHelper;
import com.braedenstewartdigitalduckyproject1.api.Message;

import java.time.LocalDateTime;

public class ChatViewModel extends BaseObservable {
    private static final String TAG = "ChatViewModel";
    private String chatTitle;
    private String thotId;
    private FirebaseHelper helper;
    private ChatAdapter adapter;
    private ObservableArrayList<Message> messageData;

    public ChatViewModel(){
        messageData = new ObservableArrayList<>();
        helper = new FirebaseHelper();
        adapter = new ChatAdapter(messageData);

        notifyPropertyChanged(BR.adapter);
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
        Message message = new Message();
        message.setAuthor(helper.getUsername());
        message.setContent(content);
        message.setPublishDate(LocalDateTime.now());

        if (!helper.saveMessage(TAG, message, thotId)){
            return "user message failed to reach server";
        }

        Message duckyMess = new Message();
        duckyMess.setAuthor("Ducky");
        duckyMess.setContent("Random message here");
        duckyMess.setPublishDate(LocalDateTime.now());

        if(!helper.saveMessage(TAG, duckyMess, thotId)){
            return "Ducky failed to respond";
        }
        messageData.add(message);
        messageData.add(duckyMess);

        notifyPropertyChanged(BR.messageData);
        return "";
    }

    @Bindable
    public ObservableArrayList<Message> getMessageData(){
        return this.messageData;
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
        messageData = (ObservableArrayList<Message>) helper.getLocalMesses();

        notifyPropertyChanged(BR.messageData);
    }
}
