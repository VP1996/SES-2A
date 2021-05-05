package com.example.ses_2a_team_autoset;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextThemeWrapper;
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
    int count = 1;
    DatabaseReference subjects,reff1,reff2;
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
    ArrayAdapter<String> facultiesAdapter, actvAdapter;

    String[] classesArray = {"Tut1", "Tut2", "Tut3"};
    ArrayList<String> classesList = new ArrayList<String>(Arrays.asList(classesArray));
    ArrayList<TextInputLayout> classesLayoutList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_page_two);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");
        subjects = database.getReference("Subjects");
        reff1 = FirebaseDatabase.getInstance().getReference().child("Subjects");
        reff2 = FirebaseDatabase.getInstance().getReference();
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
                builder.setMultiChoiceItems(subjectsArray, selectedSubjects, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!subjectsIndexList.contains(position))
                                subjectsIndexList.add(position);
                        } else {
                            subjectsIndexList.remove((Integer.valueOf(position)));
                            selectedSubjectsList.remove((Integer.valueOf(position)));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mLayout.removeAllViews();
                        classesLayoutList.clear();
                        selectedSubjectsList.clear();
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
                            TextInputLayout tilClass = createNewTextViewSelectClasses(subjectsArray[subjectsIndexList.get(i)]);
                            classesLayoutList.add(tilClass);
                            mLayout.addView(tilClass);
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
                        classesLayoutList.clear();
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

    private TextInputLayout createNewTextViewSelectClasses(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.bottomMargin = 20;
        TextInputLayout textInputLayout = new TextInputLayout(new ContextThemeWrapper(this, R.style.Widget_MaterialComponents_TextInputLayout_FilledBox_ExposedDropdownMenu));
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_FILLED);
        textInputLayout.setLayoutParams(lparams);
        AutoCompleteTextView actv = new AutoCompleteTextView(textInputLayout.getContext());
        actv.setHint(text);
        actv.setPadding(40, 60, 40, 60);
        actv.setInputType(InputType.TYPE_NULL);
        actv.setTextSize(16);

        actvAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_item, classesList);
        actv.setAdapter(actvAdapter);
        actv.setThreshold(1);

        textInputLayout.addView(actv, lparams);
        return textInputLayout;
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

        for (int i = 0; i < subjects.size(); i ++) {
            String classString = classesLayoutList.get(i).getEditText().getText().toString();
            String subjectString = selectedSubjectsList.get(i);
            reff1 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjectString).child(classString);
            reff1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        int gs = Integer. parseInt(snapshot.child("GroupSizes").getValue().toString());
                        int NOfG = (int)snapshot.child("Groups").getChildrenCount();
                        for(int z = 1;z<=NOfG;z++){
                            String GropN = "Group"+z;
                            for(int x = 1; x <= gs;x++){
                                String member = snapshot.child("Groups").child(GropN).child(String.valueOf(x)).getValue().toString();
                                if(member.equals("1") && count==1 ){
                                    count = 0;
                                    snapshot.child("Groups").child(GropN).child(String.valueOf(x)).getRef().setValue(ID);
                                }
                            }
                            if(z==NOfG && count==0){
                                count = 1;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            users.child(ID).child("Quiz").child("QuizPage2").child("subjects").child(String.valueOf(i)).child("subjectName").setValue(subjectString);
            users.child(ID).child("Quiz").child("QuizPage2").child("subjects").child(String.valueOf(i)).child("class").setValue(classString);

        }

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
