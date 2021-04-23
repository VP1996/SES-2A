package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectClassesStudent extends AppCompatActivity {



    // private RecyclerView mRecyclerView;
    //    private AdapterForSubjects mAdapter;
    //    private RecyclerView.LayoutManager mLayoutManager;
    //    public TextView welcomeTXT;
    //    String currentuser;
    //
    //    CurrentUser user;
    //
    //    Button btProfile,btLogOut;
    //    //Firebase
    //    private DatabaseReference reff1;
    //    private DatabaseReference reffy1;

    Button btnlogout, btnback, btnprofile;
    private AdapterForClasses mAdapter;
    TextView time, location, group, tut;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    public TextView ClassName;
    FirebaseDatabase reference;
    String currentuser, temp;
    RecyclerView recyclerView;
     CurrentUser user;
   DatabaseReference users, subjects, ref1;


   // referecce subject
    // edit the Student details or create a subject class like student details
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_student);
        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");
        String ID = user.getID();
        time= findViewById(R.id.time);
        location=findViewById(R.id.location);
        group =findViewById(R.id.group);
        tut = findViewById(R.id.tut);


        //reference = FirebaseDatabase.getInstance().getReference().child("SubjectInfo");


        subjects = FirebaseDatabase.getInstance().getReference().child("Subjects");
        users = FirebaseDatabase.getInstance().getReference().child("Users");// do another reference
        ArrayList<AddSubjectToClassesView> classlist = new ArrayList<>(); // edit this for subject
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String temp = snapshot.getKey();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        subjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String temp = snapshot.child(subject).getKey();
                    String temp2 = snapshot.child(subject).child("Act1").getKey();      // tut
                    String temp3 = temp + "  " + temp2;
                    tut.setText(temp3);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_student);
        btnlogout = findViewById(R.id.btn_logout_student);
        btnprofile = findViewById(R.id.btn_profile_student);    // reference th id here


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



    }


}
