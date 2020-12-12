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

public class EventEditDialog extends AppCompatDialogFragment {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    Global global;
    private TextInputLayout addspeaker;
    private TextInputLayout changecapacity;
    private String eventstring;
    DialogCallback callback;




    public EventEditDialog(Global global, String eventname, EventEditDialog.DialogCallback callback){
        this.global=global;
        this.eventstring = eventname;
        this.callback = callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View sendPopup = getLayoutInflater().inflate(R.layout.dialog_event_edit, null);

        addspeaker = sendPopup.findViewById(R.id.textFieldAddSpeaker);
        changecapacity = sendPopup.findViewById(R.id.textFieldChangeCapacity);
        btn = sendPopup.findViewById(R.id.btnEditEvent);
        snackbar_layout = sendPopup.findViewById(R.id.snackbareventchanged);


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String adds = addspeaker.getEditText().getText().toString();
                String newcap = changecapacity.getEditText().getText().toString();

                if (!adds.isEmpty()) {
                    global.getTc().getOC().addSpeakerToEvent(eventstring, adds);
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Speaker Added!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
                if(!newcap.isEmpty() && newcap.matches("\\d+")){
                    global.getTc().getOC().changeEventCapacity(eventstring, newcap);
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Capacity changed!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                callback.onDialogCallback();
                getDialog().dismiss();
            }
        });

        builder.setView(sendPopup);
        return builder.create();

    }

    public interface DialogCallback {
        void onDialogCallback();
    }

}