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

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.TimeZone;

public class EventAddDialog extends AppCompatDialogFragment {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    private TextInputLayout eventnameinput;
    private TextInputLayout eventcapacityinput;
    private TextInputLayout eventroominput;
    private Button btndatepicker;
    private Button btnStartTime;
    private Button btnEndTime;
    private Calendar eventstart;
    private Calendar eventend;
    Global global;
    EventDialogCallback callback;

    public EventAddDialog(Global global){
        this.global=global;
    }

    public EventAddDialog(Global global, EventDialogCallback callback){
        this.global=global;
        this.callback = callback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View sendPopup = getLayoutInflater().inflate(R.layout.event_add_modal, null);

        eventnameinput = sendPopup.findViewById(R.id.textFieldEventName);
        eventcapacityinput = sendPopup.findViewById(R.id.textFieldEventCapacity);
        eventroominput = sendPopup.findViewById(R.id.textInputEventRoom);
        btndatepicker = sendPopup.findViewById(R.id.btnShowDatePicker);
        btn = sendPopup.findViewById(R.id.btnAddEvent);
        btnStartTime = sendPopup.findViewById(R.id.btnShowStartTime);
        btnEndTime = sendPopup.findViewById(R.id.btnShowEndTime);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarEventadded);

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());


        MaterialDatePicker.Builder builder2 = MaterialDatePicker.Builder.datePicker();
        builder2.setTitleText("Select Date");
        builder2.setSelection(today);
        builder2.setCalendarConstraints(constraintBuilder.build());
        MaterialDatePicker materialDatePicker = builder2.build();

        btndatepicker.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "Date Picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                btndatepicker.setText(materialDatePicker.getHeaderText());

                eventstart = Calendar.getInstance();
                eventend = Calendar.getInstance();
                eventstart.setTimeInMillis((long) selection);
                eventend.setTimeInMillis((long) selection);
            }
        });








//CREATE EVENT
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String eventname = eventnameinput.getEditText().getText().toString();
                String eventcapacity = eventcapacityinput.getEditText().getText().toString();
                String eventroom = eventroominput.getEditText().getText().toString();


                if(null == null) {
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Event Added!", Snackbar.LENGTH_SHORT);
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

    public interface EventDialogCallback {
        void onDialogCallback();
    }
}