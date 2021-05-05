package com.example.ses_2a_team_autoset;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
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

public class StudentEditProfile extends AppCompatActivity {
    Button btnLogout, btnHome, btnSave;
    TextInputLayout tilFullName, tilEmail, tilAge, tilPhone, tilAddress, tilCulturalBack, tilDegree, tilGpa,
            tilAvailabilities;
    RadioGroup radGrpGender, radGrpStudyLevel;
    RadioButton radioBtnSelectedGender, radioBtnSelectedLevel;
    TextView tvSubjects;
    LinearLayout mLayout;
    AutoCompleteTextView actvFaculties;

    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference studentRef, subjectsRef;
    QP1Answers QP1Answers;
    QP2Answers QP2Answers;

    ArrayList<Integer> subjectsIndexList = new ArrayList<>();
    ArrayList<String> subjectsList = new ArrayList<>();
    ArrayList<String> selectedSubjectsList = new ArrayList<>();
    String[] subjectsArray;
    boolean[] isSubjectsSelected;

    String[] classesArray = {"Tut1", "Tut2", "Tut3"};
    ArrayList<String> classesList = new ArrayList<String>(Arrays.asList(classesArray));
    ArrayList<TextInputLayout> classesLayoutList = new ArrayList<>();

    ArrayAdapter<String> facultiesAdapter, classAdapter;
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

    String fullName, email, age, phone, address, culturalBack, faculty, degree,
            gpa, availabilities, gender, studyLevel;
    ArrayList<String> subjectsNameList = new ArrayList<>();
    ArrayList<String> subjectsClassesList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_edit_profile);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        studentRef = FirebaseDatabase.getInstance().getReference("Users");
        subjectsRef = FirebaseDatabase.getInstance().getReference("Subjects");

        String ID = user.getID();

        btnLogout = findViewById(R.id.btn_logout_edit_profile);
        btnHome = findViewById(R.id.btn_home_edit_profile);
        btnSave = findViewById(R.id.btn_save_edit_profile);
        tilFullName = findViewById(R.id.layout_fullName_edit);
        tilEmail = findViewById(R.id.layout_email_edit);
        tilAge = findViewById(R.id.layout_age_edit);
        tilPhone = findViewById(R.id.layout_phone_edit);
        tilAddress = findViewById(R.id.layout_address_edit);
        tilCulturalBack = findViewById(R.id.layout_background_edit);
        tvSubjects = findViewById(R.id.tv_subjects_edit);
        tilDegree = findViewById(R.id.layout_major_edit);
        tilGpa = findViewById(R.id.layout_gpa_edit);
        tilAvailabilities = findViewById(R.id.layout_availabilities_edit);
        radGrpGender = findViewById(R.id.radGrpGender_edit);
        radGrpStudyLevel = findViewById(R.id.radioGrpStudyLevel);
        mLayout = findViewById(R.id.llSelectClasses_edit);
        actvFaculties = findViewById(R.id.actv_faculties_edit);

        getAllStudentData();

        subjectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    subjectsList.add(dataSnapshot.getKey().toString());
                }
                isSubjectsSelected = new boolean[subjectsList.size()];
                subjectsArray = subjectsList.toArray(new String[subjectsList.size()]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentEditProfile.this, "No subjects found", Toast.LENGTH_SHORT).show();
            }
        });

        tvSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentEditProfile.this);
                builder.setTitle("Select Subjects");
                builder.setMultiChoiceItems(subjectsArray, isSubjectsSelected, new DialogInterface.OnMultiChoiceClickListener() {
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
                        for (int i = 0; i < isSubjectsSelected.length; i++) {
                            //Remove all selections
                            isSubjectsSelected[i] = false;
                            subjectsIndexList.clear();
                            tvSubjects.setText("");
                        }
                    }
                });
                //Show dialog
                builder.show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentEditProfile.this, Login.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentEditProfile.this, HomeScreenStudent.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBtnSelectedGender = findViewById(radGrpGender.getCheckedRadioButtonId());
                radioBtnSelectedLevel = findViewById(radGrpStudyLevel.getCheckedRadioButtonId());
                ArrayList<String> toBeStoredSubjectsList = new ArrayList<>();
                if (!selectedSubjectsList.isEmpty())
                    toBeStoredSubjectsList = new ArrayList<>(selectedSubjectsList);
                else
                    toBeStoredSubjectsList = new ArrayList<>(subjectsNameList);

                saveEdit(tilFullName.getEditText().getText().toString(),
                        tilEmail.getEditText().getText().toString(),
                        radioBtnSelectedGender.getText().toString(),
                        tilAge.getEditText().getText().toString(),
                        tilPhone.getEditText().getText().toString(),
                        tilAddress.getEditText().getText().toString(),
                        tilCulturalBack.getEditText().getText().toString(),
                        toBeStoredSubjectsList,
                        tilAvailabilities.getEditText().getText().toString(),
                        actvFaculties.getText().toString(),
                        tilDegree.getEditText().getText().toString(),
                        radioBtnSelectedLevel.getText().toString(),
                        tilGpa.getEditText().getText().toString());
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

        classAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.menu_item, classesList);
        actv.setAdapter(classAdapter);
        actv.setThreshold(1);

        textInputLayout.addView(actv, lparams);
        return textInputLayout;
    }

    private void saveEdit(String fullName, String email, String gender, String age, String phoneNumber, String address,
                          String cultureBackground, ArrayList<String> subjects, String availabilities, String faculty,
                          String degree, String studyLevel, String gpa) {
        QP1Answers = new QP1Answers();
        QP1Answers.setFullName(fullName);
        QP1Answers.setEmail(email);
        QP1Answers.setGender(gender);
        QP1Answers.setAge(age);
        QP1Answers.setPhoneNumber(phoneNumber);
        QP1Answers.setAddress(address);
        QP1Answers.setCulturalBackground(cultureBackground);

        QP2Answers = new QP2Answers();
        QP2Answers.setSubjects(subjects);
        QP2Answers.setAvailabilities(availabilities);
        QP2Answers.setFaculty(faculty);
        QP2Answers.setDegree(degree);
        QP2Answers.setStudyLevel(studyLevel);
        QP2Answers.setGPA(gpa);

        String ID = user.getID();
        studentRef.child(ID).child("Quiz").child("QuizPage1").setValue(QP1Answers);
        studentRef.child(ID).child("Quiz").child("QuizPage2").setValue(QP2Answers);

        for (int i = 0; i < subjects.size(); i++) {
            String classString = "";
            String subjectString = "";
            if (!selectedSubjectsList.isEmpty()) {
                classString = classesLayoutList.get(i).getEditText().getText().toString();
                subjectString = selectedSubjectsList.get(i);
            } else {
                classString = subjectsClassesList.get(i);
                subjectString = subjectsNameList.get(i);
            }

            studentRef.child(ID).child("Quiz").child("QuizPage2").child("subjects")
                    .child(String.valueOf(i)).child("subjectName").setValue(subjectString);
            studentRef.child(ID).child("Quiz").child("QuizPage2").child("subjects")
                    .child(String.valueOf(i)).child("class").setValue(classString);
        }
        startActivity(new Intent(StudentEditProfile.this, StudentProfile.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getAllStudentData() {
        Intent studentData = getIntent();
        fullName = studentData.getStringExtra("fullName");
        email = studentData.getStringExtra("email");
        age = studentData.getStringExtra("age");
        phone = studentData.getStringExtra("phone");
        address = studentData.getStringExtra("address");
        culturalBack = studentData.getStringExtra("culturalBack");
        degree = studentData.getStringExtra("degree");
        gpa = studentData.getStringExtra("gpa");
        availabilities = studentData.getStringExtra("availabilities");
        gender = studentData.getStringExtra("gender");
        studyLevel = studentData.getStringExtra("studyLevel");
        faculty = studentData.getStringExtra("faculty");
        subjectsNameList = studentData.getStringArrayListExtra("subjectsName");
        subjectsClassesList = studentData.getStringArrayListExtra("subjectsClass");

        tilFullName.getEditText().setText(fullName);
        tilEmail.getEditText().setText(email);
        tilAge.getEditText().setText(age);
        tilPhone.getEditText().setText(phone);
        tilAddress.getEditText().setText(address);
        tilCulturalBack.getEditText().setText(culturalBack);
        tilDegree.getEditText().setText(degree);
        tilGpa.getEditText().setText(gpa);
        tilAvailabilities.getEditText().setText(availabilities);
        actvFaculties.setText(faculty, false);

        // Check gender radio button
        if (gender.equals("Female"))
            radGrpGender.check(R.id.radioBtn_female_edit);
        if (gender.equals("Male"))
            radGrpGender.check(R.id.radioBtn_male_edit);
        if (gender.equals("Other"))
            radGrpGender.check(R.id.radioBtn_other_edit);

        // Check study level button
        if (studyLevel.equals("Undergraduate"))
            radGrpStudyLevel.check(R.id.radioBtn_ug_edit);
        if (studyLevel.equals("Postgraduate"))
            radGrpStudyLevel.check(R.id.radioBtn_pg_edit);
        if (studyLevel.equals("PhD"))
            radGrpStudyLevel.check(R.id.radioBtn_phd_edit);

        // Display the subjects and tutorials stored in database
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < subjectsNameList.size(); i++) {
            stringBuilder.append(subjectsNameList.get(i));
            if (i != subjectsNameList.size() - 1) {
                stringBuilder.append(", ");
            }
            TextInputLayout tilClass = createNewTextViewSelectClasses(subjectsNameList.get(i));
            classesLayoutList.add(tilClass);
            mLayout.addView(tilClass);
            tilClass.getEditText().setText(subjectsClassesList.get(i));
        }
        tvSubjects.setText(stringBuilder.toString());
    }
}
