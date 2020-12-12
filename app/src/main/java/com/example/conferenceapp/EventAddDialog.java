package com.example.conferenceapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import me.tittojose.www.timerangepicker_library.TimeRangePickerDialog;

public class EventAddDialog extends AppCompatDialogFragment implements TimeRangePickerDialog.OnTimeRangeSelectedListener {
    private Button btn;
    private CoordinatorLayout snackbar_layout;
    private TextInputLayout eventnameinput;
    private TextInputLayout eventcapacityinput;
    private TextInputLayout eventroominput;
    private TextInputLayout eventspeakerinput;
    private TextInputLayout eventdescriptioninput;
    private TextInputLayout eventtypeinput; //TODO: if time, change to dropdown
    private Button btndatepicker;
    private Button btnStartTime;
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
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View sendPopup = View.inflate(getActivity(), R.layout.event_add_modal, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());

        //View sendPopup = getLayoutInflater().inflate(R.layout.event_add_modal, null);

        eventnameinput = sendPopup.findViewById(R.id.textFieldEventName);
        eventcapacityinput = sendPopup.findViewById(R.id.textFieldEventCapacity);
        eventroominput = sendPopup.findViewById(R.id.textInputEventRoom);
        eventspeakerinput = sendPopup.findViewById(R.id.textInputEventSpeaker);
        eventdescriptioninput = sendPopup.findViewById(R.id.textInputEventDescription);
        eventtypeinput = sendPopup.findViewById(R.id.textInputEventType);
        btndatepicker = sendPopup.findViewById(R.id.btnShowDatePicker);
        btn = sendPopup.findViewById(R.id.btnAddEvent);
        btnStartTime = sendPopup.findViewById(R.id.btnShowStartTime);
        snackbar_layout = sendPopup.findViewById(R.id.snackbarEventadded);

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        //date picker
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

        //time picker
        btnStartTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final TimeRangePickerDialog timePickerDialog = TimeRangePickerDialog.newInstance(
                         EventAddDialog.this, false);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), "timerangepicker");
            }
        });



//CREATE EVENT
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String eventname = eventnameinput.getEditText().getText().toString();
                String eventcapacity = eventcapacityinput.getEditText().getText().toString();
                String eventroom = eventroominput.getEditText().getText().toString();
                String eventspeakerinputtext = eventspeakerinput.getEditText().getText().toString();
                String eventdescription = eventdescriptioninput.getEditText().getText().toString();
                String eventtype = eventtypeinput.getEditText().getText().toString();

                String[] speakerarray = new String[1];
                speakerarray[0] = eventspeakerinputtext;


                if(!eventname.isEmpty() && !eventcapacity.isEmpty() && !eventroom.isEmpty() &&
                        eventstart != null && eventend != null){
                    if(global.getTc().getOC().scheduleSpeaker(eventname, speakerarray, eventstart,
                            eventend, eventroom, eventdescription, eventtype, Integer.valueOf(eventcapacity))) {
                        Snackbar snackbar = Snackbar.make(snackbar_layout, "Event Added!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        callback.onDialogCallback();
                        getDialog().dismiss();
                    }
                    else{
                        Snackbar snackbar = Snackbar.make(snackbar_layout, "Could not be created", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
                else{
                    Snackbar snackbar = Snackbar.make(snackbar_layout, "Invalid Input", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        dialog.setContentView(sendPopup);
        dialog.show();
        return dialog;

        //builder.setView(sendPopup);
        //return builder.create();



    }


    @Override
    public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
        String startminstring = "";
        String endminstring = "";
        if(startMin < 10){
            startminstring = "0" + startMin;
        }
        else{
            startminstring = Integer.toString(startMin);
        }

        if(endMin < 10){
            endminstring = "0" + endMin;
        }
        else{
            endminstring = Integer.toString(endMin);
        }

        btnStartTime.setText(startHour + ":" + startminstring + " - " + endHour + ":" + endminstring);
        eventstart.set(Calendar.HOUR_OF_DAY, startHour);
        eventstart.set(Calendar.MINUTE, startMin);
        eventend.set(Calendar.HOUR_OF_DAY, endHour);
        eventend.set(Calendar.MINUTE, endMin);

    }

    public interface EventDialogCallback {
        void onDialogCallback();
    }
}