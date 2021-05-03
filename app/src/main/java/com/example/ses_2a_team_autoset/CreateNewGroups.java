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

    Button btnGoBack, btnSubmit;
    TextView tvSubjects;
    boolean[] selectedSubjects;
    TextInputLayout tGroupSize,tNumberOfGroups;
    LinearLayout mLayout;
    CurrentUser user;
    FirebaseDatabase database;
    DatabaseReference users,reff1;
    ArrayList<Integer> subjectsIndexList = new ArrayList<>();
    ArrayList<String> subjectsList = new ArrayList<>();
    ArrayList<String> selectedSubjectsList = new ArrayList<>();

    String[] subjectsArray;


    String[] classesArray = {"Tut1", "Tut2", "Tut3"};
    ArrayList<String> classesList = new ArrayList<String>(Arrays.asList(classesArray));
    ArrayList<TextInputLayout> classesLayoutList = new ArrayList<>();
    ArrayAdapter<String> actvAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewgroups);

        reff1 = FirebaseDatabase.getInstance().getReference().child("Subjects");
        btnGoBack = findViewById(R.id.btn_goBack);
        btnSubmit = findViewById(R.id.btn_submit);
        tvSubjects = findViewById(R.id.tv_subjects);
        tGroupSize = findViewById(R.id.GRoupSizes);//NumberOfGroups
        tNumberOfGroups = findViewById(R.id.NumberOfGroups);

        mLayout = findViewById(R.id.llSelectClasses);

        reff1.addValueEventListener(new ValueEventListener() {
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
                if (!validateSubjects() | !validateTextInput(tNumberOfGroups)| !validateTextInput(tGroupSize) )
                    return;

                saveDataG(selectedSubjectsList,
                        tGroupSize.getEditText().getText().toString(),
                        tNumberOfGroups.getEditText().getText().toString());
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

    private void saveDataG(ArrayList<String> subjects, String GroupSizes,String NumberOfGroups) {

        int sGroupSizes = Integer. parseInt(GroupSizes);
        int sNumberOfGroups = Integer. parseInt(NumberOfGroups);

        for (int i = 0; i < subjects.size(); i ++) {
            String classString = classesLayoutList.get(i).getEditText().getText().toString();
            String subjectString = selectedSubjectsList.get(i);
            reff1.child(subjectString).child(classString).child("GroupSizes").setValue(GroupSizes);


            for(int f = 1; f <= sNumberOfGroups;f++){

                for(int x = 1; x <= sGroupSizes;x++){
                    String gn = "Group"+String.valueOf(f);
                    reff1.child(subjectString).child(classString).child("Groups").child(gn).child(String.valueOf(x)).setValue("1");

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
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
