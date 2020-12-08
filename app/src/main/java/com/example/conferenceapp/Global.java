package com.example.conferenceapp;

import android.app.Application;

import src.TechConferenceController;

public class Global extends Application {

    private TechConferenceController tc;
    private String un;

    public TechConferenceController getTc(){
        return tc;
    }

    public void setTc(TechConferenceController tc){
        this.tc = tc;
    }

    public String getUn(){
        return un;
    }

    public void setUn(String un){
        this.un = un;
    }

}
