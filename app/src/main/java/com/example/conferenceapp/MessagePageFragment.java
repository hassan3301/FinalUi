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
    private FloatingActionButton fabAdd;
    public EditText from;
    private Button enter;
    ArrayList<String> s1 = new ArrayList<>();
    Global global;

    public MessagePageFragment(Global global){
        this.global = global;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_message_page, container, false);
        getActivity().setTitle("Messenger");


        fab = root.findViewById(R.id.fabSend);
        fabAdd = root.findViewById(R.id.fabAddMessenger);

        from = root.findViewById(R.id.etFrom);
        enter = root.findViewById(R.id.btnFromEnter);

        String userFrom = from.getText().toString();


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendeeViewMessagePage.class);
                intent.putExtra("userFrom", userFrom);
                startActivity(intent);

            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageSendPage.class);
                startActivity(intent);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMessengerPage.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void openDialog() {
        MessageSenderDialog msd = new MessageSenderDialog();
        msd.show(getChildFragmentManager(), "message sender dialog");
    }
}
