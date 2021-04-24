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
    RadioButton radioBtnSelectedGender, radioBtnOther;
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
        textInputEmail = findViewById(R.id.layout_email);
        textInputPhone = findViewById(R.id.layout_phone);
        textInputAddress = findViewById(R.id.layout_address);
        textInputCulturalBack = findViewById(R.id.layout_background);
        radGrpGender = findViewById(R.id.radioGroupGender);
        radioBtnOther = findViewById(R.id.radioBtn_other);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageOne.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTextInput(textInputFullName) | !validateEmail() | !validateGender() | !validateTextInput(textInputAge) |
                        !validateTextInput(textInputPhone) | !validateTextInput(textInputAddress) | !validateTextInput(textInputCulturalBack))
                    return;

                radioBtnSelectedGender = findViewById(radGrpGender.getCheckedRadioButtonId());

                saveQuizPage1Answers(textInputFullName.getEditText().getText().toString(),
                        textInputEmail.getEditText().getText().toString(),
                        radioBtnSelectedGender.getText().toString(),
                        textInputAge.getEditText().getText().toString(),
                        textInputPhone.getEditText().getText().toString(),
                        textInputAddress.getEditText().getText().toString(),
                        textInputCulturalBack.getEditText().getText().toString());
            }
        });
    }

    private void saveQuizPage1Answers(String fullName, String email, String gender, String age, String phoneNumber, String address, String cultureBackground) {
        QP1Answers = new QP1Answers();
        QP1Answers.setFullName(fullName);
        QP1Answers.setEmail(email);
        QP1Answers.setGender(gender);
        QP1Answers.setAge(age);
        QP1Answers.setPhoneNumber(phoneNumber);
        QP1Answers.setAddress(address);
        QP1Answers.setCulturalBackground(cultureBackground);

        String ID = user.getID();
        users.child(ID).child("Quiz").child("QuizPage1").setValue(QP1Answers);
        startActivity(new Intent(QuizPageOne.this, QuizPageTwo.class));
    }

    private boolean validateTextInput(TextInputLayout textInput) {
        String input = textInput.getEditText().getText().toString().trim();
        String inputName = (String) textInput.getHint();
        if (input.isEmpty()) {
            textInput.setError(inputName + " is required");
            return false;
        } else {
            textInput.setError(null);
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
}
