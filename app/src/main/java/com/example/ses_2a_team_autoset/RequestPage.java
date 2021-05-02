package com.example.ses_2a_team_autoset;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RequestPage extends AppCompatActivity {
    EditText etFullName,etStudentID,etClass,etCurrentG,etExpectedG, etReason;
    Button btSubmit,btGoBack;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request);{
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
                        SubmitRequest(
                                etFullName.getText().toString(),
                                etStudentID.getText().toString(),
                                etClass.getText().toString(),
                                etCurrentG.getText().toString(),
                                etExpectedG.getText().toString());
                        etReason.getText().toString());
                    }
                }
            });
        }




    }

