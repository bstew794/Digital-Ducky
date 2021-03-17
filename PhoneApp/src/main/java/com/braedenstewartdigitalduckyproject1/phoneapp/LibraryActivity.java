package com.braedenstewartdigitalduckyproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braedenstewartdigitalduckyproject1.phoneapp.databinding.ActivityLibraryBinding;

public class LibraryActivity extends AppCompatActivity {
    EditText addThotField;
    Button submitButt;
    Button logoutButt;
    LibViewModel libViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = bind();

        initRV(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        libViewModel.setUp();

        addThotField = findViewById(R.id.add_thot_train_field);
        submitButt = findViewById(R.id.submit_title_butt);
        logoutButt = findViewById(R.id.logout_butt);

        submitButt.setOnClickListener(view -> {
            String thotTitle = addThotField.getText().toString();

            if (thotTitle != null && thotTitle.length() > 0){
                String toastText = libViewModel.submitThot(thotTitle);

                if (toastText == ""){
                    addThotField.getText().clear();
                }
                else{
                    Toast.makeText(LibraryActivity.this, toastText, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        logoutButt.setOnClickListener(view -> {
            //TODO: implement logout user function
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        libViewModel.tearDown();

        submitButt.setOnClickListener(null);
        logoutButt.setOnClickListener(null);
    }

    private View bind(){
        ActivityLibraryBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_library);

        libViewModel = new LibViewModel();
        binding.setViewModel(libViewModel);
        return binding.getRoot();
    }

    private void initRV(View view){
        RecyclerView rv = view.findViewById(R.id.library_rv);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
    }
}