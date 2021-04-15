package com.example.ses_2a_team_autoset;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubjectClassesStudent extends AppCompatActivity {

    Button btnlogout, btnback, btnprofile;
    public TextView ClassName;
    DatabaseReference reference;
    RecyclerView recyclerView;

    private FirebaseRecyclerOptions<Studentdetails> options;
    private FirebaseRecyclerAdapter<Studentdetails, mAdapter> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_student);

        reference = FirebaseDatabase.getInstance().getReference().child("SubjectInfo");
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerstudent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<Studentdetails>().setQuery(reference, Studentdetails.class).build();
        adapter= new FirebaseRecyclerAdapter<Studentdetails, mAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull mAdapter holder, int position, @NonNull Studentdetails model) {

                holder.time.setText(model.getTime());
                holder.location.setText(model.getLocation());
                holder.group.setText(model.getGroup());


            }

            @NonNull
            @Override
            public mAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewstudent, parent, false);
                return new mAdapter(v);
            }
        };



        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");


        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_student);
        btnlogout = findViewById(R.id.btn_logout_student);
        btnprofile = findViewById(R.id.btn_profile_student);


        ClassName.setText(subject);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesStudent.this, Login.class));
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesStudent.this, HomeScreenStudent.class));
            }
        });
        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesStudent.this, Profile.class));
            }
        });

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    private class mAdapter extends RecyclerView.ViewHolder {

        TextView time, location, group;
        public mAdapter(@NonNull View itemView) {
            super(itemView);

            time= itemView.findViewById(R.id.time);
            location= itemView.findViewById(R.id.location);
            group = itemView.findViewById(R.id.group);

        }
    }
}
