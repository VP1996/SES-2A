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

public class ListOfGroups extends AppCompatActivity {

    Button btnlogout, btnback;
    public TextView SubjectName,ClassName;
    private AdapterForListOfGroups mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    FirebaseDatabase reference;
    String StudentsinGroup = "";
    RecyclerView recyclerView;
    CurrentUser user;
    DatabaseReference users, subjects, ref1;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_admin);
        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");
        String classType = bundle.getString("classType");
        String ID = user.getID();

        ref1 = FirebaseDatabase.getInstance().getReference();
        ArrayList<AddGroupToGroupListView> groupList = new ArrayList<>(); // edit this for subject

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    float count = dataSnapshot.child("Subjects").child(subject).child(classType).child("Groups").getChildrenCount();
                    for (int i = 1; i <= count; i++){
                        String temp = "Group" + String.valueOf(i);      // act + String

                        ref1 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject).child(classType).child("Groups").child(temp);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    String GroupNumber = dataSnapshot.getKey();
                                    float countAgain = dataSnapshot.getChildrenCount();
                                    for(int f = 1;f<=countAgain;f++){
                                        String temp1 = String.valueOf(f);
                                        StudentsinGroup = StudentsinGroup + temp1+ ", ";
                                    }



                                    groupList.add(new AddGroupToGroupListView(GroupNumber,StudentsinGroup));




                                    mRecyclerView = findViewById(R.id.myRecyclerViewForListOfGroups);
                                    mRecyclerView.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(ListOfGroups.this);
                                    mAdapter = new AdapterForListOfGroups(groupList);
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);



                                }else {
                                    Toast.makeText(ListOfGroups.this, "Not found", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ListOfGroups.this, "Not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Toast.makeText(ListOfGroups.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SubjectName = findViewById(R.id.SubjectName);
        ClassName = findViewById(R.id.ClassType);
        btnback = findViewById(R.id.btn_back_admin);
        btnlogout = findViewById(R.id.btn_logout_admin);

        SubjectName.setText(subject);
        ClassName.setText(classType);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListOfGroups.this, Login.class));
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListOfGroups.this, HomeScreenStaff.class));
            }
        });






    }

}
