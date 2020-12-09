package com.example.conferenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class OrganizerPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private String username;
    private String accounttype = "OrganizerAccount";
    public Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_page);
        Toolbar toolbar = findViewById(R.id.toolbar4_organizer);
        setSupportActionBar(toolbar);
        global = (Global) getApplicationContext();

        Intent intent_prev = getIntent();
        username = intent_prev.getStringExtra("user_name");

        drawer = findViewById(R.id.drawer_layout_organizer);
        NavigationView navigationView = findViewById(R.id.nav_view_organizer);
        navigationView.setNavigationItemSelectedListener(this);

        View navbar_header_view = navigationView.getHeaderView(0);
        TextView navbar_username = navbar_header_view.findViewById(R.id.navbar_user_display);
        navbar_username.setText(getString(R.string.welcome, username));
        TextView navbar_account = navbar_header_view.findViewById(R.id.navbar_accounttype);
        navbar_account.setText(accounttype);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.open_nav_drawer, R.string.close_nav_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                    new RoomsFragment(global)).commit();
                navigationView.setCheckedItem(R.id.nav_rooms);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_rooms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                        new RoomsFragment(global)).commit();
                break;
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                        new SchedulerFragment(global)).commit();
                break;
            case R.id.nav_accounts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                        new AccountsFragment()).commit();
                break;
            case R.id.nav_organizerproofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_myeevents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                        new EventsListFragment(global)).commit();
                break;
            case R.id.nav_organizermessenger:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_organizer,
                        new MessagePageFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}