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


    int results[]={1,2,3,4,5,6,7,8,9,10,11,12,13}; // Temp Answer Array

    //array list to store students
    ArrayList<Iterator<DataSnapshot>> Students = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_admin);
        Log.d("error message", "working");

        Button btsort = (Button) findViewById(R.id.btn_sort);

        //Sorting button
        btsort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //fetches the data when clicked
                getData();
                //initiaise g to be 0
                int g = 0;
                //loop through each student and fetch each multiple choice quiz answers

                /*
                for (int i = 0; i < Students.length; i++) {
                    sortAlgo(results);
                    results[0] = students[i].QuizPage3.mcq1;
                    results[1] = students[i].QuizPage3.mcq2;
                    results[2] = students[i].QuizPage3.mcq3;
                    results[3] = students[i].QuizPage3.mcq4;
                    results[4] = students[i].QuizPage3.mcq5;
                    results[5] = students[i].QuizPage3.mcq6;
                    results[6] = students[i].QuizPage3.mcq7;
                    results[7] = students[i].QuizPage3.mcq8;
                    results[8] = students[i].QuizPage3.mcq9;
                    results[9] = students[i].QuizPage4.mcq1;
                    results[10] = students[i].QuizPage4.mcq2;
                    results[11] = students[i].QuizPage4.mcq3;
                    results[12] = students[i].QuizPage4.mcq4;
                    if (g == 4) {
                        g = 1;
                    }
                    myRef.child("Subjects").child("Software Engineering Studio 1A").child("Group" + g);
                    g++;
                }
                 */
            }
        });
    }

    public void getData(){ //Gets student Ids from firebase
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    Iterator<DataSnapshot> ID = items;
                    Students.add(ID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sortAlgo(int results[]){
            long score =0;
            score=scorer(results);
            int arrLength =results.length;
            char[] strResults = new char[arrLength];

            for (int i = 0; i <arrLength; i++){
                strResults[i] = (char) results[i]; //change res to string so it can be hashed
            }

            long score1 = scorer(hashString(strResults,arrLength));
            //System.out.println("Score of result test: " + score); // whichever on is higher has to be used
            //System.out.println("Score of second test: " +score1);
    }
    public long scorer(int[] arr){
            int magExp = 1;
            int mod = 1000000007;
            int prime = 31;
            long score =arr[0];
            for(int i=1;i<arr.length;i++){
                score+=(arr[i]*Math.pow(prime,magExp)) % mod;
                magExp++;
            }
            return score;
    }
    public int[] hashString(char arr[], int length){
            int hash[];
            hash = new int[length];
            System.out.println("Translated array: " );
            for(int i = 0;i<length;i++){
                hash[i] = arr[i] -'a' + 1;
                System.out.print(hash[i]);
            }
            System.out.println(" ");
            return hash;
    }
}