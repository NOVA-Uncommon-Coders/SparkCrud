package com.theironyard.novauc;

import java.util.ArrayList;

/**
 * Created by jerieshasmith on 3/5/17.
 */
public class User {
    static ArrayList<Movie> movies = new ArrayList<>();

    String name;
    String password;


    public User(String name, String password){
        this.name = name;
        this.password = password;

    }

}
