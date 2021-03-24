package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.braedenstewartdigitalduckyproject1.phoneapp.databinding.ActivityChatBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatActivity extends AppCompatActivity {
    EditText addMessageField;
    Button submitButt;
    ImageButton pushToTalk;
    FloatingActionButton returnToLib;
    ChatViewModel chatViewModel;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = bind();

        initRV(view);
    }
    @Override
    protected void onResume(){
        super.onResume();

        chatViewModel.setUp(getIntent().getStringExtra("TITLE"),
                getIntent().getStringExtra("ID"));

        addMessageField = findViewById(R.id.add_message_field);
        submitButt = findViewById(R.id.submit_message_butt);
        pushToTalk = findViewById(R.id.push_to_talk);
        returnToLib = findViewById(R.id.return_to_lib_butt);

        submitButt.setOnClickListener(view -> {
            String content = addMessageField.getText().toString();

            if (content != null && content.length() > 0){
                String toastText = chatViewModel.submitMessage(addMessageField.toString());

                if (toastText == ""){
                    addMessageField.getText().clear();
                }
                else{
                    Toast.makeText(ChatActivity.this, toastText, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(ChatActivity.this, "You cannot submit an empty message",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        pushToTalk.setOnClickListener(view -> {
            // TODO: set up speech-to-text API
        });

        returnToLib.setOnClickListener(view ->{
            //TODO: set up call to library activity
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.tearDown();

        submitButt.setOnClickListener(null);
        pushToTalk.setOnClickListener(null);
        returnToLib.setOnClickListener(null);
    }

    private View bind(){
        ActivityChatBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        chatViewModel = new ChatViewModel();
        binding.setViewModel(chatViewModel);
        return binding.getRoot();
    }

    private void initRV(View view){
        rv = view.findViewById(R.id.chat_rv);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(chatViewModel.getAdapter());
    }
}