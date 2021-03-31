package com.example.ses_2a_team_autoset;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubjectClassesAdmin extends AppCompatActivity {
    public TextView ClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_admin);

        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");

        ClassName = findViewById(R.id.SubjectName);

        ClassName.setText(subject);
    }
}
