package com.example.conferenceapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;

import kotlin.reflect.KClass;
import src.AttendeeController;
import src.Event;
import src.OrganizerController;
import src.SpeakerController;
import src.TechConferenceController;
import src.User;
import src.UserAccount;
import src.VIPAttendeeController;

public class MainActivity extends AppCompatActivity {

    public EditText userName;
    public EditText password;
    private Button logIn;
    private Button signUp;
    public static AttendeeController ac;
    public static AttendeeController ac2;
    public TechConferenceController tc;

    private boolean isValid = false;
    private boolean attendee = false;
    private boolean speaker = false;
    private boolean organizer = false;
    private boolean VIP = false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Global global = (Global) getApplicationContext();
        tc = new TechConferenceController();
        global.setTc(tc);

        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        logIn = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignUp);

        //TEST ACCOUNTS
        ac = new AttendeeController("h");
        ac.createNewAccount("h", "h");
        ac2 = new AttendeeController("a2");
        ac2.createNewAccount("a2", "a2");


        //Event e = new Event("Apple Event", "Room A", "Steve Jobs", "Unveiling Iphone30", 12, 13);
        //UserAccount.unToAttendee.get("h").addEvent();
        OrganizerController oc = new OrganizerController("j");
        oc.createNewAccount("j", "j");
        SpeakerController scon = new SpeakerController("s");
        scon.createNewAccount("s", "s");
        //TEST ACCOUNTS

        logIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String inputName = userName.getText().toString();
                String inputPassword = password.getText().toString();
                global.setUn(inputName);

                isValid = validate(inputName, inputPassword);

                if (!isValid){
                    Toast.makeText(MainActivity.this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                }
                else if (attendee || VIP){
                    Toast.makeText(MainActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();

                    if (VIP){
                        VIPAttendeeController vac = new VIPAttendeeController(inputName);
                        global.getTc().setVac(vac);
                    }
                    else {
                        AttendeeController ac = new AttendeeController(inputName);
                        global.getTc().setAc(ac);
                    }
                    try {
                        global.getTc().login(inputName, inputPassword);
                        System.out.println("y");
                    }
                    catch (IOException io){
                        System.out.println("x");
                    }
                    catch(ClassNotFoundException cnf){
                        System.out.println("x");
                    }

                    Intent intent = new Intent(MainActivity.this, AttendeePage.class);
                    intent.putExtra("user_name", inputName);
                    intent.putExtra("VIP", VIP);
                    startActivity(intent);
                }
                else if (speaker){
                    try {
                        global.getTc().login(inputName, inputPassword);
                        System.out.println("y");
                    }
                    catch (IOException io){
                        System.out.println("x");
                    }
                    catch(ClassNotFoundException cnf){
                        System.out.println("x");
                    }
                    Toast.makeText(MainActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SpeakerPage.class);
                    intent.putExtra("user_name", inputName);
                    startActivity(intent);
                    SpeakerController scon = new SpeakerController(inputName);
                    global.getTc().setScon(scon);
                }
                else {
                    try {
                        global.getTc().login(inputName, inputPassword);
                        System.out.println("y");
                    }
                    catch (IOException io){
                        System.out.println("x");
                    }
                    catch(ClassNotFoundException cnf){
                        System.out.println("x");
                    }
                    Toast.makeText(MainActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, OrganizerPage.class);
                    intent.putExtra("user_name", inputName);
                    startActivity(intent);
                    OrganizerController oc = new OrganizerController(inputName);
                    global.getTc().setOc(oc);
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
    private boolean validate(String name, String password){

        if (UserAccount.getUnToAttendee().containsKey(name) && password.equals(UserAccount.getUnToAttendee().get(name).getPassword())){
            attendee = true;
            return true;
        }
        else if (UserAccount.getUnToVip().containsKey(name) && password.equals(UserAccount.getUnToVip().get(name).getPassword())){
            VIP = true;
        }
        else if (UserAccount.getUnToSpeaker().containsKey(name) && password.equals(UserAccount.getUnToSpeaker().get(name).getPassword()))
        {
            speaker = true;
            return true;
        }
        else if (UserAccount.getUnToOrganizer().containsKey(name) && password.equals(UserAccount.getUnToOrganizer().get(name).getPassword()))
        {
            organizer = true;
            return true;
        }

        return false;
    }

}