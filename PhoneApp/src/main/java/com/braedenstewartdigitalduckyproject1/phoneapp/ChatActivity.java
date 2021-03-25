package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.braedenstewartdigitalduckyproject1.phoneapp.databinding.ActivityChatBinding;

import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    public static final Integer recordAudioRequestCode = 1;
    EditText addMessageField;
    Button submitButt;
    ImageView pushToTalk;
    ChatViewModel chatViewModel;
    RecyclerView rv;
    SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = bind();

        initRV(view);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){

            checkPermission();
        }
        sr.createSpeechRecognizer(this);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume(){
        super.onResume();

        chatViewModel.setUp(getIntent().getStringExtra("TITLE"),
                getIntent().getStringExtra("ID"));

        addMessageField = findViewById(R.id.add_message_field);
        submitButt = findViewById(R.id.submit_message_butt);
        pushToTalk = findViewById(R.id.push_to_talk);

        final Intent srIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        srIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        srIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                addMessageField.getText().clear();
                addMessageField.setHint("@string/listening");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                Log.e("speech Recognizer", "Error code of " + error);
            }

            @Override
            public void onResults(Bundle results) {
                pushToTalk.setImageResource(R.drawable.ic_mic_off);
                String content = results.getString(SpeechRecognizer.RESULTS_RECOGNITION);

                if (content != null && content.length() > 0){
                    String toastText = chatViewModel.submitMessage(content);

                    if (toastText == ""){
                        addMessageField.getText().clear();
                        addMessageField.setHint("@string/text_field");
                    }
                    else{
                        Toast.makeText(ChatActivity.this, toastText, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                else{
                    Toast.makeText(ChatActivity.this,
                            "You cannot submit an empty message", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        submitButt.setOnClickListener(view -> {
            String content = addMessageField.getText().toString();

            if (content != null && content.length() > 0){
                String toastText = chatViewModel.submitMessage(content);

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

        pushToTalk.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                sr.stopListening();
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                pushToTalk.setImageResource(R.drawable.ic_mic_on);
                sr.startListening(srIntent);
            }
            return false;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.tearDown();

        submitButt.setOnClickListener(null);
        pushToTalk.setOnClickListener(null);
        sr.setRecognitionListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sr.destroy();
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

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, recordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == recordAudioRequestCode && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}