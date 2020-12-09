package com.example.conferenceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import src.Attendee;
import src.Message;
import src.UserAccount;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    ArrayList<String> s1;
    Context context;
    Global global;
    Attendee a;
    String from;



    public MessageAdapter(Context ct, ArrayList<String> s1, Global global, String from){
        this.s1 = s1;
        this.context = ct;
        this.global = global;
        a = UserAccount.getUnToAttendee().get(global.getUn());
        this.from = from;

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

        holder.user.setText(UserAccount.getIdToMessage().get((a.getMessages_received(from).get(position))).toString());
        holder.message.setText(UserAccount.getIdToMessage().get(s1.get(position)).toString());

        holder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageSendPage.class);
                context.startActivity(intent);
            }
        });
        holder.btnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.getTc().getAc().archiveMessage(s1.get(position));
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAccount.getIdToMessage().remove(s1.get(position));
            }
        });
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                global.getTc().getAc().markMessageUnread(s1.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return s1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        TextView message;
        Button btnDelete;
        Button btnArchive;
        Button btnReply;
        CheckBox cb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.textView8);
            message = itemView.findViewById(R.id.textView12);
            btnDelete = itemView.findViewById(R.id.btnDlt);
            btnArchive = itemView.findViewById(R.id.btnArchive);
            btnReply = itemView.findViewById(R.id.btnReply);
            cb = itemView.findViewById(R.id.checkBox);
        }
    }

}
