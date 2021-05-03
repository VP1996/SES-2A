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


public class Mailbox extends AppCompatActivity {
    Button btnback, btnlogout;

    private AdapterForMailbox mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    public TextView ClassName;
    FirebaseDatabase reference;
    String currentuser;
    String classname,subjName;
    RecyclerView recyclerView;
    CurrentUser user;
    int Countmembers = 0;
    DatabaseReference ref3, ref1,ref2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailbox);
        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");
        String ID = user.getID();
        btnback = findViewById(R.id.btn_back_mailbox);
        btnlogout = findViewById(R.id.btn_logout_mailbox);
        ArrayList<String> StudentsInMailList = new ArrayList<>();
        ref1 = FirebaseDatabase.getInstance().getReference();
        ref1 = FirebaseDatabase.getInstance().getReference().child("Mailbox").child(subject);
        ref2 = FirebaseDatabase.getInstance().getReference();
        ref2 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject);
        ref3 = FirebaseDatabase.getInstance().getReference();
        ref3 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject);
        ArrayList<AddMailToMailboxView> maillist = new ArrayList<>();


        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String SID = snapshot.getKey();
                        String FullNameSID = snapshot.child("fullName").getValue().toString()+ "-"+SID;
                        String SubjectClassType = snapshot.child("subject").getValue().toString()+ "-"+snapshot.child("classType").getValue().toString();
                        String CurrentGroup = "Current Group Number: "+snapshot.child("currentGroup").getValue().toString();
                        String ExpectedGroup = "Requested Group Number: "+snapshot.child("expectedGroup").getValue().toString();
                        String Reason = "Comment: "+snapshot.child("reason").getValue().toString();

                        maillist.add(new AddMailToMailboxView(FullNameSID,SubjectClassType,CurrentGroup,ExpectedGroup,Reason,SID));

                        mRecyclerView = findViewById(R.id.rvMailbox);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(Mailbox.this);
                        mAdapter = new AdapterForMailbox(maillist);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(new AdapterForMailbox.OnItemClickListener() {
                        @Override
                        public void onApprove(int position) {
                            String StudentID = maillist.get(position).getStudentID();
                            String ClassType = dataSnapshot.child(StudentID).child("classType").getValue().toString();
                            String CGroup = "Group" + dataSnapshot.child(StudentID).child("currentGroup").getValue().toString();
                            String NewGroup = "Group" + dataSnapshot.child(StudentID).child("expectedGroup").getValue().toString();

                            ref2 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject).child(ClassType).child("Groups").child(CGroup);
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            String member = ds.getValue().toString();
                                            if(member.equals(StudentID)){
                                                ds.getRef().setValue("1");
                                                ref3 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subject).child(ClassType).child("Groups").child(NewGroup);
                                                ref3.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists()){
                                                            for (DataSnapshot ds2 : snapshot.getChildren()) {
                                                                String member = ds2.getValue().toString();
                                                                if(member.equals("1") && Countmembers==0 ){
                                                                    Countmembers = 1;
                                                                    ds2.getRef().setValue(StudentID);
                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                Toast.makeText(Mailbox.this, "Approved", Toast.LENGTH_SHORT).show();
                                                dataSnapshot.child(StudentID).getRef().removeValue();
                                                reload();

                                            }else {
                                                Toast.makeText(Mailbox.this, "Student Not Found", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });





                        }
                        @Override
                        public void onDeclive(int position) {
                            Toast.makeText(Mailbox.this, "Declined", Toast.LENGTH_SHORT).show();
                            String StudentID = maillist.get(position).getStudentID();
                            dataSnapshot.child(StudentID).getRef().removeValue();
                            reload();

                        }
                    });

                    }
                }else {
                    Toast.makeText(Mailbox.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





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

    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }}
