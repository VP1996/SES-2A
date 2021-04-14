package com.example.ses_2a_team_autoset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {

    Context context;
    ArrayList<Studentdetails> users;

    public Myadapter(Context c , ArrayList<Studentdetails> p)
    {
        context = c;
        users= p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardviewstudent,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.subject.setText(users.get(position).getsubject());
        holder.time.setText(users.get(position).gettime());
        holder.location.setText(users.get(position).getlocation());
        holder.group.setText(users.get(position).getgroup());


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class MyViewHolder extends ViewHolder
    {
        TextView subject, time, location,group;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject);
            time = (TextView) itemView.findViewById(R.id.time);
            location = (TextView) itemView.findViewById(R.id.location);
            group = (TextView) itemView.findViewById(R.id.group);
            btn = (Button) itemView.findViewById(R.id.request);
        }
        public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}