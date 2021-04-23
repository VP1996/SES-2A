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

public class SubjectClassesAdmin extends AppCompatActivity {
    Button btnlogout, btnback, btnmailbox;
    public TextView ClassName;


    private FirebaseRecyclerOptions<Staffdetails> options;
    private FirebaseRecyclerAdapter<Staffdetails, sAdapter> adapter;
    DatabaseReference reference;
    RecyclerView recyclerView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjclass_admin);

       reference = FirebaseDatabase.getInstance().getReference().child("Users");

        RecyclerView recyclerView =  findViewById(R.id.myRecyclerstaff);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<Staffdetails>().setQuery(reference, Staffdetails.class).build();
        adapter= new FirebaseRecyclerAdapter<Staffdetails, SubjectClassesAdmin.sAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SubjectClassesAdmin.sAdapter holder, int position, @NonNull Staffdetails model) {

                holder.time.setText(model.getTime());
                holder.id.setText(model.getId());
                holder.group.setText(model.getGroup());
                holder.name.setText(model.getName());


            }

            @NonNull
            @Override
            public SubjectClassesAdmin.sAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View s = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewstaff, parent, false);
                return new SubjectClassesAdmin.sAdapter(s);
            }
        };

        Bundle bundle = getIntent().getExtras();
        String subject = bundle.getString("subject");

        ClassName = findViewById(R.id.SubjectName);
        btnback = findViewById(R.id.btn_back_admin);
        btnlogout = findViewById(R.id.btn_logout_admin);
        btnmailbox = findViewById(R.id.btn_mailbox_admin);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, Login.class));
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, HomeScreenStaff.class));
            }
        });
        btnmailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, Mailbox.class));
            }
        });
        ClassName.setText(subject);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, Login.class));
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, HomeScreenStaff.class));
            }
        });
        btnmailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubjectClassesAdmin.this, Mailbox.class));

            }
        });
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    private class sAdapter extends RecyclerView.ViewHolder {

        TextView time, id, group, name;
        public sAdapter(@NonNull View itemView) {
            super(itemView);

            time= itemView.findViewById(R.id.time);
            id= itemView.findViewById(R.id.id);
            group = itemView.findViewById(R.id.group);
            name = itemView.findViewById(R.id.name);

        }
    }


}
