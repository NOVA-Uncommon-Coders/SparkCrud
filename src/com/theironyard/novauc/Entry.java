package com.theironyard.novauc;

public class Entry {
    private int entryId;
    private String entryName;
    private String entryDescription;
    private int entryNumber;
    private String authorName;

    public Entry(int entryId, String entryName, String entryDescription, int entryNumber){
        this.entryId = entryId;
        this.entryName = entryName;
        this.entryDescription = entryDescription;
        this.entryNumber = entryNumber;
    }

    public Entry(int entryId, String entryName, String entryDescription, int entryNumber, String authorName) {
        this.entryId = entryId;
        this.entryName = entryName;
        this.entryDescription = entryDescription;
        this.entryNumber = entryNumber;
        this.authorName = authorName;
    }

    public Entry() {}

    public Entry(int entryId) {
        this.entryId = entryId;
    }

    public int getEntryId() {return entryId; }

    public String getEntryName() {return entryName;}

    public String getEntryDescription() {return entryDescription; }

    public int getEntryNumber() {
        return entryNumber;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public void setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
