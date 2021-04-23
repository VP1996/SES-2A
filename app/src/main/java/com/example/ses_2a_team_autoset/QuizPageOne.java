package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizPageOne extends AppCompatActivity {
    Button btnLogout, btnNext;
    RadioButton radioBtnFemale, radioBtnMale, radioBtnOther;
    TextInputLayout textInputFullName, textInputEmail, textInputAge, textInputPhone, textInputAddress, textInputCulturalBack;
    RadioGroup radGrpGender;
    QP1Answers QP1Answers;
    CurrentUser user;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page_one);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");

        btnLogout = findViewById(R.id.btn_logout_quizOne);
        btnNext = findViewById(R.id.btn_next_quizOne);
        textInputFullName = findViewById(R.id.layout_fullName);
        textInputAge = findViewById(R.id.layout_age);
        radioBtnFemale = findViewById(R.id.radioBtn_female);
        radioBtnMale = findViewById(R.id.radioBtn_male);
        radioBtnOther = findViewById(R.id.radioBtn_other);
        textInputEmail = findViewById(R.id.layout_email);
        textInputPhone = findViewById(R.id.layout_phone);
        textInputAddress = findViewById(R.id.layout_address);
        textInputCulturalBack = findViewById(R.id.layout_background);
        radGrpGender = findViewById(R.id.radioGroupGender);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageOne.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName() | !validateEmail() | !validateGender() | !validateAge() |
                        !validatePhoneNumber() | !validateAddress() | !validateCulturalBackground())
                    return;

                String gender = "";
                if (radioBtnFemale.isChecked())
                    gender = "Female";
                if (radioBtnMale.isChecked())
                    gender = "Male";
                if (radioBtnOther.isChecked())
                    gender = "Other";

                QuizPage1(textInputFullName.getEditText().getText().toString(),
                        textInputEmail.getEditText().getText().toString(),
                        gender,
                        textInputAge.getEditText().getText().toString(),
                        textInputPhone.getEditText().getText().toString(),
                        textInputAddress.getEditText().getText().toString(),
                        textInputCulturalBack.getEditText().getText().toString());
            }
        });
    }

    private void QuizPage1(String fullName, String email, String gender, String age, String phoneNumber, String address, String cultureBackground) {
        QP1Answers = new QP1Answers();
        QP1Answers.setFullName(fullName);
        QP1Answers.setEmail(email);
        QP1Answers.setGender(gender);
        QP1Answers.setAge(age);
        QP1Answers.setPhoneNumber(phoneNumber);
        QP1Answers.setAddress(address);
        QP1Answers.setCulturalBackground(cultureBackground);

        String ID = user.getID();
        users.child(ID).child("Quiz").child("Quizpage1").setValue(QP1Answers);
        startActivity(new Intent(QuizPageOne.this, QuizPageTwo.class));
    }

    private boolean validateFullName() {
        String fullNameInput = textInputFullName.getEditText().getText().toString().trim();
        if (fullNameInput.isEmpty()) {
            textInputFullName.setError("Full Name is required");
            return false;
        } else {
            textInputFullName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateGender() {
        int isSelected = radGrpGender.getCheckedRadioButtonId();
        if (isSelected <= 0) {
            radioBtnOther.setError("Gender is required");
            return false;
        } else {
            radioBtnOther.setError(null);
            return true;
        }

    }

    private boolean validateAge() {
        String AgeInput = textInputAge.getEditText().getText().toString().trim();
        if (AgeInput.isEmpty()) {
            textInputAge.setError("Age is required");
            return false;
        } else {
            textInputAge.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumberInput = textInputPhone.getEditText().getText().toString().trim();
        if (phoneNumberInput.isEmpty()) {
            textInputPhone.setError("Phone Number is required");
            return false;
        } else {
            textInputPhone.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        String addressInput = textInputAddress.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()) {
            textInputAddress.setError("Address is required");
            return false;
        } else {
            textInputAddress.setError(null);
            return true;
        }
    }

    private boolean validateCulturalBackground() {
        String culturalBackInput = textInputCulturalBack.getEditText().getText().toString().trim();
        if (culturalBackInput.isEmpty()) {
            textInputCulturalBack.setError("Cultural Background is required");
            return false;
        } else {
            textInputCulturalBack.setError(null);
            return true;
        }
    }
}
