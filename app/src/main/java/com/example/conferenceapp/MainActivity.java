package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import src.*;

public class MainActivity extends AppCompatActivity {
    public EditText userName;
    public static String CurrentUser;
    public EditText password;
    private Button logIn;
    private Button signUp;
    public static AttendeeController ac;
    public static UserController uc;
    public TechConferenceController tc;

    private String atUnTest = "attendeetest";
    private String atPwTest = "a1";
    private String spUnTest = "speakertest";
    private String spPwTest = "s1";
    private String ogUnTest = "organizertest";
    private String ogPwTest = "o1";

    private boolean isValid = false;
    private boolean attendee = false;
    private boolean speaker = false;
    private boolean organizer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        logIn = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignUp);
        System.out.println(FirebaseGateway.ReadFromDB("TCC", "Events"));

        tc = new TechConferenceController();
        ac = new AttendeeController("h");
        ac.createNewAccount("h", "h");
        FirebaseGateway.WriteToDB("TCC", UserAccount.unToAttendee, "Events");
        //Event e = new Event("Apple Event", "Room A", "Steve Jobs", "Unveiling Iphone30", 12, 13);
        //UserAccount.unToAttendee.get("h").addEvent();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = userName.getText().toString();
                CurrentUser = userName.getText().toString();
                String inputPassword = password.getText().toString();

                isValid = validate(inputName, inputPassword);

                if (!isValid){
                    Toast.makeText(MainActivity.this, "Incorrect username or password.", Toast.LENGTH_SHORT).show();
                }
                else if (attendee){
                    Toast.makeText(MainActivity.this, "Login was successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AttendeePage.class);
                    startActivity(intent);
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
        if (UserAccount.unToAttendee.containsKey(name) && password.equals(UserAccount.unToAttendee.get(name).getPassword())){
            attendee = true;
            return true;
        }
        else if (UserAccount.unToSpeaker.containsKey(name) && password.equals(UserAccount.unToSpeaker.get(name).getPassword()))
        {
            speaker = true;
            return true;
        }
        else if (UserAccount.unToOrganizer.containsKey(name) && password.equals(UserAccount.unToOrganizer.get(name).getPassword()))
        {
            organizer = true;
            return true;
        }

        return false;
    }
    private void populate_maps(ArrayList<User> array) throws IOException, ClassNotFoundException {
        for (User o : array){
            if(o instanceof Attendee){
                UserAccount.unToAttendee.put(o.getName(), (Attendee) o);
            }
            else if(o instanceof Speaker){
                UserAccount.unToSpeaker.put(o.getName(), (Speaker) o);
            }
            else if(o instanceof Organizer){
                UserAccount.unToOrganizer.put(o.getName(), (Organizer) o);
            }
        }
//        for(Event e : EventArray){
//            EventManager.EventList.put(e.getName(), e);
//        }
//        for(Message m: MessagesArray){
//            UserAccount.idToMessage.put(m.getId(), m);
//        }

    }

}