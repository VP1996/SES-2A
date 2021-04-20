package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizPageOne extends AppCompatActivity {
    Button btnLogout, btnNext;
    RadioButton btn1,btn2,btn3;
    EditText etFName,etAge,etEmail,etPhone,etAddress, etCultBack ;
    QP1Database newAnswers;
    CurrentUser user;
    String currentuser;

    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page_one);

        btnLogout = findViewById(R.id.btn_logout_quizOne);
        btnNext = findViewById(R.id.btn_next_quizOne);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");



        etFName = findViewById(R.id.input_fullName);
        etAge = findViewById(R.id.input_age);
        etEmail = findViewById(R.id.input_email);
        etPhone = findViewById(R.id.input_phone);
        btn1 = findViewById(R.id.radioBtn_female);
        btn2 =  findViewById(R.id.radioBtn_male);
        btn3 =  findViewById(R.id.radioBtn_other);
        etAddress =findViewById(R.id.input_address);
        etCultBack = findViewById(R.id.input_background);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageOne.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(etFName.getText().toString().trim())) {
                    etFName.setError("First Name is Required.");
                    return;
                }else if(TextUtils.isEmpty(etAge.getText().toString().trim())) {
                    etAge.setError("Last Name is Required.");
                    return;
                }else if(TextUtils.isEmpty(etEmail.getText().toString().trim())) {
                    etEmail.setError("Email is Required.");
                    return;
                }else if(TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                    etPhone.setError("Password is Required.");
                    return;
                }else if(TextUtils.isEmpty(etAddress.getText().toString().trim())) {
                    etAddress.setError("Password is Required.");
                    return;
                }else if(TextUtils.isEmpty(etCultBack.getText().toString().trim())) {
                    etCultBack.setError("Password is Required.");
                    return;
                }else {
                    QuizPage1(etFName.getText().toString(),
                            etAge.getText().toString(),
                            etEmail.getText().toString(),
                            etPhone.getText().toString(),
                            etAddress.getText().toString(),
                            etCultBack.getText().toString());
                }
            }
        });
    }

    private void QuizPage1(String q1, String q2, String q3, String q4, String q5, String q6) {
        newAnswers = new QP1Database();
        newAnswers.setQ1(q1);
        newAnswers.setQ2(q2);
        newAnswers.setQ3(q3);
        newAnswers.setQ4(q4);
        newAnswers.setQ5(q5);
        newAnswers.setQ6(q6);

        String ID = user.getID();

        users.child(ID).child("Quiz").child("Quizpage1").setValue(newAnswers);
        //users.child(ID).child("Quiztaken").setValue("69");
        startActivity(new Intent(QuizPageOne.this, QuizPageTwo.class));

    }



}
