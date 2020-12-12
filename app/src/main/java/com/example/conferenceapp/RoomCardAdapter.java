package com.example.conferenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomCardAdapter extends RecyclerView.Adapter<RoomCardAdapter.RoomCardViewHolder> {
    private ArrayList<RoomCard> mRoomList;
    private Context context;
    Global global;

    public static class RoomCardViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView capacityView;
        public Button deleteRoom;
        Context context;


        public RoomCardViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nameView = itemView.findViewById(R.id.roomTitle);
            capacityView = itemView.findViewById(R.id.roomCapacity);
            deleteRoom = itemView.findViewById(R.id.btnDeleteRoom);
            this.context = context;
        }
    }

    public RoomCardAdapter(Context context, ArrayList<RoomCard> roomlist, Global global) {
        this.context = context;
        mRoomList = roomlist;
        this.global = global;
    }

    @NonNull
    @Override
    public RoomCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomcard, parent, false);
        RoomCardViewHolder cvh = new RoomCardViewHolder(v, context);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomCardViewHolder holder, int position) {
        RoomCard currItem = mRoomList.get(position);

        holder.nameView.setText(currItem.getName());
        holder.capacityView.setText(String.valueOf(currItem.getCapacity()));
        holder.deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.getTc().getOC().deleteRoomFromSystem(currItem.getName());
                mRoomList.remove(currItem);

                notifyItemChanged(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }

    public void updateData(ArrayList<RoomCard> RoomCardList){
        mRoomList.clear();
        mRoomList.addAll(RoomCardList);
        notifyDataSetChanged();
    }


}
