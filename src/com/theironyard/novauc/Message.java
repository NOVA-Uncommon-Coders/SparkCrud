package com.theironyard.novauc;


public class Message {
private static int nextId = 1;
    int midentifier;
    String text;


    public Message(String text) {

        this.text = text;
        this.midentifier = nextId;
        nextId++;

    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Message.nextId = nextId;
    }

    public int getMidentifier() {
        return midentifier;
    }

    public void setMidentifier(int midentifier) {
        this.midentifier = midentifier;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}