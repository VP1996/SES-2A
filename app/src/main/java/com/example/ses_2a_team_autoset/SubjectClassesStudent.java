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
    private AdapterForClasses mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    public TextView ClassName;
    FirebaseDatabase reference;
    String currentuser;
    String currentGroup = "Pending";
    RecyclerView recyclerView;
     CurrentUser user;
   DatabaseReference users, subjects, ref1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_student);
        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");
        String ID = user.getID();



        //reference = FirebaseDatabase.getInstance().getReference().child("SubjectInfo");


        //subjects = FirebaseDatabase.getInstance().getReference().child("Subjects");
        //users = FirebaseDatabase.getInstance().getReference().child("Users");// do another reference
        ref1 = FirebaseDatabase.getInstance().getReference();
        ArrayList<AddSubjectToClassesView> classlist = new ArrayList<>(); // edit this for subject

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    float count = dataSnapshot.child("Subjects").child(subject).child("Tut1").getChildrenCount();
                    for (int i = 1; i <= count; i++){
                        String temp = "Act " + String.valueOf(i);      // act + String

                        ref1 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject).child("Tut1").child(temp);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    // build tutorial and act string
                                    String act =  "Tut1" + " - " + dataSnapshot.getKey();
                                    // build day and time string
                                    String dayNTime = "Time: " + dataSnapshot.child("DayNTime").getValue().toString();
                                    // build location string
                                    String location = "Location: " + dataSnapshot.child("Location").getValue().toString();
                                    // build Group string

                                    float countAgain = dataSnapshot.child("Groups").getChildrenCount();
                                    for (int x = 1; x <= countAgain; x++){
                                        String tempAGAIN = "Group"+String.valueOf(x);
                                        float countAgain1 = dataSnapshot.child("Groups").child(tempAGAIN).getChildrenCount();
                                        for (int g = 1; g <= countAgain1; g++){
                                            String fsd = String.valueOf(g);
                                            String temp1 = dataSnapshot.child("Groups").child(tempAGAIN).child(fsd).getValue().toString();
                                            if(temp1.equals(ID)){
                                                currentGroup = tempAGAIN;
                                            }
                                        }
                                    }

                                    String group = "Group: " + currentGroup;

                                    classlist.add(new AddSubjectToClassesView(act,dayNTime,location,group));


                                    mRecyclerView = findViewById(R.id.myRecyclerViewForClassesStud);
                                    mRecyclerView.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(SubjectClassesStudent.this);
                                    mAdapter = new AdapterForClasses(classlist);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);

                                }else {
                                    Toast.makeText(SubjectClassesStudent.this, "Not found", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(SubjectClassesStudent.this, "Not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(SubjectClassesStudent.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_student);
        btnlogout = findViewById(R.id.btn_logout_student);
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




    }


}
