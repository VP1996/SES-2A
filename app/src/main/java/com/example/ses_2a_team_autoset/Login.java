package com.example.ses_2a_team_autoset;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Login extends AppCompatActivity {
    EditText etUsername,etPassword;
    Button btSubmit;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference DRef;
    //String currentUserId;
    CurrentUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Firebase
        database = FirebaseDatabase.getInstance("https://ses-2a-studybuddies-default-rtdb.firebaseio.com/");
        DRef = database.getReference("Users");





        etUsername = findViewById(R.id.et_userID);
        etPassword = findViewById(R.id.et_password);
        btSubmit = findViewById(R.id.bt_login);



        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(etUsername.getText().toString(),
                        etPassword.getText().toString());

            }
        });


    }

    private void logIn(final String UID, final String UPassword) {
        DRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(UID).exists()){
                    if(!UID.isEmpty()){
                        String password = dataSnapshot.child(UID).child("password").getValue().toString();
                        if(password.equals(UPassword)){
                            String type = dataSnapshot.child(UID).child("type").getValue().toString();
                            user = new CurrentUser();
                            user.setID(UID);
                            user.setFirstName(dataSnapshot.child(UID).child("firstName").getValue().toString());
                            user.setLastName(dataSnapshot.child(UID).child("lastName").getValue().toString());


                            if(type.equals("Student")){
                                Toast.makeText(Login.this, "Success Login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, HomeScreenStudent.class));
                            }else if(type.equals("Staff")){
                                Toast.makeText(Login.this, "Success Login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, HomeScreenStaff.class));
                            }else
                                Toast.makeText(Login.this, "Wrong type", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }

                }else
                    Toast.makeText(Login.this, "Wrong ID", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Login.this, "****NOT FOUND****", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
