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

public class Register extends AppCompatActivity {

    EditText etFName,etLName,etStudentID,etEmail,etPassword;
    Button btRegister,btGoBack;
    Person newPerson;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newregister);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");

        etFName = findViewById(R.id.et_FirstName);
        etLName = findViewById(R.id.et_LastName);
        etStudentID = findViewById(R.id.et_StudentID);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btRegister = findViewById(R.id.signup_btn);
        btGoBack = findViewById(R.id.goback_btn);

        btGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, HomeScreenStaff.class));
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etFName.getText().toString().trim())) {
                    etFName.setError("First Name is Required.");
                    return;
                }else if(TextUtils.isEmpty(etLName.getText().toString().trim())) {
                    etLName.setError("Last Name is Required.");
                    return;
                }else if(TextUtils.isEmpty(etStudentID.getText().toString().trim())) {
                    etStudentID.setError("Student ID is Required.");
                    return;
                }else if(TextUtils.isEmpty(etEmail.getText().toString().trim())) {
                    etEmail.setError("Email is Required.");
                    return;
                }else if(TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    etPassword.setError("Password is Required.");
                    return;
                }else {
                    RegisterNewStudent(etFName.getText().toString(),
                            etLName.getText().toString(),
                            etStudentID.getText().toString(),
                            etEmail.getText().toString(),
                            etPassword.getText().toString());
                }
            }
        });
    }

    private void RegisterNewStudent(String FName, String LastName, String StudentID, String Email, String Password) {
        newPerson = new Person();
        newPerson.setFirstName(FName);
        newPerson.setLastName(LastName);
        newPerson.setEmail(Email);
        newPerson.setPassword(Password);
        newPerson.setType("Student");
        newPerson.setQuiz("0");


        users.child(StudentID).setValue(newPerson);
        Toast.makeText(Register.this, "New Student Added", Toast.LENGTH_SHORT).show();
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
