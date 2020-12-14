package com.example.conferenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import src.Event;

public class EventSignUpAdapter extends RecyclerView.Adapter<EventSignUpAdapter.MyViewHolder> {

    ArrayList<Event> s1;
    Context context;
    Global global;

    public EventSignUpAdapter(Context ct, ArrayList<Event> s1, Global global){
        this.s1 = s1;
        this.context = ct;
        this.global = global;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_event_signup, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.event.setText(s1.get(position).toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.getTc().getAc().callSignUp(global.getUn(), s1.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return s1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView event;
        Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.tvEventSignUp);
            btn = itemView.findViewById(R.id.btnSignUpAdd);
        }
    }

}