package com.example.conferenceapp;

import android.accounts.Account;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.Distribution;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import src.User;
import src.UserAccount;
import src.VIPAttendee;

public class EventViewBottomSheet extends AppCompatDialogFragment {
    private RecyclerView recyclerViewSpeaker;
    private RecyclerView recyclerViewAttendee;
    private AccountsAdapter adapter;
    private AccountsAdapter adapterspeaker;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerSpeaker;
    private TextView eventname;
    private TextView eventtype;
    private String eventstring;
    private String[] speakerlist;
    private Button editevent;
    Global global;

    public EventViewBottomSheet(Global global, String eventname, String[] speakerlist){
        this.global = global;
        this.eventstring = eventname;
        this.speakerlist = speakerlist;
    }


    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View root = View.inflate(getActivity(), R.layout.bottomsheet_eventitem, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());

        eventname = root.findViewById(R.id.event_name_bottom);
        eventtype = root.findViewById(R.id.event_type_bottom);
        editevent = root.findViewById(R.id.button_edit_event);

        eventname.setText(eventstring);
        eventtype.setText(global.getTc().getEm().getEventList().get(eventstring).getType());

        editevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        ArrayList<User> AccountArrayList = new ArrayList<User>();
        ArrayList<String> Accountkeys = global.getTc().getEm().getEventAttendees(eventstring);
        for(String un : Accountkeys){
            if(UserAccount.getUnToAttendee().containsKey(un)) {
                AccountArrayList.add(UserAccount.getUnToAttendee().get(un));
            }
            else if (UserAccount.getUnToAttendee().containsKey(un)){
                AccountArrayList.add(UserAccount.getUnToVip().get(un));
            }
        }
        ArrayList<AccountCard> AccountCardArrayList = AccountsFragmentAttendee.getAccountList(AccountArrayList);

        ArrayList<User> SpeakerArrayList = new ArrayList<User>();
        for(String un : speakerlist){
            if(UserAccount.getUnToSpeaker().containsKey(un)){
                SpeakerArrayList.add(UserAccount.getUnToSpeaker().get(un));
            }
        }
        ArrayList<AccountCard> SpeakerCardArrayList = AccountsFragmentSpeaker.getAccountList(SpeakerArrayList);


        recyclerViewAttendee = root.findViewById(R.id.event_attendees_recyclerview);
        recyclerViewSpeaker = root.findViewById(R.id.event_speakers_recyclerview);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManagerSpeaker = new LinearLayoutManager(getActivity());

        recyclerViewAttendee.setLayoutManager(layoutManager);
        recyclerViewSpeaker.setLayoutManager(layoutManagerSpeaker);

        adapter = new AccountsAdapter(getActivity(), AccountCardArrayList);
        recyclerViewAttendee.setAdapter(adapter);
        adapterspeaker = new AccountsAdapter(getActivity(), SpeakerCardArrayList);
        recyclerViewSpeaker.setAdapter(adapterspeaker);

        dialog.setContentView(root);
        dialog.show();
        return dialog;
    }

    public void openDialog() {
        EventEditDialog eed = new EventEditDialog(global, eventstring, new EventEditDialog.DialogCallback() {
            @Override
            public void onDialogCallback() {
                System.out.println("CALLBACK");
                ArrayList<User> SpeakerArrayList = new ArrayList<User>();
                String[] updatespeakerlist = global.getTc().getEm().getEventSpeaker(eventstring);
                for(String un : updatespeakerlist){
                    SpeakerArrayList.add(UserAccount.getUnToSpeaker().get(un));
                }
                ArrayList<AccountCard> SpeakerCardArrayList = AccountsFragmentSpeaker.getAccountList(SpeakerArrayList);
                adapterspeaker.updateData(SpeakerCardArrayList);
            }
        });

        eed.show(getChildFragmentManager(), "eventadder dialog");
    }


}
