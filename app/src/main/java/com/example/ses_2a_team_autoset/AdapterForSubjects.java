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
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class AFSViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;

        public AFSViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.SubjectName);

            itemView.setOnClickListener(new View.OnClickListener() {
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
    public AdapterForSubjects(ArrayList<AddSubjectToSubjectView> subjectList) {
        mSubjectList = subjectList;
    }


    @NonNull
    @Override
    public AFSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subject, parent, false);
        AFSViewHolder afsvh = new AFSViewHolder(v, mListener);
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
