package com.example.conferenceapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import src.AttendeesAccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class AttendeeMessageDialog extends AppCompatDialogFragment {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    Global global;
    private TextInputLayout messageinput;


    public AttendeeMessageDialog(Global global){
        this.global=global;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View sendPopup = getLayoutInflater().inflate(R.layout.dialog_attendee_message, null);

        messageinput = sendPopup.findViewById(R.id.textFieldMessagesend);
        btn = sendPopup.findViewById(R.id.btnsendmessagetoall);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarmsgsentadded);


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = messageinput.getEditText().getText().toString();

                if (!message.isEmpty()) {
                    global.getTc().getOC().sendToAllAttendees(message);
                    //TODO: Fix snackbar
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Message Sent!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
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

}