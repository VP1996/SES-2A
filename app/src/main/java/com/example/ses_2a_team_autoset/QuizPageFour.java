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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizPageFour extends AppCompatActivity {
    Button btnLogout, btnNext;
    RadioGroup radioGrpQ1, radioGrpQ2, radioGrpQ3, radioGrpQ4;
    RadioButton radioBtnSelectedQ1, radioBtnSelectedQ2, radioBtnSelectedQ3, radioBtnSelectedQ4;

    QP4Answers QP4Answers;
    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference users, subjectsInstanceRef;

    ArrayList<String> studentSubjectsList = new ArrayList<>();
    ArrayList<String> studentClassesList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page_four);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");
        subjectsInstanceRef = FirebaseDatabase.getInstance().getReference().child("Subjects");

        String ID = user.getID();

        btnLogout = findViewById(R.id.btn_logout_quizFour);
        btnNext = findViewById(R.id.btn_next_quizFour);
        radioGrpQ1 = findViewById(R.id.rgQuizFourQ1);
        radioGrpQ2 = findViewById(R.id.rgQuizFourQ2);
        radioGrpQ3 = findViewById(R.id.rgQuizFourQ3);
        radioGrpQ4 = findViewById(R.id.rgQuizFourQ4);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.child(ID).child("Quiz").child("QuizPage2").child("subjects").getChildren()) {
                        if (dataSnapshot.child("subjectName").exists() && dataSnapshot.child("class").exists()) {
                            studentSubjectsList.add(dataSnapshot.child("subjectName").getValue().toString());
                            studentClassesList.add(dataSnapshot.child("class").getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizPageFour.this, "No student found", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageFour.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateQuestion(radioGrpQ1) | !validateQuestion(radioGrpQ2) | !validateQuestion(radioGrpQ3) | !validateQuestion(radioGrpQ4))
                    return;

                radioBtnSelectedQ1 = findViewById(radioGrpQ1.getCheckedRadioButtonId());
                radioBtnSelectedQ2 = findViewById(radioGrpQ2.getCheckedRadioButtonId());
                radioBtnSelectedQ3 = findViewById(radioGrpQ3.getCheckedRadioButtonId());
                radioBtnSelectedQ4 = findViewById(radioGrpQ4.getCheckedRadioButtonId());

                saveQuizPage4Answers(String.valueOf(radioGrpQ1.indexOfChild(radioBtnSelectedQ1)),
                        String.valueOf(radioGrpQ2.indexOfChild(radioBtnSelectedQ2)),
                        String.valueOf(radioGrpQ3.indexOfChild(radioBtnSelectedQ3)),
                        String.valueOf(radioGrpQ4.indexOfChild(radioBtnSelectedQ4)));
            }
        });
    }

    private void saveQuizPage4Answers(String mcq1, String mcq2, String mcq3, String mcq4) {
        QP4Answers = new QP4Answers();
        QP4Answers.setMcq1(mcq1);
        QP4Answers.setMcq2(mcq2);
        QP4Answers.setMcq3(mcq3);
        QP4Answers.setMcq4(mcq4);

        String ID = user.getID();
        users.child(ID).child("Quiz").child("QuizPage4").setValue(QP4Answers);
        users.child(ID).child("quizTaken").setValue("1");

        for (int i = 0; i < studentSubjectsList.size(); i++) {
            String subjectName = studentSubjectsList.get(i);
            String className = studentClassesList.get(i);

            subjectsInstanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Get the group size set for the particular class of this subject
                        int groupSize = Integer.parseInt(snapshot.child(subjectName).child(className).child("GroupSizes").getValue().toString());

                        // Get the number of groups in a particular class
                        int numberOfGroups = (int) snapshot.child(subjectName).child(className).child("Groups").getChildrenCount();

                        loop:
                        for (int j = 1; j <= numberOfGroups; j++) {
                            String groupNumber = "Group" + j;
                            for (int k = 1; k <= groupSize; k++) {
                                String groupMember = snapshot.child(subjectName).child(className).child("Groups").child(groupNumber).child(String.valueOf(k)).getValue().toString();
                                if (groupMember.equals("1")) {
                                    snapshot.child(subjectName).child(className).child("Groups").child(groupNumber).child(String.valueOf(k)).getRef().setValue(ID);
                                    break loop;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(QuizPageFour.this, "No subjects found", Toast.LENGTH_SHORT).show();
                }
            });
        }

        startActivity(new Intent(QuizPageFour.this, HomeScreenStudent.class));
    }

    private boolean validateQuestion(RadioGroup radioGrp) {
        int isSelected = radioGrp.getCheckedRadioButtonId();
        RadioButton lastRadioBtn = (RadioButton) radioGrp.getChildAt(2);
        if (isSelected <= 0) {
            lastRadioBtn.setError("You must select an answer");
            return false;
        } else {
            lastRadioBtn.setError(null);
            return true;
        }
    }
}
