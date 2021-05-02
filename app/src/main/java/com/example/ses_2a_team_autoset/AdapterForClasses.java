package com.example.ses_2a_team_autoset;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForClasses extends RecyclerView.Adapter<AdapterForClasses.AFSViewHolder> {
    private ArrayList<AddSubjectToClassesView> mClassList;
    private OnItemClickListener mListener2;
    CurrentUser user;
    View v;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener2 = listener;
    }

    public static class AFSViewHolder extends RecyclerView.ViewHolder{
        public TextView mtime, mlocation, mgroup, mtut;
        public Button bRequest;
        CurrentUser user;
        public AFSViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            String Type = user.getType();
            bRequest= itemView.findViewById(R.id.requestButton);
            mtime= itemView.findViewById(R.id.time);
            mlocation= itemView.findViewById(R.id.location);
            mgroup = itemView.findViewById(R.id.group);
            mtut = itemView.findViewById(R.id.tut);

            bRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
    public AdapterForClasses(ArrayList<AddSubjectToClassesView> classlist) {
        mClassList = classlist;
    }


    @NonNull
    @Override
    public AFSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        String Type = user.getType();
        if(Type.equals("Student")){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewstudent, parent, false);

        }else if (Type.equals("Staff")){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewadmin, parent, false);
        }
        AFSViewHolder afsvh = new AFSViewHolder(v, mListener2);//for student
        return afsvh;

    }

    @Override
    public void onBindViewHolder(@NonNull AFSViewHolder holder, int position) {
        AddSubjectToClassesView currentItem = mClassList.get(position);

        holder.mtut.setText(currentItem.getTutorialAct());
        holder.mtime.setText(currentItem.getDaytime());
        holder.mlocation.setText(currentItem.getLocation());
        holder.mgroup.setText(currentItem.getGroup());
                                                            // 4 of these for all of them .....
    }

    @Override
    public int getItemCount() {
        return  mClassList.size();
    }
}
