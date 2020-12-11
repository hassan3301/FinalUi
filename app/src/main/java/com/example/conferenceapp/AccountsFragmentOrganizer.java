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
import src.Organizer;
import src.User;
import src.UserAccount;

public class AccountsFragmentOrganizer extends Fragment {

    private RecyclerView recyclerView;
    private AccountsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAddUser;
    private FloatingActionButton messageall;
    Global global;

    public AccountsFragmentOrganizer(Global global){
        this.global = global;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_accounts_attendee, container, false);
        ArrayList<User> AccountArrayList = new ArrayList<User>(UserAccount.getUnToOrganizer().values());
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
        OrganizerAddDialog oad = new OrganizerAddDialog(global, new OrganizerAddDialog.DialogCallback() {
            @Override
            public void onDialogCallback() {
                System.out.println("CALLBACK");
                adapter.updateData(getAccountList(new ArrayList<>(UserAccount.getUnToOrganizer().values())));
            }
        });
        oad.show(getChildFragmentManager(), "organizeradd dialog");
    }

    public void openMessageDialog(){
        OrganizerMessageDialog omd = new OrganizerMessageDialog(global);
        omd.show(getChildFragmentManager(), "speakeradd dialog");
    }

    public static ArrayList<AccountCard> getAccountList(ArrayList<User> userlist){
        ArrayList<AccountCard> returnlist = new ArrayList<AccountCard>();

        //TESTING

        //TESTING

        for(User u: userlist){
            returnlist.add(new AccountCard(u));
        }
        return returnlist;
    }

}