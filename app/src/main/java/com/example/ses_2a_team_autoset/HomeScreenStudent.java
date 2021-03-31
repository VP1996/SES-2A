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

public class HomeScreenStudent  extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AdapterForSubjects mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TextView welcomeTXT;
    String currentuser;

    CurrentUser user;

    Button btProfile,btLogOut;
    //Firebase
    private DatabaseReference reff1;
    private DatabaseReference reffy1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_student);
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


                                    mRecyclerView = findViewById(R.id.rvStudent1);
                                    mRecyclerView.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(HomeScreenStudent.this);
                                    mAdapter = new AdapterForSubjects(subjectList);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mAdapter.setOnItemClickListener(new AdapterForSubjects.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            String subject = subjectList.get(position).getSubject1();
                                            Intent intent = new Intent(HomeScreenStudent.this, SubjectClassesStudent.class);
                                            intent.putExtra("subject", subject);
                                            startActivity(intent);
                                        }
                                    });



                                }else {
                                    Toast.makeText(HomeScreenStudent.this, "Not found", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(HomeScreenStudent.this, "Not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(HomeScreenStudent.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        welcomeTXT = findViewById(R.id.welcometxt);
        btProfile = findViewById(R.id.btn_Profile);
        btLogOut = findViewById(R.id.btn_logout);

        currentuser = "Welcome " + user.getFirstName()+" "+ user.getLastName();
        welcomeTXT.setText(currentuser);

        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenStudent.this, Login.class));
            }
        });

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenStudent.this, "LOL no....", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
