package com.example.conferenceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.CardViewHolder> {
    private ArrayList<EventCard> mEventList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView descriptionView;
        public TextView speakernameView;
        public TextView timeView;
        public TextView roomView;

        public CardViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.eventTitle);
            descriptionView = itemView.findViewById(R.id.eventDescription);
            speakernameView = itemView.findViewById(R.id.eventSpeaker);
            timeView = itemView.findViewById(R.id.eventTime);
            roomView = itemView.findViewById(R.id.eventRoom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public EventCardAdapter(ArrayList<EventCard> eventList) {
        mEventList = eventList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eventcard, parent, false);
        CardViewHolder cvh = new CardViewHolder(v, mListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        EventCard currItem = mEventList.get(position);

        holder.titleView.setText(currItem.getTitle());
        holder.descriptionView.setText(currItem.getDescription());
        holder.speakernameView.setText(currItem.getSpeakername()[0]);
        holder.timeView.setText(currItem.getTime());
        holder.roomView.setText(currItem.getRoomlocation());
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public void updateData(ArrayList<EventCard> EventCardList){
        mEventList.clear();
        mEventList.addAll(EventCardList);
        notifyDataSetChanged();
    }
}
