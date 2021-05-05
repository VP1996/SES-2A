package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.ArrayList;

public class StudentProfile extends AppCompatActivity {
    Button btnLogout, btnHome, btnEditProfile, btnEditQuiz;
    TextView tvName;
    TextInputLayout tilEmail, tilAge, tilPhone, tilAddress, tilCulturalBack, tilFaculty, tilDegree, tilGpa,
            tilStudyLevel, tilSubjects, tilAvailabilities;

    String fullName, email, age, phone, address, culturalBack, faculty, degree,
            gpa, studyLevel, availabilities, gender;
    String subjects = "";

    ArrayList<String> subjectsNameList = new ArrayList<>();
    ArrayList<String> subjectsClassesList = new ArrayList<>();

    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        users = database.getReference("Users");

        String ID = user.getID();

        btnLogout = findViewById(R.id.btn_logout_profile);
        btnHome = findViewById(R.id.btn_home_profile);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnEditQuiz = findViewById(R.id.btn_edit_quiz_answers);
        tvName = findViewById(R.id.tv_studentName);
        tilEmail = findViewById(R.id.layout_profileEmail);
        tilAge = findViewById(R.id.layout_profileAge);
        tilPhone = findViewById(R.id.layout_profilePhone);
        tilAddress = findViewById(R.id.layout_profileAddress);
        tilCulturalBack = findViewById(R.id.layout_profileCulturalBackground);
        tilFaculty = findViewById(R.id.layout_profileFaculty);
        tilDegree = findViewById(R.id.layout_profileDegree);
        tilGpa = findViewById(R.id.layout_profileGpa);
        tilStudyLevel = findViewById(R.id.layout_profileStudentLevel);
        tilSubjects = findViewById(R.id.layout_profileSubjects);
        tilAvailabilities = findViewById(R.id.layout_profileAvailabilities);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(ID).child("Quiz").exists()) {
                    fullName = snapshot.child(ID).child("Quiz").child("QuizPage1").child("fullName").getValue().toString();
                    email = snapshot.child(ID).child("Quiz").child("QuizPage1").child("email").getValue().toString();
                    age = snapshot.child(ID).child("Quiz").child("QuizPage1").child("age").getValue().toString();
                    phone = snapshot.child(ID).child("Quiz").child("QuizPage1").child("phoneNumber").getValue().toString();
                    address = snapshot.child(ID).child("Quiz").child("QuizPage1").child("address").getValue().toString();
                    culturalBack = snapshot.child(ID).child("Quiz").child("QuizPage1").child("culturalBackground").getValue().toString();
                    faculty = snapshot.child(ID).child("Quiz").child("QuizPage2").child("faculty").getValue().toString();
                    degree = snapshot.child(ID).child("Quiz").child("QuizPage2").child("degree").getValue().toString();
                    gpa = snapshot.child(ID).child("Quiz").child("QuizPage2").child("gpa").getValue().toString();
                    studyLevel = snapshot.child(ID).child("Quiz").child("QuizPage2").child("studyLevel").getValue().toString();
                    availabilities = snapshot.child(ID).child("Quiz").child("QuizPage2").child("availabilities").getValue().toString();
                    gender = snapshot.child(ID).child("Quiz").child("QuizPage1").child("gender").getValue().toString();

                    for (DataSnapshot dataSnapshot : snapshot.child(ID).child("Quiz").child("QuizPage2").child("subjects").getChildren()) {
                        if (dataSnapshot.child("subjectName").exists() && dataSnapshot.child("class").exists()) {
                            String subjectName = dataSnapshot.child("subjectName").getValue().toString();
                            String subjectClass = dataSnapshot.child("class").getValue().toString();
                            subjectsNameList.add(subjectName);
                            subjectsClassesList.add(subjectClass);

                            if (subjects == "")
                                subjects = subjectName + "-" + subjectClass;
                            else
                                subjects = subjects + ", " + subjectName + "-" + subjectClass;
                        }
                    }


                    tvName.setText(fullName);
                    tilEmail.getEditText().setText(email);
                    tilAge.getEditText().setText(age);
                    tilPhone.getEditText().setText(phone);
                    tilAddress.getEditText().setText(address);
                    tilCulturalBack.getEditText().setText(culturalBack);
                    tilFaculty.getEditText().setText(faculty);
                    tilDegree.getEditText().setText(degree);
                    tilGpa.getEditText().setText(gpa);
                    tilStudyLevel.getEditText().setText(studyLevel);
                    tilAvailabilities.getEditText().setText(availabilities);
                    tilSubjects.getEditText().setText(subjects);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfile.this, "No student found", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, Login.class));
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, HomeScreenStudent.class));
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StudentEditProfile.class);
                intent.putExtra("fullName", fullName);
                intent.putExtra("email", email);
                intent.putExtra("age", age);
                intent.putExtra("phone", phone);
                intent.putExtra("address", address);
                intent.putExtra("culturalBack", culturalBack);
                intent.putExtra("faculty", faculty);
                intent.putExtra("degree", degree);
                intent.putExtra("gpa", gpa);
                intent.putExtra("studyLevel", studyLevel);
                intent.putExtra("availabilities", availabilities);
                intent.putExtra("subjectsName", subjectsNameList);
                intent.putExtra("subjectsClass", subjectsClassesList);
                intent.putExtra("gender", gender);
                startActivity(intent);
                //startActivity(new Intent(StudentProfile.this, StudentEditProfile.class));
            }
        });

        btnEditQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this, StudentQuizAnswers.class));
            }
        });
    }
}
