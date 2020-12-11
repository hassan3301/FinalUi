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
import com.google.android.material.textfield.TextInputLayout;

public class RoomAddDialog extends AppCompatDialogFragment {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    Global global;
    private TextInputLayout roomnameinput;
    private TextInputLayout capacityinput;
    DialogCallback callback;

    public RoomAddDialog(Global global, DialogCallback callback){
        this.global=global;
        this.callback = callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View sendPopup = getLayoutInflater().inflate(R.layout.fragment_room_add_dialog, null);

        roomnameinput = sendPopup.findViewById(R.id.textFieldRoomName);
        capacityinput = sendPopup.findViewById(R.id.textFieldCapacity);
        btn = sendPopup.findViewById(R.id.btnAddRoom);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarroomadded);


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String roomname = roomnameinput.getEditText().getText().toString();
                String capacity = capacityinput.getEditText().getText().toString();

                if (!roomname.isEmpty() && capacity.matches("\\d+")) {
                    global.getTc().getOC().enterRoomIntoSystem(roomname);
                    global.getTc().getOC().setRoomCapacity(roomname, capacity);
                    //TODO: Fix snackbar
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Room Added!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    callback.onDialogCallback();
                    getDialog().dismiss();
                }
                else{
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Invalid Input", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        builder.setView(sendPopup);
        return builder.create();

    }

    public interface DialogCallback {
        void onDialogCallback();
    }
}