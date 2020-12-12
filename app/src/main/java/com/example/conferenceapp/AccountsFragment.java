package com.example.conferenceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AccountsFragmentAttendee attendeefrag;
    private AccountsFragmentOrganizer organizerfrag;
    private AccountsFragmentSpeaker speakerfrag;
    Global global;

    public AccountsFragment(){

    }

    public AccountsFragment(Global global){
        this.global = global;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Accounts");

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_accounts, container, false);

        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tab_layout);
        attendeefrag = new AccountsFragmentAttendee(global);
        organizerfrag = new AccountsFragmentOrganizer(global);
        speakerfrag = new AccountsFragmentSpeaker(global);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(attendeefrag, "Attendee");
        adapter.addFragment(organizerfrag, "Organizer");
        adapter.addFragment(speakerfrag, "Speaker");

        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
