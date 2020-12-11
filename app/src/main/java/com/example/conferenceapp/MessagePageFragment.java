package com.example.conferenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class MessagePageFragment extends Fragment{

    private FloatingActionButton fab;
    private RecyclerView rv;

    String items[] = new String[] {"1", "2"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_message_page, container, false);
        getActivity().setTitle("Messenger");
        fab = root.findViewById(R.id.fabSend);
        rv = root.findViewById(R.id.rvMsg);


//        MessageAdapter myAdapter = new MessageAdapter(getActivity(), items);
//        rv.setAdapter(myAdapter);
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
