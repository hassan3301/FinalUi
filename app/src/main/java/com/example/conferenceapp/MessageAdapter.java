package com.example.conferenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.Message;
import src.UserAccount;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    ArrayList<String> map;
    Context context;

    public MessageAdapter(Context ct, ArrayList<String> map){
        this.map = map;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //TODO: modify for Messages
        holder.message.setText(UserAccount.idToMessage.get(map.get(position)).getText());
        holder.user.setText(UserAccount.);
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        TextView message;
        TextView time;
        Button btnDelete;
        Button btnArchive;
        Button btnReply;
        CheckBox cb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.textView8);
            message = itemView.findViewById(R.id.textView12);
            time = itemView.findViewById(R.id.textView13);
            btnDelete = itemView.findViewById(R.id.btnDlt);
            btnArchive = itemView.findViewById(R.id.btnArchive);
            btnReply = itemView.findViewById(R.id.btnReply);
            cb = itemView.findViewById(R.id.checkBox);
        }
    }

}
