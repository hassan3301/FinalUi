package com.example.conferenceapp;

import android.accounts.Account;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import src.Attendee;
import src.Room;
import src.User;
import src.UserAccount;
import src.VIPAttendee;

public class AccountsFragmentAttendee extends Fragment {

    private RecyclerView recyclerView;
    private AccountsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAddUser;
    private FloatingActionButton messageall;
    Global global;

    public AccountsFragmentAttendee(Global global){
        this.global = global;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_accounts_attendee, container, false);
        ArrayList<User> AccountArrayList = new ArrayList<User>(UserAccount.getUnToAttendee().values());
        ArrayList<User> vipArrayList = new ArrayList<User>(UserAccount.getUnToVip().values());
        AccountArrayList.addAll(vipArrayList);
        ArrayList<AccountCard> AccountCardArrayList = getAccountList(AccountArrayList);


        fabAddUser = root.findViewById(R.id.fabAddattendee);
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        messageall = root.findViewById(R.id.fabMessageAll);
        messageall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMessageDialog();
            }
        });


        //Testing

        //Testing



        recyclerView = root.findViewById(R.id.attendee_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new AccountsAdapter(getActivity(), AccountCardArrayList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    public void openDialog() {
        AttendeeAddDialog aad = new AttendeeAddDialog(global, new AttendeeAddDialog.DialogCallback() {
            @Override
            public void onDialogCallback() {
                System.out.println("CALLBACK");
                ArrayList<User> AccountArrayList = new ArrayList<User>(UserAccount.getUnToAttendee().values());
                ArrayList<User> vipArrayList = new ArrayList<User>(UserAccount.getUnToVip().values());
                AccountArrayList.addAll(vipArrayList);
                adapter.updateData(getAccountList(AccountArrayList));
            }
        });
        aad.show(getChildFragmentManager(), "roomadd dialog");
    }

    public void openMessageDialog(){
        AttendeeMessageDialog amd = new AttendeeMessageDialog(global);
        amd.show(getChildFragmentManager(), "roomadd dialog");
    }

    public static ArrayList<AccountCard> getAccountList(ArrayList<User> userlist){
        ArrayList<AccountCard> returnlist = new ArrayList<AccountCard>();

        //TESTING

        //TESTING

        for(User u: userlist){
            AccountCard a = new AccountCard(u);
            if(u instanceof VIPAttendee){
                a.makeVip();
            }
            returnlist.add(a);
        }
        return returnlist;
    }

}