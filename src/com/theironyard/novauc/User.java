package com.theironyard.novauc;

import java.util.ArrayList;

/**
 * Created by jerieshasmith on 3/5/17.
 */
public class User {
    static ArrayList<Movie> movies = new ArrayList<>();
    int id;
    String name;
    String password;

    public User() {
    }

    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }
}
