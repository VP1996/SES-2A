package com.example.ses_2a_team_autoset;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuizPageTwo extends AppCompatActivity {
    Button btnLogout, btnNext;
    TextView tvSubjects;
    boolean[] selectedSubjects;
    TextInputLayout tilFaculties, tilAvailabilities, tilDegree, tilGpa;
    RadioButton radioBtnSelectedLevel, radioBtnPhd;
    RadioGroup radioGrpStudyLevel;
    LinearLayout mLayout;
    AutoCompleteTextView actvFaculties;

    QP2Answers QP2Answers;
    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference subjects;

    ArrayList<Integer> subjectsIndexList = new ArrayList<>();
    ArrayList<String> subjectsList = new ArrayList<>();
    ArrayList<String> selectedSubjectsList = new ArrayList<>();
    String[] subjectsArray;

    String[] facultiesArray = {
            "Business",
            "Communication",
            "Creative Intelligence and Innovation",
            "Design Architecture and Building",
            "Education",
            "Engineering",
            "Health",
            "Information Technology",
            "International Studies",
            "Law",
            "Science and Mathematics"
    };
    ArrayList<String> facultiesList = new ArrayList<String>(Arrays.asList(facultiesArray));
    ArrayAdapter<String> facultiesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page_two);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");
        subjects = database.getReference("Subjects");

        btnLogout = findViewById(R.id.btn_logout_quizTwo);
        btnNext = findViewById(R.id.btn_next_quizTwo);
        tvSubjects = findViewById(R.id.tv_subjects);
        tilAvailabilities = findViewById(R.id.layout_availabilities);
        tilFaculties = findViewById(R.id.menu_faculties);
        tilDegree = findViewById(R.id.layout_degree);
        tilGpa = findViewById(R.id.layout_gpa);
        radioBtnPhd = findViewById(R.id.radioBtn_phd);
        radioGrpStudyLevel = findViewById(R.id.radioGroupQuizTwo);
        actvFaculties = findViewById(R.id.actv_faculties);
        mLayout = findViewById(R.id.llSelectClasses);

        subjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    subjectsList.add(dataSnapshot.getKey().toString());
                }
                selectedSubjects = new boolean[subjectsList.size()];
                subjectsArray = subjectsList.toArray(new String[subjectsList.size()]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizPageTwo.this, "No subjects found", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageTwo.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateSubjects() | !validateTextInput(tilAvailabilities) | !validateTextInput(tilFaculties) |
                        !validateTextInput(tilDegree) | !validateStudyLevel() | !validateTextInput(tilGpa))
                    return;

                radioBtnSelectedLevel = findViewById(radioGrpStudyLevel.getCheckedRadioButtonId());

                saveQuizPage2Answers(selectedSubjectsList,
                        tilAvailabilities.getEditText().getText().toString(),
                        actvFaculties.getText().toString(),
                        tilDegree.getEditText().getText().toString(),
                        radioBtnSelectedLevel.getText().toString(),
                        tilGpa.getEditText().getText().toString());
            }
        });

        tvSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizPageTwo.this);
                builder.setTitle("Select Subjects");
                builder.setCancelable(true);
                builder.setMultiChoiceItems(subjectsArray, selectedSubjects, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            subjectsIndexList.add(which);
                            Collections.sort(subjectsIndexList);
                        } else {
                            subjectsIndexList.remove(which);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLayout.removeAllViews();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < subjectsIndexList.size(); i++) {
                            selectedSubjectsList.add(subjectsArray[subjectsIndexList.get(i)]);
                            //Concat array value
                            stringBuilder.append(subjectsArray[subjectsIndexList.get(i)]);
                            if (i != subjectsIndexList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        tvSubjects.setText(stringBuilder.toString());

                        for (int i = 0; i < subjectsIndexList.size(); i++) {
                            mLayout.addView(createNewTextViewSelectClasses(subjectsArray[subjectsIndexList.get(i)]));
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLayout.removeAllViews();
                        for (int i = 0; i < selectedSubjects.length; i++) {
                            //Remove all selections
                            selectedSubjects[i] = false;
                            subjectsIndexList.clear();
                            tvSubjects.setText("");
                        }
                    }
                });
                //Show dialog
                builder.show();
            }
        });

        facultiesAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_item, facultiesList);
        actvFaculties.setAdapter(facultiesAdapter);
        actvFaculties.setThreshold(1);
    }

    private TextView createNewTextViewSelectClasses(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.bottomMargin = 20;
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setBackgroundResource(android.R.drawable.editbox_background);
        textView.getBackground().setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
        textView.setPadding(60, 60, 60, 60);
        textView.setTextSize(16);
        textView.setText(text);
        return textView;
    }

    private void saveQuizPage2Answers(ArrayList<String> subjects, String availabilities, String faculty, String degree, String studyLevel, String gpa) {
        QP2Answers = new QP2Answers();
        QP2Answers.setSubjects(subjects);
        QP2Answers.setAvailabilities(availabilities);
        QP2Answers.setFaculty(faculty);
        QP2Answers.setDegree(degree);
        QP2Answers.setStudyLevel(studyLevel);
        QP2Answers.setGPA(gpa);

        String ID = user.getID();
        users.child(ID).child("Quiz").child("QuizPage2").setValue(QP2Answers);
        startActivity(new Intent(QuizPageTwo.this, QuizPageThree.class));
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

    private boolean validateSubjects() {
        if (selectedSubjectsList.isEmpty()) {
            tvSubjects.setError("Subjects is required");
            return false;
        } else {
            tvSubjects.setError(null);
            return true;
        }
    }

    private boolean validateStudyLevel() {
        int isSelected = radioGrpStudyLevel.getCheckedRadioButtonId();
        if (isSelected <= 0) {
            radioBtnPhd.setError("Study level is required");
            return false;
        } else {
            radioBtnPhd.setError(null);
            return true;
        }
    }
}
