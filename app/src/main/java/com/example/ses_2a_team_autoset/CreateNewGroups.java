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

public class CreateNewGroups extends AppCompatActivity {

    Button btnGoBack, btnSubmit,btnSortAlg;
    TextView tvSubjects,tvClasses;
    boolean[] selectedSubjects;
    boolean[] selectedClasses;
    int lol = 0;
    int lolAgain = 0;
    TextInputLayout tGroupSize,tNumberOfGroups;
    FirebaseDatabase database;
    DatabaseReference reffff,reff1,Sortthing;
    ArrayList<Integer> subjectsIndexList = new ArrayList<>();
    ArrayList<String> subjectsList = new ArrayList<>();
    ArrayList<String> selectedSubjectsList = new ArrayList<>();
    int results[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; // Temp Answer Array

    String[] classesArray = {"Tut1", "Tut2", "Tut3"};
    ArrayList<String> classesList = new ArrayList<String>(Arrays.asList(classesArray));

    ArrayList<Integer> classesIndexList = new ArrayList<>();
    ArrayList<String> selectedClassesList = new ArrayList<>();

    String[] subjectsArray;


    ArrayAdapter<String> actvAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewgroups);

        reff1 = FirebaseDatabase.getInstance().getReference().child("Subjects");
        Sortthing = FirebaseDatabase.getInstance().getReference().child("Users");
        btnGoBack = findViewById(R.id.btn_goBack);
        btnSubmit = findViewById(R.id.btn_submit);
        tvSubjects = findViewById(R.id.tv_subjects);
        btnSortAlg = findViewById(R.id.btn_sortAlg);
        tvClasses = findViewById(R.id.tv_classes);
        tGroupSize = findViewById(R.id.GRoupSizes);//NumberOfGroups
        tNumberOfGroups = findViewById(R.id.NumberOfGroups);


        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    subjectsList.add(dataSnapshot.getKey().toString());
                }
                selectedSubjects = new boolean[subjectsList.size()];
                selectedClasses = new boolean[classesList.size()];
                subjectsArray = subjectsList.toArray(new String[subjectsList.size()]);
                classesArray = classesList.toArray(new String[classesList.size()]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CreateNewGroups.this, "No subjects found", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewGroups.this, HomeScreenStaff.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateSubjects() | !validateClasses() | !validateTextInput(tNumberOfGroups)| !validateTextInput(tGroupSize) )
                    return;

                saveDataG(selectedSubjectsList, selectedClassesList,
                        tGroupSize.getEditText().getText().toString(),
                        tNumberOfGroups.getEditText().getText().toString());
            }
        });

        btnSortAlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sortthing.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("type").equals("Student") && dataSnapshot.child("quizTaken").equals("1")) {
                                    for (DataSnapshot dp : dataSnapshot.child("Quiz").child("QuizPage3").getChildren()) {
                                        if(dp.child("friendId").equals("") && lol <11 ){
                                            String jeff= "mcq"+ String.valueOf(lol+1);
                                            results[lol] = Integer.parseInt(dp.child(jeff).getRef().toString());
                                            lol++;
                                            }
                                        }
                                    for (DataSnapshot dank : dataSnapshot.child("Quiz").child("QuizPage4").getChildren()) {
                                        if(lol >=11 ){
                                            String jeff= "mcq"+ String.valueOf(lolAgain+1);
                                            results[lol] = Integer.parseInt(dank.child(jeff).getRef().toString());
                                            lol++;
                                            lolAgain++;
                                        }
                                    }

                                        long value = sortAlgo(results);
                                        dataSnapshot.child("QuizREsults").getRef().setValue(value);


                                    }
                                }
                            }
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        tvSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewGroups.this);
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
                            subjectsIndexList.clear();
                            tvSubjects.setText("");
                        }
                    }
                });
                //Show dialog
                builder.show();
            }
        });

        tvClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewGroups.this);
                builder.setTitle("Select Classes");
                builder.setMultiChoiceItems(classesArray, selectedClasses, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!classesIndexList.contains(position))
                                classesIndexList.add(position);
                        } else {
                            classesIndexList.remove((Integer.valueOf(position)));
                            selectedClassesList.remove((Integer.valueOf(position)));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedClassesList.clear();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < classesIndexList.size(); i++) {
                            selectedClassesList.add(classesArray[classesIndexList.get(i)]);
                            //Concat array value
                            stringBuilder.append(classesArray[classesIndexList.get(i)]);
                            if (i != classesIndexList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        tvClasses.setText(stringBuilder.toString());

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
                        for (int i = 0; i < selectedClasses.length; i++) {
                            //Remove all selections
                            selectedClasses[i] = false;
                            classesIndexList.clear();
                            tvClasses.setText("");
                        }
                    }
                });
                //Show dialog
                builder.show();
            }
        });

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

    private void saveDataG(ArrayList<String> subjects, ArrayList<String> classes, String GroupSizes,String NumberOfGroups) {

        int sGroupSizes = Integer. parseInt(GroupSizes);
        int sNumberOfGroups = Integer. parseInt(NumberOfGroups);

        for (int i = 0; i < subjects.size(); i ++) {
            for (int tut = 0; tut < classes.size(); tut ++) {

                String classString = selectedClassesList.get(tut);
                String subjectString = selectedSubjectsList.get(i);
                reff1.child(subjectString).child(classString).child("GroupSizes").setValue(GroupSizes);
                reff1.child(subjectString).child(classString).child("Groups").getRef().removeValue();
                for(int f = 1; f <= sNumberOfGroups;f++){
                    for(int x = 1; x <= sGroupSizes;x++){
                        String gn = "Group"+String.valueOf(f);
                        reff1.child(subjectString).child(classString).child("Groups").child(gn).child(String.valueOf(x)).setValue("1");
                    }
                }
            }
        }
        Toast.makeText(CreateNewGroups.this, "New Groups Added", Toast.LENGTH_SHORT).show();
        reload();
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
            tvSubjects.setError("Subjects are required");
            return false;
        } else {
            tvSubjects.setError(null);
            return true;
        }
    }
    private boolean validateClasses() {
        if (selectedClassesList.isEmpty()) {
            tvClasses.setError("Classes are required");
            return false;
        } else {
            tvClasses.setError(null);
            return true;
        }
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    public long sortAlgo(int results[]){
        long score =0;
        score=scorer(results);
        int arrLength =results.length;
        char[] strResults = new char[arrLength];

        for (int i = 0; i <arrLength; i++){
            strResults[i] = (char) results[i]; //change res to string so it can be hashed
        }

        long score1 = scorer(hashString(strResults,arrLength));

        return score1;
    }

    //function to give the score of the user
    public long scorer(int[] arr){
        int magExp = 1;
        int mod = 1000000007;
        int prime = 5;
        long score =arr[0];
        for(int i=1;i<arr.length;i++){
            score+=(arr[i]*Math.pow(prime,magExp)) % mod;
            magExp++;
        }
        return score;
    }

    //function to compute the integer value of the string
    public int[] hashString(char arr[], int length){
        int hash[];
        hash = new int[length];
        for(int i = 0; i < length; i++){
            if(arr[i] <= 'Z' && arr[i] >= 'A') {
                hash[i] = arr[i] - 'A' + 1;
            }else if(arr[i] <= 'z' && arr[i] >= 'a'){
                hash[i] = arr[i] - 'a' + 1;
            }else{
                hash[i] = arr[i];
            }
        }

        return hash;
    }


}
