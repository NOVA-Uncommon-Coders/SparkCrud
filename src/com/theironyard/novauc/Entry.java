package com.theironyard.novauc;


public class Entry {
    private int id;
    private int replyId;
    private String author;
    private String text;

    private static int incrementer = 0;
    //public final int identifier;

    public Entry(int id, int replyId, String author, String text) {
        this.id = id;
        this.replyId = incrementer;
        this.author = author;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getReplyId() {
        return replyId;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
