package com.example.conferenceapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class RoomAddDialog extends AppCompatDialogFragment {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    Global global;

    public RoomAddDialog(Global global){
        this.global=global;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View sendPopup = getLayoutInflater().inflate(R.layout.fragment_room_add_dialog, null);

        btn = sendPopup.findViewById(R.id.btnAddRoom);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarroomadded);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Snackbar snackbar = Snackbar.make(snackbar_layout, "Room Added!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        builder.setView(sendPopup);
        return builder.create();

    }
}