package com.theironyard.novauc;

public class Entry {
    private int entryId;
    private String entryName;
    private String entryModifier;
    private int entryNumber;

    public Entry(int entryId, String entryName, String entryModifier, int entryNumber){
        this.entryId = entryId;
        this.entryName = entryName;
        this.entryModifier = entryModifier;
        this.entryNumber = entryNumber;
    }

    public Entry() {}

    public int getEntryId() {return entryId; }

    public String getEntryName() {
        return entryName;
    }

    public String getEntryModifier() {return entryModifier; }

    public int getEntryNumber() {
        return entryNumber;
    }
}
