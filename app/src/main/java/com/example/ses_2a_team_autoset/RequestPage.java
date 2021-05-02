package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestPage extends AppCompatActivity {
    EditText etFullName,etStudentID,etClass,etCurrentG,etExpectedG, etReason;
    Button btSubmit,btGoBack;
    NewRequestMail newEmail;
    FirebaseDatabase database;
    DatabaseReference users;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");

        //String subject = bundle.getString("subject");
        String subject = "jeff";
        etFullName = findViewById(R.id.et_FullName);
        etStudentID = findViewById(R.id.et_StudentID);
        etClass = findViewById(R.id.et_Class);
        etCurrentG = findViewById(R.id.et_CGroupNumberl);
        etExpectedG = findViewById(R.id.et_EGroupNumber);
        etReason = findViewById(R.id.et_Reason);
        btSubmit = findViewById(R.id.submit_btn);
        btGoBack = findViewById(R.id.go_btn);



        btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(TextUtils.isEmpty(etFullName.getText().toString().trim())) {
                        etFullName.setError("Full Name is Required.");
                        return;
                    }else if(TextUtils.isEmpty(etStudentID.getText().toString().trim())) {
                        etStudentID.setError("Student ID is Required.");
                        return;
                    }else if(TextUtils.isEmpty(etClass.getText().toString().trim())) {
                        etClass.setError("Class Name is Required.");
                        return;
                    }else if(TextUtils.isEmpty(etCurrentG.getText().toString().trim())) {
                        etCurrentG.setError("Current Group Number is Required.");
                        return;
                    }else if(TextUtils.isEmpty(etExpectedG.getText().toString().trim())) {
                        etExpectedG.setError("Expected Group Number is Required.");
                        return;
                    }else if(TextUtils.isEmpty(etReason.getText().toString().trim())) {
                        etReason.setError("Reason for Change is Required.");
                        return;
                    }else {
                        NewEmail(
                                etFullName.getText().toString(),
                                etStudentID.getText().toString(),
                                etClass.getText().toString(),
                                etCurrentG.getText().toString(),
                                etExpectedG.getText().toString(),
                                etReason.getText().toString(),
                                subject);
                    }
                }
            });

        btGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RequestPage.this, SubjectClassesAdmin.class));
            }
        });
    }

    private void NewEmail(String FullName, String ClassType, String StudentID, String CurrentGroup, String ExpectedGroup,String Reason, String subject) {
        newEmail = new NewRequestMail();
        newEmail.setFullName(FullName);
        newEmail.setClassType(ClassType);
        newEmail.setSubject(subject);
        newEmail.setStudentID(StudentID);
        newEmail.setCurrentGroup(CurrentGroup);
        newEmail.setExpectedGroup(ExpectedGroup);
        newEmail.setReason(Reason);

        users.child(StudentID).setValue(newEmail);
        Toast.makeText(RequestPage.this, "Request Sent", Toast.LENGTH_SHORT).show();
        reload();
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}

