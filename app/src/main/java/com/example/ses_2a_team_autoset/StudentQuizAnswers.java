package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StudentQuizAnswers extends AppCompatActivity {
    Button btnLogout, btnHome, btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_quiz_answers);

        btnLogout = findViewById(R.id.btn_logout_quiz_answers);
        btnHome = findViewById(R.id.btn_home_quiz_answers);
        btnSave = findViewById(R.id.btn_save_quiz_answers);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentQuizAnswers.this, Login.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentQuizAnswers.this, HomeScreenStudent.class));
            }
        });
    }
}
