package com.example.ses_2a_team_autoset;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeScreenStaff extends AppCompatActivity {

    Button btSubjectList,btLogOut,btRegisterNewStudent,btRequests;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_staff);


        btSubjectList = findViewById(R.id.btn_subjectlist);
        btLogOut = findViewById(R.id.btn_logout);
        btRequests = findViewById(R.id.btn_replytoreq);
        btRegisterNewStudent = findViewById(R.id.btn_regnew);

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
