package com.theironyard.novauc;

public class Entry {
    private int entryId;
    private String entryName;
    private String entryUserName;
    private int entryNumber;

    public Entry(int entryId, String entryName, String entryUserName, int entryNumber){
        this.entryId = entryId;
        this.entryName = entryName;
        this.entryUserName = entryUserName;
        this.entryNumber = entryNumber;
    }

    public Entry() {}

    public Entry(int entryId) {
        this.entryId = entryId;
    }

    public int getEntryId() {return entryId; }

    public String getEntryName() {return entryName;}

    public String getentryUserName() {return entryUserName; }

    public int getEntryNumber() {
        return entryNumber;
    }
}
