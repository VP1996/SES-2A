package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SubjectClassesAdmin extends AppCompatActivity {

    Button btnlogout, btnback;
    public TextView ClassName;
    private AdapterForClasses mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    FirebaseDatabase reference;
    String currentuser;
    RecyclerView recyclerView;
    CurrentUser user;
    DatabaseReference users, subjects, ref1;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_admin);
        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");
        String ID = user.getID();

        ref1 = FirebaseDatabase.getInstance().getReference();
        ArrayList<AddSubjectToClassesView> classlist = new ArrayList<>(); // edit this for subject

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    float count = dataSnapshot.child("Subjects").child(subject).getChildrenCount();
                    for (int i = 1; i <= count; i++){
                        String temp = "Tut" + String.valueOf(i);      // act + String

                        ref1 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject).child(temp);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    // build tutorial and act string
                                    String act = dataSnapshot.getKey();
                                    // build day and time string
                                    String dayNTime = "Time: " + dataSnapshot.child("DayNTime").getValue().toString();
                                    // build location string
                                    String location = "Location: " + dataSnapshot.child("Location").getValue().toString();
                                    // build Group string
                                    String group = "Allowed Group Sizes: " + dataSnapshot.child("GroupSizes").getValue().toString();

                                    classlist.add(new AddSubjectToClassesView(act,dayNTime,location,group));


                                    mRecyclerView = findViewById(R.id.myRecyclerViewForClassesAdmin);
                                    mRecyclerView.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(SubjectClassesAdmin.this);
                                    mAdapter = new AdapterForClasses(classlist);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);

                                    mAdapter.setOnItemClickListener(new AdapterForClasses.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            String classType = act;
                                            Intent intent = new Intent(SubjectClassesAdmin.this, ListOfGroups.class);
                                            intent.putExtra("classType", classType);
                                            intent.putExtra("subject", subject);
                                            startActivity(intent);
                                        }
                                    });

                                }else {
                                    Toast.makeText(SubjectClassesAdmin.this, "Not found", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(SubjectClassesAdmin.this, "Not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(SubjectClassesAdmin.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_admin);
        btnlogout = findViewById(R.id.btn_logout_admin);

        ClassName.setText(subject);

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






    }

}
