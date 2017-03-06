package com.theironyard.novauc;

import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private String email;
    private String bookInterest;
    private boolean adminStatus;
    private ArrayList<Book> books = new ArrayList<>();
    private boolean adminUsersPage = false;
    private boolean adminBooksPage = false;
    private boolean userHomePage = false;
    private boolean userViewAllPage = false;

    public boolean isAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public User(String name, String password, String email, String bookInterest) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.bookInterest = bookInterest;
        this.adminStatus = false;
    }

    public boolean isUserHomePage() {
        return userHomePage;
    }

    public void setUserHomePage(boolean userHomePage) {
        this.userHomePage = userHomePage;
    }

    public boolean isUserViewAllPage() {
        return userViewAllPage;
    }

    public void setUserViewAllPage(boolean userViewAllPage) {
        this.userViewAllPage = userViewAllPage;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookInterest() {
        return bookInterest;
    }

    public void setBookInterest(String bookInterest) {
        this.bookInterest = bookInterest;
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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public User() {

    }
    public boolean isAdminUsersPage() {
        return adminUsersPage;
    }

    public void setAdminUsersPage(boolean adminUsersPage) {
        this.adminUsersPage = adminUsersPage;
    }

    public boolean isAdminBooksPage() {
        return adminBooksPage;
    }

    public void setAdminBooksPage(boolean adminBooksPage) {
        this.adminBooksPage = adminBooksPage;
    }
}
