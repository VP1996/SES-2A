package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.security.auth.Subject;

public class SubjectClassesStudent extends AppCompatActivity {

    Button btnlogout, btnback, btnprofile;
    public TextView ClassName;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_student);

        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");



        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_student);
        btnlogout = findViewById(R.id.btn_logout_student);
        btnprofile = findViewById(R.id.btn_profile_student);


        ClassName.setText(subject);
        btnlogout.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        startActivity(new Intent(SubjectClassesStudent.this, Login.class));
        }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        startActivity(new Intent(SubjectClassesStudent.this, HomeScreenStudent.class));
        }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        startActivity(new Intent(SubjectClassesStudent.this, Profile.class));
        }
        });


        }}
