package com.example.ses_2a_team_autoset;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuizPageTwo extends AppCompatActivity {
    Button btnLogout, btnNext;
    TextView tvSubjects;
    boolean[] selectedSubjects;
    TextInputLayout tilFaculties;
    LinearLayout mLayout;
    AutoCompleteTextView actvFaculties;

    ArrayList<Integer> subjectsList = new ArrayList<>();
    //TODO: Replace hardcoded list with subjects list from Firebase
    String[] subjectsArray = {"SES1A", "SES1B", "SES2A", "SES2B", "SES3A", "SES3B"};

    //TODO: Add all faculties to the array
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

        btnLogout = findViewById(R.id.btn_logout_quizTwo);
        btnNext = findViewById(R.id.btn_next_quizTwo);
        tvSubjects = findViewById(R.id.tv_subjects);
        selectedSubjects = new boolean[subjectsArray.length];
        tilFaculties = findViewById(R.id.menu_faculties);
        actvFaculties = findViewById(R.id.actv_faculties);
        mLayout = findViewById(R.id.llSelectClasses);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageTwo.this, Login.class));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizPageTwo.this, QuizPageThree.class));
            }
        });

        tvSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizPageTwo.this);
                builder.setTitle("Select Subjects");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(subjectsArray, selectedSubjects, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            subjectsList.add(which);
                            Collections.sort(subjectsList);
                        }
                        else {
                            subjectsList.remove(which);
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < subjectsList.size(); i++) {
                            //Concat array value
                            stringBuilder.append(subjectsArray[subjectsList.get(i)]);
                            if (i != subjectsList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        tvSubjects.setText(stringBuilder.toString());

                        //TODO: Create TextView dialogue
                        for (int i = 0; i < subjectsList.size(); i++) {
                            mLayout.addView(createNewTextViewSelectClasses(subjectsArray[subjectsList.get(i)]));
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
                        for (int i = 0; i < selectedSubjects.length; i++) {
                            //Remove all selections
                            selectedSubjects[i] = false;
                            subjectsList.clear();
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
        //TODO: Convert the margin to dp (from pixel to dp)
        lparams.bottomMargin = 16;
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setBackgroundResource(R.drawable.select_box_background);
        textView.setBackgroundColor(Color.parseColor("#E0E0E0"));
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.select_box_arrow_down, 0);
        textView.setCompoundDrawablePadding(16);
        textView.setPadding(23, 23, 23, 23);
        textView.setTextSize(16);
        textView.setText(text);
        return textView;
    }
}
