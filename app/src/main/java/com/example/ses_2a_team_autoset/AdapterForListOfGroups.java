package com.example.ses_2a_team_autoset;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForListOfGroups extends RecyclerView.Adapter<AdapterForListOfGroups.AFSViewHolder> {
    private ArrayList<AddGroupToGroupListView> mListOfGroups;




    public static class AFSViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1, mTextView2;

        public AFSViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.mGroupNumber);
            mTextView2 = itemView.findViewById(R.id.mStudentList);


        }
    }
    public AdapterForListOfGroups(ArrayList<AddGroupToGroupListView> ListOfGroups) {
        mListOfGroups = ListOfGroups;
    }


    @NonNull
    @Override
    public AFSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_groups, parent, false);
        AFSViewHolder afsvh = new AFSViewHolder(v);
        return afsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AFSViewHolder holder, int position) {
        AddGroupToGroupListView currentItem = mListOfGroups.get(position);

        holder.mTextView1.setText(currentItem.getGroup());
        holder.mTextView2.setText(currentItem.getStudents());
    }

    @Override
    public int getItemCount() {
        return  mListOfGroups.size();
    }
}
