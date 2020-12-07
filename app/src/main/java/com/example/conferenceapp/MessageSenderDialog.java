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

public class MessageSenderDialog extends AppCompatDialogFragment {
    private Button btn;
    private EditText un;
    private EditText msg;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View sendPopup = getLayoutInflater().inflate(R.layout.message_send_modal, null);

        EditText newpopup_username = sendPopup.findViewById(R.id.etNameSend);
        EditText newpopup_message = sendPopup.findViewById(R.id.etMsgSend);
        Button newpopup_button = sendPopup.findViewById(R.id.btnSendMsg);

        newpopup_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: Implement send message
            }
        });

        builder.setView(sendPopup);
        return builder.create();

    }
}
