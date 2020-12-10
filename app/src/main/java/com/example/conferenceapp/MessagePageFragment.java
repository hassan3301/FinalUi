package com.example.conferenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import src.UserAccount;


public class MessagePageFragment extends Fragment{

    private FloatingActionButton fab;
    private RecyclerView rv;

    private FloatingActionButton fabAdd;
    private EditText from;
    private Button enter;

    ArrayList<String> s1 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_message_page, container, false);
        getActivity().setTitle("Messenger");
        fab = root.findViewById(R.id.fabSend);
        rv = root.findViewById(R.id.rvMsg);

        //Global global = (Global) root.getApplicationContext();
        Global global = new Global();

        fab = root.findViewById(R.id.fabSend);
        fabAdd = root.findViewById(R.id.fabAddMessenger);
        rv = root.findViewById(R.id.rvMsg);
        from = root.findViewById(R.id.etFrom);
        enter = root.findViewById(R.id.btnFromEnter);

        String userFrom = from.getText().toString();
        s1 = UserAccount.getUnToAttendee().get(global.getTc().getAc().username).getMessages_received(userFrom);


        MessageAdapter myAdapter = new MessageAdapter(getActivity(), s1, global, userFrom);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return root;
    }

    public void openDialog() {
        MessageSenderDialog msd = new MessageSenderDialog();
        msd.show(getChildFragmentManager(), "message sender dialog");
    }
}
