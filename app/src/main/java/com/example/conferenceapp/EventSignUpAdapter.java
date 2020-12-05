package com.example.conferenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventSignUpAdapter extends RecyclerView.Adapter<EventSignUpAdapter.MyViewHolder> {

    String s1[];
    Context context;

    public EventSignUpAdapter(Context ct, String s1[]){
        this.s1 = s1;
        this.context = ct;

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
        //TODO: modify for events
        holder.title.setText(s1[position]);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView speaker;
        TextView time;
        Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvSignUpTitle);
            description = itemView.findViewById(R.id.tvSignUpDescription);
            speaker = itemView.findViewById(R.id.tvSignUpSpeaker);
            time = itemView.findViewById(R.id.tvSignUpTime);
            btn = itemView.findViewById(R.id.btnSignUpAdd);
        }
    }

}