package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Mailbox extends AppCompatActivity {
    Button btnback, btnlogout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailbox);

        btnback = findViewById(R.id.btn_back_mailbox);
        btnlogout = findViewById(R.id.btn_logout_mailbox);


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mailbox.this, Login.class));
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Mailbox.this, HomeScreenStaff.class));
            }
        });

    }}
