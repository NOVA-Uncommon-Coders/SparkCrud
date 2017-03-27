package com.theironyard.novauc;

import org.junit.Test;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void insertUser() throws Exception {
        Main.insertUser("Mike", "something here");
        assertEquals("Mike", Main.selectUser("Mike").getUserName());
    }

    @Test
    public void insertEntry() throws Exception {
        Main.insertEntry("other", "Mike", 3, 1);
        assertTrue(Main.selectEntry(1) != null);
        assertTrue(Main.selectEntry(1).getAuthorName().equals("peter"));
    }

    @Test
    public void deleteEntry() throws Exception {
        Main.deleteEntry(2);
        assertTrue(Main.selectEntry(2) == null);
    }

    @Test
    public void selectUser() throws Exception {
        assertEquals("peter", Main.selectUser("peter").getUserName());
    }

    @Test
    public void updateEntry() throws Exception {
        Main.insertEntry("something here", "bad stuff might happen", 69, 4);
        assertTrue(Main.selectEntry(1) != null);
        Entry createdEntry = null;
        for (Entry entry: Main.selectEntries()){
            if (entry.getEntryNumber() == 69){
                createdEntry = entry;
            }
        }
        if (createdEntry != null) {
            Main.updateEntry(createdEntry.getEntryId(), "something ELSE here", "nothing bad YET", 9696 );
            assertEquals(Main.selectEntry(createdEntry.getEntryId()).getEntryDescription(), "nothing bad YET" );
        }
        Main.selectEntries();
    }

    @Test
    public void selectEntries() throws Exception {
        ArrayList<Entry> entriesAL = Main.selectEntries();
        assertTrue(entriesAL.size()!=0);
        for (Entry entry: entriesAL){
            System.out.print(entry.getAuthorName()+ "\n");
        }
    }
}