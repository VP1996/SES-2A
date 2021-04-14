package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StudentProfile extends AppCompatActivity {
    Button btnLogout, btnHome, btnEditProfile, btnEditQuiz;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        btnLogout = findViewById(R.id.btn_logout_profile);
        btnHome = findViewById(R.id.btn_home_profile);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnEditQuiz = findViewById(R.id.btn_edit_quiz_answers);

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
