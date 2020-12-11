package com.example.conferenceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ProfileFragment extends Fragment {
    public TextView nameview;
    public TextView accounttypeview;
    public String username;
    public String accounttype;
    Global global;

    public ProfileFragment(Global global, String accounttype, String username){
        this.global = global;
        this.accounttype = accounttype;
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");


        TextView nameview = root.findViewById(R.id.profile_username);
        nameview.setText(username);
        TextView accounttypeview = root.findViewById(R.id.profile_accounttype);
        accounttypeview.setText(accounttype);


        return root;
    }
}
