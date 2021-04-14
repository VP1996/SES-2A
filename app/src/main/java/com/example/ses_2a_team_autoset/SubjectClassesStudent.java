package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectClassesStudent extends AppCompatActivity {

    Button btnlogout, btnback, btnprofile;
    public TextView ClassName;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Studentdetails> list;
    Myadapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_student);

            recyclerView = (RecyclerView) findViewById(R.id.myRecyclerstudent);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            reference = FirebaseDatabase.getInstance().getReference().child("Users").child("ID").child("Subjects");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<Studentdetails>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Studentdetails p = dataSnapshot1.getValue(Studentdetails.class);
                        list.add(p);
                    }
                    adapter = new Myadapter(SubjectClassesStudent.this, list);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(SubjectClassesStudent.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                }
            });


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
