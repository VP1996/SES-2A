package com.example.ses_2a_team_autoset;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForSubjects extends RecyclerView.Adapter<AdapterForSubjects.AFSViewHolder> {
    private ArrayList<AddSubjectToSubjectView> mSubjectList;

    public static class AFSViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;

        public AFSViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.SubjectName);
        }
    }

    public AdapterForSubjects(ArrayList<AddSubjectToSubjectView> subjectList) {
        mSubjectList = subjectList;
    }


    @NonNull
    @Override
    public AFSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subject, parent, false);
        AFSViewHolder afsvh = new AFSViewHolder(v);
        return afsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AFSViewHolder holder, int position) {
        AddSubjectToSubjectView currentItem = mSubjectList.get(position);

        holder.mTextView1.setText(currentItem.getSubject1());



    }

    @Override
    public int getItemCount() {
        return  mSubjectList.size();
    }
}
