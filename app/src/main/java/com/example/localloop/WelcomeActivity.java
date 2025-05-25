package com.example.localloop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String username = getIntent().getStringExtra("username");

        TextView welcomeText = findViewById(R.id.welcomeMessage);
        welcomeText.setText("Welcome Admin! You are logged in as \"" + username + "\".");
    }
}
