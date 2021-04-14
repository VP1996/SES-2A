package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StudentEditProfile extends AppCompatActivity {
    Button btnLogout, btnHome, btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_edit_profile);

        btnLogout = findViewById(R.id.btn_logout_edit_profile);
        btnHome = findViewById(R.id.btn_home_edit_profile);
        btnSave = findViewById(R.id.btn_save_edit_profile);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentEditProfile.this, Login.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentEditProfile.this, HomeScreenStudent.class));
            }
        });
    }
}
