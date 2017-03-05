package com.theironyard.novauc;


public class User {
    private String name;
    private String password;


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
}
