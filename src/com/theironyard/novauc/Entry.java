package com.theironyard.novauc;


public class Entry {
    public static int i = 0;
    private String author;
    private String text;
    private int id;

    public Entry() {
    }

    public Entry( String author, String text) {
        //this.id = id;
        this.author = author;
        this.text = text;
        id = i++;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        Entry.i = i;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }
}
