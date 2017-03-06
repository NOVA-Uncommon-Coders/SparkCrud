package com.theironyard.novauc;


public class Entry {
    int id;
    int replyId;
    String author;
    String text;

    public Entry(int id, int replyId, String author, String text) {
        this.id = id;
        this.replyId = replyId;
        this.author = author;
        this.text = text;
    }
}
