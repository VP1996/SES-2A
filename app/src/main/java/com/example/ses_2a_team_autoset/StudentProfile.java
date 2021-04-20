package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {
    Button btnLogout, btnHome, btnEditProfile, btnEditQuiz;

    TextView tvFName,tvSEmail;

    String currentuser,temp;

    CurrentUser user;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");

        String ID = user.getID();

        btnLogout = findViewById(R.id.btn_logout_profile);
        btnHome = findViewById(R.id.btn_home_profile);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnEditQuiz = findViewById(R.id.btn_edit_quiz_answers);
        tvFName = findViewById(R.id.tv_studentName);
        tvSEmail = findViewById(R.id.tv_studentEmail);


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(ID).child("Quiz").exists()){
                    temp = snapshot.child(ID).child("Quiz").child("Quizpage1").child("q1").getValue().toString();
                    tvFName.setText(temp);
                    temp = snapshot.child(ID).child("Quiz").child("Quizpage1").child("q2").getValue().toString();
                    tvSEmail.setText(temp);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, Login.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, HomeScreenStudent.class));
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, StudentEditProfile.class));
            }
        });

        btnEditQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, StudentQuizAnswers.class));
            }
        });
    }
}
