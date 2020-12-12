package com.example.conferenceapp;

import src.User;

public class AccountCard {
    private String name;
    private String username;
    private boolean vip;

    public AccountCard(User u){
        this.name = u.getName();
        this.username = u.getUsername();
        this.vip = false;
    }

    public String getName(){
        return name;
    }

    public String getUsername(){return username; }

    public boolean isVip(){return vip;}

    public void makeVip(){
        this.vip = true;
    }

}
