package com.example.conferenceapp;

import src.User;

public class AccountCard {
    private String name;
    private String username;

    public AccountCard(User u){
        this.name = u.getName();
        this.username = u.getUsername();
    }

    public String getName(){
        return name;
    }

    public String getUsername(){return username; }

}
