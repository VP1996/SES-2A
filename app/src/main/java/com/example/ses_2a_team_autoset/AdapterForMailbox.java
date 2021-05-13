package com.example.ses_2a_team_autoset;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterForMailbox extends RecyclerView.Adapter<AdapterForMailbox.AFSViewHolder> {
    private ArrayList<AddMailToMailboxView> mMail;
    private OnItemClickListener mListener3;

    public interface OnItemClickListener {
        void onApprove(int position);
        void onDeclive(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener3 = listener;
    }

    public static class AFSViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1,mTextView2,mTextView3,mTextView4,mTextView5;
        public Button bApprove,bDec;

        public AFSViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.mStudentName);
            mTextView2 = itemView.findViewById(R.id.mSubClass);
            mTextView3 = itemView.findViewById(R.id.mReason);
            mTextView4 = itemView.findViewById(R.id.mCGroup);
            mTextView5 = itemView.findViewById(R.id.mEGroup);

            bApprove = itemView.findViewById(R.id.btn_Approve);
            bDec = itemView.findViewById(R.id.btn_Decline);

            bApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onApprove(position);
                        }
                    }
                }
            });

            bDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeclive(position);
                        }
                    }
                }
            });




        }
    }
    public AdapterForMailbox(ArrayList<AddMailToMailboxView> Mail) {
        mMail = Mail;
    }


    @NonNull
    @Override
    public AFSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewmailbox, parent, false);
        AFSViewHolder afsvh = new AFSViewHolder(v, mListener3);
        return afsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AFSViewHolder holder, int position) {
        AddMailToMailboxView currentItem = mMail.get(position);

        holder.mTextView1.setText(currentItem.getFullNameStudentID());
        holder.mTextView2.setText(currentItem.getSubjectClassType());
        holder.mTextView3.setText(currentItem.getReason());
        holder.mTextView4.setText(currentItem.getCurrentGroup());
        holder.mTextView5.setText(currentItem.getExpectedGroup());
    }

    @Override
    public int getItemCount() {
        return  mMail.size();
    }
}
