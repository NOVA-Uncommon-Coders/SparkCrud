package com.theironyard.novauc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by souporman on 3/2/17.
 */
public class User {
    private String username;
    private String password;
    List<Food> foodList = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
