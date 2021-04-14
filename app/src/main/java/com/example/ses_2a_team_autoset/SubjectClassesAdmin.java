package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectClassesAdmin extends AppCompatActivity {
    Button btnlogout, btnback, btnmailbox;
    public TextView ClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_admin);

        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");

        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_admin);
        btnlogout = findViewById(R.id.btn_logout_admin);
        btnmailbox = findViewById(R.id.btn_mailbox_admin);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, Login.class));
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, HomeScreenStaff.class));
            }
        });
        btnmailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, Mailbox.class));
            }
        });
        ClassName.setText(subject);
    }
}

