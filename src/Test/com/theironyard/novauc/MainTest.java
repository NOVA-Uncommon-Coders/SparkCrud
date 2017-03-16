package com.theironyard.novauc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void insertEntry() throws Exception {
        Main.insertEntry();
        assertTrue(Main.selectEntries() ==
                Main.insertEntry("other", "stuff", 3, 4));
    }

    @Test
    public void insertUser() throws Exception {

    }

    @Test
    public void deleteEntry() throws Exception {

    }

    @Test
    public void selectUser() throws Exception {

    }

    @Test
    public void updateEntry() throws Exception {

    }

    @Test
    public void selectEntries() throws Exception {
        ArrayList<Entry> entriesAL = Main.selectEntries();
        assertTrue(entriesAL.size()!=0);
    }
}