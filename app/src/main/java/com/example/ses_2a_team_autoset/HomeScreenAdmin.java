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
import java.lang.reflect.Array;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.widget.Toast;

public class HomeScreenAdmin extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();    //use this for the firebase reference


    int results[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}; // Temp Answer Array
    ArrayList<String> subjects= new ArrayList<>();
    //array list to store students
    ArrayList<ArrayList<String>> Students = new ArrayList<>(18);
    ArrayList<String> selectedClassesList = new ArrayList<>();
    ArrayList<String> selectedSubjectsList = new ArrayList<>();
    ArrayList<ArrayList<String>> SES1A = new ArrayList<>();
    ArrayList<ArrayList<String>> SES2A = new ArrayList<>();
    ArrayList<ArrayList<String>> SES1B = new ArrayList<>();
    ArrayList<ArrayList<String>> SES2B = new ArrayList<>();
    ArrayList<ArrayList<String>> SES3A = new ArrayList<>();
    ArrayList<ArrayList<String>> SES3B = new ArrayList<>();
    private final int TUTNUM = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_admin);
        Log.d("Error Message", "Working");

        Button btsort = (Button) findViewById(R.id.btn_sort);

        for (int i = 0; i < 16; i++) {
            Students.add(new ArrayList());
        }

        //Sorting button
        btsort.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //fetches the data when clicked
                getData();
                //initiaise g to be 0
                int g = 0;
                //loop through each student and fetch each multiple choice quiz answers

                for (int i = 0; i < Students.get(0).size(); i++){
                        results[0] = Integer.parseInt(Students.get(2).get(i));
                        results[1] = Integer.parseInt(Students.get(3).get(i));
                        results[2] = Integer.parseInt(Students.get(4).get(i));
                        results[3] = Integer.parseInt(Students.get(5).get(i));
                        results[4] = Integer.parseInt(Students.get(6).get(i));
                        results[5] = Integer.parseInt(Students.get(7).get(i));
                        results[6] = Integer.parseInt(Students.get(8).get(i));
                        results[7] = Integer.parseInt(Students.get(9).get(i));
                        results[8] = Integer.parseInt(Students.get(10).get(i));
                        results[9] = Integer.parseInt(Students.get(11).get(i));
                        results[10] = Integer.parseInt(Students.get(12).get(i));
                        results[11] = Integer.parseInt(Students.get(13).get(i));
                        results[12] = Integer.parseInt(Students.get(14).get(i));
                        long value = sortAlgo(results);
                        System.out.println(value);
                        Students.get(16).add(Long.toString(value)); //Adds scores to ArrayList
                }

                inputSubjects();


            }
        });
    }
    public void addSubjects(){
        for (int i = 0; i < TUTNUM; i++) {
            SES1A.add(new ArrayList());
            SES2A.add(new ArrayList());
            SES3A.add(new ArrayList());
            SES1B.add(new ArrayList());
            SES2B.add(new ArrayList());
            SES3B.add(new ArrayList());
        }

        for (int i = 0; i < Students.get(0).size(); i++){
            switch (Students.get(15).get(i)) {
                case "Software Engineering Studio 1A":
                    if (Students.get(16).get(i).contains("Tut1"))
                        SES1A.get(0).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut2"))
                        SES1A.get(1).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut3"))
                        SES1A.get(2).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));

                case "Software Engineering Studio 2A":
                    if (Students.get(16).get(i).contains("Tut1"))
                        SES2A.get(0).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut2"))
                        SES2A.get(1).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut3"))
                        SES2A.get(2).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));

                case "Software Engineering Studio 3A":
                    if (Students.get(16).get(i).contains("Tut1"))
                        SES3A.get(0).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut2"))
                        SES3A.get(1).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut3"))
                        SES3A.get(2).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));

                case "Software Engineering Studio 1B":
                    if (Students.get(16).get(i).contains("Tut1"))
                        SES1B.get(0).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut2"))
                        SES1B.get(1).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut3"))
                        SES1B.get(2).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));

                case "Software Engineering Studio 2B":
                    if (Students.get(16).get(i).contains("Tut1"))
                        SES2B.get(0).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut2"))
                        SES2B.get(1).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut3"))
                        SES2B.get(2).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));

                case "Software Engineering Studio 3B":
                    if (Students.get(16).get(i).contains("Tut1"))
                        SES3B.get(0).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut2"))
                        SES3B.get(1).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));
                    else if (Students.get(16).get(i).contains("Tut3"))
                        SES3B.get(2).add(Students.get(0).get(i) + "_" + Students.get(16).get(i));

            }
        }
    }

    public void inputSubjects(){

        int sGroupSizes =5;
        int sNumberOfGroups=5;
        String GroupSizes = "8";


        for (int i = 0; i < subjects.size(); i ++) {
            for (int tut = 0; tut < SES1A.get(0).size(); tut ++) { // tut in each class

                String classString = selectedClassesList.get(tut);
                String subjectString = selectedSubjectsList.get(i);
                myRef.child(subjectString).child(classString).child("GroupSizes").setValue(GroupSizes); // adds group to database
                myRef.child(subjectString).child(classString).child("Groups").getRef().removeValue();
                for(int f = 1; f <= sNumberOfGroups;f++){
                    for(int x = 1; x <= sGroupSizes;x++){
                        String gn = "Group"+String.valueOf(f);
                        myRef.child(subjectString).child(classString).child("Groups").child(gn).child(String.valueOf(x)).setValue("1"); //Student id here
                    }
                }
            }
        }
        Toast.makeText(HomeScreenAdmin.this, "New Groups Added", Toast.LENGTH_SHORT).show();
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

    public void getData(){ //Gets all data for the sort algo
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    CurrentUser currentUser = snapshot.getValue(CurrentUser.class);
                    QP2Answers qp2 = snapshot.getValue(QP2Answers.class);
                    QP3Answers qp3 = snapshot.getValue(QP3Answers.class);
                    QP4Answers qp4 = snapshot.getValue(QP4Answers.class);
                    Studentdetails std = snapshot.getValue(Studentdetails.class);

                    for (String subject: qp2.getSubjects()) {
                        Students.get(0).add(currentUser.getID());
                        Students.get(1).add(qp3.getMcq1());
                        Students.get(2).add(qp3.getMcq3());
                        Students.get(3).add(qp3.getMcq4());
                        Students.get(4).add(qp3.getMcq5());
                        Students.get(5).add(qp3.getMcq6());
                        Students.get(6).add(qp3.getMcq7());
                        Students.get(7).add(qp3.getMcq8());
                        Students.get(8).add(qp3.getMcq9());   //Adds question answer to ArrayList
                        Students.get(9).add(qp3.getMcq10());
                        Students.get(10).add(qp3.getMcq11());
                        Students.get(11).add(qp4.getMcq1());
                        Students.get(12).add(qp4.getMcq2());
                        Students.get(13).add(qp4.getMcq3());
                        Students.get(14).add(qp4.getMcq4());
                        Students.get(15).add(subject); //Adds specific subject
                        Students.get(16).add(std.getTut());
                    }


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