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

public class MessageSenderDialog extends AppCompatDialogFragment {
    private Button btn;
    private EditText un;
    private EditText msg;
    private CoordinatorLayout snackbar_layout;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View sendPopup = getLayoutInflater().inflate(R.layout.message_send_modal, null);

        un = sendPopup.findViewById(R.id.etNameSend);
        msg = sendPopup.findViewById(R.id.etMsgSend);
        btn = sendPopup.findViewById(R.id.btnSendMsg);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarlayout);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: Implement send message
                Snackbar snackbar = Snackbar.make(snackbar_layout, "Message Sent!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        builder.setView(sendPopup);
        return builder.create();

    }
}
