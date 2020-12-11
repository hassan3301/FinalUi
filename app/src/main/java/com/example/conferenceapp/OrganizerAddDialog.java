package com.example.conferenceapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import src.AttendeesAccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class OrganizerAddDialog extends AppCompatDialogFragment {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    Global global;
    private TextInputLayout nameinput;
    private TextInputLayout usernameinput;
    private TextInputLayout passwordinput;
    DialogCallback callback;

    public OrganizerAddDialog(Global global, DialogCallback callback){
        this.global=global;
        this.callback = callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View sendPopup = getLayoutInflater().inflate(R.layout.fragment_attendee_add_dialog, null);

        nameinput = sendPopup.findViewById(R.id.textFieldAttendeeName);
        usernameinput = sendPopup.findViewById(R.id.textFieldAttendeeUsername);
        passwordinput = sendPopup.findViewById(R.id.textFieldAttendeePassword);
        btn = sendPopup.findViewById(R.id.btnAddAttendee);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarattendeeadded);
        TextView titleview = (TextView) sendPopup.findViewById(R.id.add_dialog_title);
        titleview.setText("Add Organizer");


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String attendeename = nameinput.getEditText().getText().toString();
                String attendeeusername = usernameinput.getEditText().getText().toString();
                String attendeepassword = passwordinput.getEditText().getText().toString();

                if (!attendeename.isEmpty() && !attendeeusername.isEmpty() && !attendeepassword.isEmpty()) {
                    global.getTc().getOC().createNewAccount(attendeeusername, attendeepassword);
                    //TODO: Fix snackbar
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Organizer Added!", Snackbar.LENGTH_SHORT);
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