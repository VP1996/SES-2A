package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizPageThree extends AppCompatActivity {
    Button btnLogout, btnNext;
    RadioGroup radioGrpQ1, radioGrpQ3, radioGrpQ4, radioGrpQ5, radioGrpQ6, radioGrpQ7, radioGrpQ8, radioGrpQ9, radioGrpQ10, radioGrpQ11;
    RadioButton radioBtnSelectedQ1, radioBtnSelectedQ3, radioBtnSelectedQ4, radioBtnSelectedQ5, radioBtnSelectedQ6, radioBtnSelectedQ7,
            radioBtnSelectedQ8, radioBtnSelectedQ9, radioBtnSelectedQ10, radioBtnSelectedQ11;
    TextInputLayout tilFriendId;

    QP3Answers QP3Answers;
    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page_three);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");

        btnLogout = findViewById(R.id.btn_logout_quizThree);
        btnNext = findViewById(R.id.btn_next_quizThree);
        radioGrpQ1 = findViewById(R.id.rgQuizThreeQ1);
        radioGrpQ3 = findViewById(R.id.rgQuizThreeQ3);
        radioGrpQ4 = findViewById(R.id.rgQuizThreeQ4);
        radioGrpQ5 = findViewById(R.id.rgQuizThreeQ5);
        radioGrpQ6 = findViewById(R.id.rgQuizThreeQ6);
        radioGrpQ7 = findViewById(R.id.rgQuizThreeQ7);
        radioGrpQ8 = findViewById(R.id.rgQuizThreeQ8);
        radioGrpQ9 = findViewById(R.id.rgQuizThreeQ9);
        radioGrpQ10 = findViewById(R.id.rgQuizThreeQ10);
        radioGrpQ11 = findViewById(R.id.rgQuizThreeQ11);
        tilFriendId = findViewById(R.id.layout_q2);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageThree.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateQuestion(radioGrpQ1, 3) | !validateQuestion(radioGrpQ3, 2) | !validateQuestion(radioGrpQ4, 2) |
                        !validateQuestion(radioGrpQ5, 3) | !validateQuestion(radioGrpQ6, 2) | !validateQuestion(radioGrpQ7, 3) |
                        !validateQuestion(radioGrpQ8, 3) | !validateQuestion(radioGrpQ9, 4) | !validateQuestion(radioGrpQ10, 2) |
                        !validateQuestion(radioGrpQ11, 3))
                    return;

                radioBtnSelectedQ1 = findViewById(radioGrpQ1.getCheckedRadioButtonId());
                radioBtnSelectedQ3 = findViewById(radioGrpQ3.getCheckedRadioButtonId());
                radioBtnSelectedQ4 = findViewById(radioGrpQ4.getCheckedRadioButtonId());
                radioBtnSelectedQ5 = findViewById(radioGrpQ5.getCheckedRadioButtonId());
                radioBtnSelectedQ6 = findViewById(radioGrpQ6.getCheckedRadioButtonId());
                radioBtnSelectedQ7 = findViewById(radioGrpQ7.getCheckedRadioButtonId());
                radioBtnSelectedQ8 = findViewById(radioGrpQ8.getCheckedRadioButtonId());
                radioBtnSelectedQ9 = findViewById(radioGrpQ9.getCheckedRadioButtonId());
                radioBtnSelectedQ10 = findViewById(radioGrpQ10.getCheckedRadioButtonId());
                radioBtnSelectedQ11 = findViewById(radioGrpQ11.getCheckedRadioButtonId());

                saveQuizPage3Answers(String.valueOf(radioGrpQ1.indexOfChild(radioBtnSelectedQ1)),
                        tilFriendId.getEditText().getText().toString(),
                        String.valueOf(radioGrpQ3.indexOfChild(radioBtnSelectedQ3)),
                        String.valueOf(radioGrpQ4.indexOfChild(radioBtnSelectedQ4)),
                        String.valueOf(radioGrpQ5.indexOfChild(radioBtnSelectedQ5)),
                        String.valueOf(radioGrpQ6.indexOfChild(radioBtnSelectedQ6)),
                        String.valueOf(radioGrpQ7.indexOfChild(radioBtnSelectedQ7)),
                        String.valueOf(radioGrpQ8.indexOfChild(radioBtnSelectedQ8)),
                        String.valueOf(radioGrpQ9.indexOfChild(radioBtnSelectedQ9)),
                        String.valueOf(radioGrpQ10.indexOfChild(radioBtnSelectedQ10)),
                        String.valueOf(radioGrpQ11.indexOfChild(radioBtnSelectedQ11)));
            }
        });
    }

    private void saveQuizPage3Answers(String mcq1, String friendId, String mcq3, String mcq4, String mcq5, String mcq6, String mcq7, String mcq8, String mcq9, String mcq10, String mcq11) {
        QP3Answers = new QP3Answers();
        QP3Answers.setMcq1(mcq1);
        QP3Answers.setFriendId(friendId);
        QP3Answers.setMcq3(mcq3);
        QP3Answers.setMcq4(mcq4);
        QP3Answers.setMcq5(mcq5);
        QP3Answers.setMcq6(mcq6);
        QP3Answers.setMcq7(mcq7);
        QP3Answers.setMcq8(mcq8);
        QP3Answers.setMcq9(mcq9);
        QP3Answers.setMcq10(mcq10);
        QP3Answers.setMcq11(mcq11);

        String ID = user.getID();
        users.child(ID).child("Quiz").child("QuizPage3").setValue(QP3Answers);
        startActivity(new Intent(QuizPageThree.this, QuizPageFour.class));
    }

    private boolean validateQuestion(RadioGroup radioGrp, int index) {
        int isSelected = radioGrp.getCheckedRadioButtonId();
        RadioButton lastRadioBtn = (RadioButton) radioGrp.getChildAt(index);
        if (isSelected <= 0) {
            lastRadioBtn.setError("You must select an answer");
            return false;
        } else {
            lastRadioBtn.setError(null);
            return true;
        }
    }
}
