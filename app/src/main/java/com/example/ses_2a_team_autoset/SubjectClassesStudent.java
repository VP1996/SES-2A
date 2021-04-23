package com.example.ses_2a_team_autoset;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectClassesStudent extends AppCompatActivity {

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

    public TextView ClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_student);

        String ID = user.getID();

        reff1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ID).child("Subjects");

        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");

        ClassName = findViewById(R.id.SubjectName);

        ClassName.setText(subject);
    }
}
