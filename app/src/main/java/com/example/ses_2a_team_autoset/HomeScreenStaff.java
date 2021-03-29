package com.example.ses_2a_team_autoset;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class HomeScreenStaff extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TextView welocmeTXT;
    String curretnuser;

    CurrentUser user;


    Button btSubjectList,btLogOut,btRegisterNewStudent,btRequests;
    //Firebase
    private DatabaseReference reff1;
    private DatabaseReference reffy1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_staff);
        String ID = user.getID();

        reff1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ID).child("Subjects");
        ArrayList<AddSubjectToSubjectView> subjectList = new ArrayList<>();




        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    float count = dataSnapshot.getChildrenCount();
                    for (int i = 1; i <= count; i++){
                        String temp = String.valueOf(i);
                        reffy1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ID).child("Subjects").child(temp);
                        reffy1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    subjectList.add(new AddSubjectToSubjectView(dataSnapshot.getValue().toString()));

                                    mRecyclerView = findViewById(R.id.rvAdmin1);
                                    mRecyclerView.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(HomeScreenStaff.this);
                                    mAdapter = new AdapterForSubjects(subjectList);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);

                                }else {
                                    Toast.makeText(HomeScreenStaff.this, "Not found", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(HomeScreenStaff.this, "Not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(HomeScreenStaff.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        welocmeTXT = findViewById(R.id.welocmetxt);
        btSubjectList = findViewById(R.id.btn_subjectlist);
        btLogOut = findViewById(R.id.btn_logout);
        btRequests = findViewById(R.id.btn_replytoreq);
        btRegisterNewStudent = findViewById(R.id.btn_regnew);

        curretnuser = "Welocome " + user.getFirstName()+" "+ user.getLastName();
        welocmeTXT.setText(curretnuser);



        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenStaff.this, Login.class));
            }
        });

        btSubjectList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenStaff.this, "LOL no....", Toast.LENGTH_SHORT).show();
            }
        });
        btRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenStaff.this, "LOL no....", Toast.LENGTH_SHORT).show();
            }
        });
        btRegisterNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenStaff.this, Register.class));
            }
        });
    }

}
