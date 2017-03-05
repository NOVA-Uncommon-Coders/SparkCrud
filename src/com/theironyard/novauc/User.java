package com.theironyard.novauc;


import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private ArrayList<String> aVector = new ArrayList<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }

    ArrayList getAvector() {
        return aVector;
    }
}
