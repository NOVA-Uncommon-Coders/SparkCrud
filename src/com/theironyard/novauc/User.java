package com.theironyard.novauc;

import java.util.ArrayList;

/**
 * Created by Eric on 3/9/17.
 */
public class User {

    String name;
    String password;
    ArrayList<Message> messageList = new ArrayList();

    public User(String name, String password, ArrayList messageList) {

        this.name = name;
        this.password = password;
        this.messageList = messageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }
}
