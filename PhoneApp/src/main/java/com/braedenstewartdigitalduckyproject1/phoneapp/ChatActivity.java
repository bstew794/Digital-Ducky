package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braedenstewartdigitalduckyproject1.api.FirebaseHelper;
import com.braedenstewartdigitalduckyproject1.api.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;

public class ChatActivity extends AppCompatActivity {
    DatabaseReference DB;
    FirebaseHelper helper;
    ChatAdapter adapter;
    RecyclerView rv;
    EditText addMessageField;
    Button submitButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        addMessageField = findViewById(R.id.add_message_field);
        submitButt = findViewById(R.id.submit_message_butt);

        rv = findViewById(R.id.chat_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        DB = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(DB);

        //retrieve Data set from database

        rv.setAdapter(adapter);

        submitButt.setOnClickListener(view -> {
            String author = null; // get username
            LocalDateTime publishDate = LocalDateTime.now()
            String content = addMessageField.getText().toString();

            if (false){
                Toast.makeText(ChatActivity.this,
                        "you cannot enter nothing", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}