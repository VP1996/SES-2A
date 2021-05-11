package com.example.ses_2a_team_autoset;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.Math;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;

public class HomeScreenAdmin extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();    //use this for the firebase reference


    int results[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; // Temp Answer Array
    ArrayList<String> subjects= new ArrayList<>();
    //array list to store students
    ArrayList<Iterator<DataSnapshot>> Students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_admin);
        Log.d("Error Message", "Working");

        Button btsort = (Button) findViewById(R.id.btn_sort);

        //Sorting button
        btsort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //fetches the data when clicked
                getData();
                //initiaise g to be 0
                int g = 0;
                //loop through each student and fetch each multiple choice quiz answers


                for (int i = 0; i < Students.size(); i++) {
                    results[0] = Students.QuizPage3.mcq1;
                    results[1] = Students.QuizPage3.mcq2;
                    results[2] = Students.QuizPage3.mcq3;
                    results[3] = Students.QuizPage3.mcq4;
                    results[4] = Students.QuizPage3.mcq5;
                    results[5] = Students.QuizPage3.mcq6;
                    results[6] = Students.QuizPage3.mcq7;
                    results[7] = Students.QuizPage3.mcq8;
                    results[8] = Students.QuizPage3.mcq9;
                    results[9] = Students.QuizPage4.mcq1;
                    results[10] = Students.QuizPage4.mcq2;
                    results[11] = Students.QuizPage4.mcq3;
                    results[12] = Students.QuizPage4.mcq4;
                    long value = sortAlgo(results);
                    System.out.println(value);
                    if (g == 4) {
                        g = 1;
                    }
                    myRef.child("Subjects").child("Software Engineering Studio 1A").child("Group" + g);
                    g++;
                }

            }
        });
    }

    public void getSubjects(){
        myRef.child("Users").addValueEventListener(new ValueEventListener(){
           @Override
           public void onDataChange(DataSnapshot dataSnapshot){
               for(DataSnapshot snapshot: dataSnapshot.getChildren()){

               }
           }

           @Override
            public void onCancelled(@NonNull DatabaseError error){

           }
        });
    }

    public void getData(){ //Gets student Ids from firebase
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Person person = snapshot.getValue(Person.class);
                    System.out.println(person.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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