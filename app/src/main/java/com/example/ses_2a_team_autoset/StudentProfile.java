package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {
    Button btnLogout, btnHome, btnEditProfile, btnEditQuiz;
    TextView tvName;
    TextInputLayout tilEmail, tilAge, tilPhone, tilAddress, tilCulturalBack, tilFaculty, tilDegree, tilGpa,
            tilStudyLevel, tilSubjects, tilAvailabilities;

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
        tvName = findViewById(R.id.tv_studentName);
        tilEmail = findViewById(R.id.layout_profileEmail);
        tilAge = findViewById(R.id.layout_profileAge);
        tilPhone = findViewById(R.id.layout_profilePhone);
        tilAddress = findViewById(R.id.layout_profileAddress);
        tilCulturalBack = findViewById(R.id.layout_profileCulturalBackground);
        tilFaculty = findViewById(R.id.layout_profileFaculty);
        tilDegree = findViewById(R.id.layout_profileDegree);
        tilGpa = findViewById(R.id.layout_profileGpa);
        tilStudyLevel = findViewById(R.id.layout_profileStudentLevel);
        tilSubjects = findViewById(R.id.layout_profileSubjects);
        tilAvailabilities = findViewById(R.id.layout_profileAvailabilities);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(ID).child("Quiz").exists()) {
                    tvName.setText(snapshot.child(ID).child("Quiz").child("QuizPage1").child("fullName").getValue().toString());
                    tilEmail.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage1").child("email").getValue().toString());
                    tilAge.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage1").child("age").getValue().toString());
                    tilPhone.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage1").child("phoneNumber").getValue().toString());
                    tilAddress.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage1").child("address").getValue().toString());
                    tilCulturalBack.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage1").child("culturalBackground").getValue().toString());
                    tilFaculty.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage2").child("faculty").getValue().toString());
                    tilDegree.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage2").child("degree").getValue().toString());
                    tilGpa.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage2").child("gpa").getValue().toString());
                    tilStudyLevel.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage2").child("studyLevel").getValue().toString());
                    tilAvailabilities.getEditText().setText(snapshot.child(ID).child("Quiz").child("QuizPage2").child("availabilities").getValue().toString());

                    String subjects = "";
                    for (DataSnapshot dataSnapshot : snapshot.child(ID).child("Quiz").child("QuizPage2").child("subjects").getChildren()) {
                        String subjectName = dataSnapshot.child("subjectName").getValue().toString();
                        String subjectClass = dataSnapshot.child("class").getValue().toString();
                        if (subjects == "")
                            subjects = subjectName + "-" + subjectClass;
                        else
                            subjects = subjects + ", " + subjectName + "-" + subjectClass;
                    }
                    tilSubjects.getEditText().setText(subjects);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfile.this, "No student found", Toast.LENGTH_SHORT).show();
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
