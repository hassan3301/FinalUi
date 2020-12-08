package com.example.conferenceapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_page);
        Toolbar toolbar = findViewById(R.id.toolbar4_organizer);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_organizer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.open_nav_drawer, R.string.close_nav_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_rooms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventsListFragment()).commit();
                break;
            case R.id.nav_accounts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessagePageFragment()).commit();
                break;
            case R.id.nav_organizerproofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_myeevents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventsListFragment()).commit();
                break;
            case R.id.nav_organizermessenger:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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