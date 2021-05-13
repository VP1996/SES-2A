package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentQuizAnswers extends AppCompatActivity {
    Button btnLogout, btnHome, btnSave;
    RadioGroup radioGrpQ1, radioGrpQ3, radioGrpQ4, radioGrpQ5, radioGrpQ6,
            radioGrpQ7, radioGrpQ8, radioGrpQ9, radioGrpQ10, radioGrpQ11,
            radioGrpScenarioQ1, radioGrpScenarioQ2, radioGrpScenarioQ3, radioGrpScenarioQ4;
    RadioButton radioBtnSelectedQ1, radioBtnSelectedQ3, radioBtnSelectedQ4, radioBtnSelectedQ5, radioBtnSelectedQ6, radioBtnSelectedQ7,
            radioBtnSelectedQ8, radioBtnSelectedQ9, radioBtnSelectedQ10, radioBtnSelectedQ11,
            radioBtnScenarioSelectedQ1, radioBtnScenarioSelectedQ2, radioBtnScenarioSelectedQ3, radioBtnScenarioSelectedQ4;
    TextInputLayout tilFriendId;

    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference users;
    QP3Answers QP3Answers;
    QP4Answers QP4Answers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_quiz_answers);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = FirebaseDatabase.getInstance().getReference("Users");
        String ID = user.getID();

        btnLogout = findViewById(R.id.btn_logout_quiz_answers);
        btnHome = findViewById(R.id.btn_home_quiz_answers);
        btnSave = findViewById(R.id.btn_save_quiz_answers);
        radioGrpQ1 = findViewById(R.id.rgQuizThreeQ1_edit);
        radioGrpQ3 = findViewById(R.id.rgQuizThreeQ3_edit);
        radioGrpQ4 = findViewById(R.id.rgQuizThreeQ4_edit);
        radioGrpQ5 = findViewById(R.id.rgQuizThreeQ5_edit);
        radioGrpQ6 = findViewById(R.id.rgQuizThreeQ6_edit);
        radioGrpQ7 = findViewById(R.id.rgQuizThreeQ7_edit);
        radioGrpQ8 = findViewById(R.id.rgQuizThreeQ8_edit);
        radioGrpQ9 = findViewById(R.id.rgQuizThreeQ9_edit);
        radioGrpQ10 = findViewById(R.id.rgQuizThreeQ10_edit);
        radioGrpQ11 = findViewById(R.id.rgQuizThreeQ11_edit);
        radioGrpScenarioQ1 = findViewById(R.id.rgQuizFourQ1_edit);
        radioGrpScenarioQ2 = findViewById(R.id.rgQuizFourQ2_edit);
        radioGrpScenarioQ3 = findViewById(R.id.rgQuizFourQ3_edit);
        radioGrpScenarioQ4 = findViewById(R.id.rgQuizFourQ4_edit);
        tilFriendId = findViewById(R.id.layout_q2_edit);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(ID).child("Quiz").exists()) {
                    ((RadioButton) radioGrpQ1.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq1").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ3.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq3").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ4.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq4").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ5.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq5").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ6.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq6").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ7.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq7").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ8.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq8").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ9.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq9").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ10.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq10").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpQ11.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage3")
                            .child("mcq11").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpScenarioQ1.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage4")
                            .child("mcq1").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpScenarioQ2.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage4")
                            .child("mcq2").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpScenarioQ3.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage4")
                            .child("mcq3").getValue().toString()))).setChecked(true);
                    ((RadioButton) radioGrpScenarioQ4.getChildAt(Integer.parseInt(snapshot.child(ID).child("Quiz").child("QuizPage4")
                            .child("mcq4").getValue().toString()))).setChecked(true);

                    String friendId = snapshot.child(ID).child("Quiz").child("QuizPage3").child("friendId").getValue().toString();
                    if (!friendId.equals("")) {
                        tilFriendId.getEditText().setText(friendId);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentQuizAnswers.this, "No student found", Toast.LENGTH_SHORT).show();
            }
        });

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                radioBtnScenarioSelectedQ1 = findViewById(radioGrpScenarioQ1.getCheckedRadioButtonId());
                radioBtnScenarioSelectedQ2 = findViewById(radioGrpScenarioQ2.getCheckedRadioButtonId());
                radioBtnScenarioSelectedQ3 = findViewById(radioGrpScenarioQ3.getCheckedRadioButtonId());
                radioBtnScenarioSelectedQ4 = findViewById(radioGrpScenarioQ4.getCheckedRadioButtonId());

                saveQuizAnswers(String.valueOf(radioGrpQ1.indexOfChild(radioBtnSelectedQ1)),
                        tilFriendId.getEditText().getText().toString(),
                        String.valueOf(radioGrpQ3.indexOfChild(radioBtnSelectedQ3)),
                        String.valueOf(radioGrpQ4.indexOfChild(radioBtnSelectedQ4)),
                        String.valueOf(radioGrpQ5.indexOfChild(radioBtnSelectedQ5)),
                        String.valueOf(radioGrpQ6.indexOfChild(radioBtnSelectedQ6)),
                        String.valueOf(radioGrpQ7.indexOfChild(radioBtnSelectedQ7)),
                        String.valueOf(radioGrpQ8.indexOfChild(radioBtnSelectedQ8)),
                        String.valueOf(radioGrpQ9.indexOfChild(radioBtnSelectedQ9)),
                        String.valueOf(radioGrpQ10.indexOfChild(radioBtnSelectedQ10)),
                        String.valueOf(radioGrpQ11.indexOfChild(radioBtnSelectedQ11)),
                        String.valueOf(radioGrpScenarioQ1.indexOfChild(radioBtnScenarioSelectedQ1)),
                        String.valueOf(radioGrpScenarioQ2.indexOfChild(radioBtnScenarioSelectedQ2)),
                        String.valueOf(radioGrpScenarioQ3.indexOfChild(radioBtnScenarioSelectedQ3)),
                        String.valueOf(radioGrpScenarioQ4.indexOfChild(radioBtnScenarioSelectedQ4)));

                Toast.makeText(StudentQuizAnswers.this, "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveQuizAnswers(String mcq1, String friendId, String mcq3, String mcq4, String mcq5,
                                 String mcq6, String mcq7, String mcq8, String mcq9, String mcq10,
                                 String mcq11, String mcqScenario1, String mcqScenario2, String mcqScenario3, String mcqScenario4) {
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

        QP4Answers = new QP4Answers();
        QP4Answers.setMcq1(mcqScenario1);
        QP4Answers.setMcq2(mcqScenario2);
        QP4Answers.setMcq3(mcqScenario3);
        QP4Answers.setMcq4(mcqScenario4);

        String ID = user.getID();
        users.child(ID).child("Quiz").child("QuizPage3").setValue(QP3Answers);
        users.child(ID).child("Quiz").child("QuizPage4").setValue(QP4Answers);
        startActivity(new Intent(StudentQuizAnswers.this, StudentProfile.class));
    }
}
