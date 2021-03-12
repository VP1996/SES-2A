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

public class HomeScreenStudent  extends AppCompatActivity {


    Button btSubjectList,btLogOut;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_student);

        //database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        //users = database.getReference("Users");


        btSubjectList = findViewById(R.id.btn_subjectlist);
        btLogOut = findViewById(R.id.btn_logout);

        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenStudent.this, Login.class));
            }
        });

        btSubjectList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenStudent.this, "LOL no....", Toast.LENGTH_SHORT).show();
            }
        });
    }

}