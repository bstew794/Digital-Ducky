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
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.braedenstewartdigitalduckyproject1.phoneapp.databinding.ActivityChatBinding;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private static final int RECORD_AUDIO_REQUEST_CODE = 100;
    EditText addMessageField;
    Button submitButt;
    Button menuButt;
    ImageButton pushToTalk;
    ChatViewModel chatViewModel;
    RecyclerView rv;
    SpeechRecognizer sr;
    private Intent srIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = bind();

        initRV(view);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume(){
        super.onResume();

        chatViewModel.setUp(getIntent().getStringExtra("TITLE"),
                getIntent().getStringExtra("ID"));

        addMessageField = findViewById(R.id.add_message_field);
        submitButt = findViewById(R.id.submit_message_butt);
        menuButt = findViewById(R.id.to_lib_butt);
        pushToTalk = findViewById(R.id.push_to_talk);
        pushToTalk.setImageResource(R.drawable.ic_mic_off);
        sr = SpeechRecognizer.createSpeechRecognizer(getBaseContext());

        srIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        srIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "US-en");
        srIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        srIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                addMessageField.getText().clear();
                addMessageField.setHint("Listening...");
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
                Log.e("Speech Recognizer",
                        "Error code of " + error);

                pushToTalk.setImageResource(R.drawable.ic_mic_off);
                addMessageField.setHint("type here");
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(sr.RESULTS_RECOGNITION);
                String text = "";

                for (String result : matches){
                    text = result;
                }
                pushToTalk.setImageResource(R.drawable.ic_mic_off);
                addMessageField.setText(text);
                addMessageField.setHint("type here");
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

                if (toastText.equals("")){
                    addMessageField.getText().clear();
                }
                else{
                    Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getBaseContext(), "You cannot submit an empty message",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        menuButt.setOnClickListener(view ->{
            Intent intent = new Intent(this, LibAcitivity.class);

            startActivity(intent);
        });

        pushToTalk.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP){
                sr.stopListening();
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                if (ContextCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            RECORD_AUDIO_REQUEST_CODE);
                }
                else{
                    pushToTalk.setImageResource(R.drawable.ic_mic_on);
                    sr.startListening(srIntent);
                }
            }
            return false;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.tearDown();

        submitButt.setOnClickListener(null);
        menuButt.setOnClickListener(null);
        pushToTalk.setOnClickListener(null);
        sr.setRecognitionListener(null);
    }

    @Override
    protected void onStop(){
        super.onStop();

        if (sr != null){
            sr.destroy();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getBaseContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                pushToTalk.setImageResource(R.drawable.ic_mic_on);
                sr.startListening(srIntent);
            }
            else{
                Toast.makeText(getBaseContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}