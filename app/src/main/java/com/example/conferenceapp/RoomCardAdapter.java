package com.example.conferenceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomCardAdapter extends RecyclerView.Adapter<RoomCardAdapter.RoomCardViewHolder> {
    private ArrayList<RoomCard> mRoomList;

    public static class RoomCardViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView capacityView;

        public RoomCardViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.roomTitle);
            capacityView = itemView.findViewById(R.id.roomCapacity);

        }
    }

    public RoomCardAdapter(ArrayList<RoomCard> roomlist) {
        mRoomList = roomlist;
    }

    @NonNull
    @Override
    public RoomCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomcard, parent, false);
        RoomCardViewHolder cvh = new RoomCardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomCardViewHolder holder, int position) {
        RoomCard currItem = mRoomList.get(position);

        holder.nameView.setText(currItem.getName());
        holder.capacityView.setText(currItem.getCapacity());

    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }
}
