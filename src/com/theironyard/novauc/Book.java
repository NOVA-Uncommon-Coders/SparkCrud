package com.theironyard.novauc;

public class Book {
    String title;
    String author;
    String pubDate;
    String userCheckOut;
    String bookGenre;
    int amount;
    int checkedOutAmount;
    String isbn10;


    public Book() {
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getUserCheckOut() {
        return userCheckOut;
    }

    public void setUserCheckOut(String userCheckOut) {
        this.userCheckOut = userCheckOut;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCheckedOutAmount() {
        return checkedOutAmount;
    }

    public void setCheckedOutAmount(int checkedOutAmount) {
        this.checkedOutAmount = checkedOutAmount;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public Book(String title, String author, String pubDate, String userCheckOut) {
        this.title = title;
        this.author = author;
        this.pubDate = pubDate;
        this.userCheckOut = userCheckOut;
        checkedOutAmount = 0;
        amount = 0;
        try {
            isbn10 = Main.getBookInfo(title);
        } catch (Exception e){
            System.out.println("e happened for get ISBN");
        }
    }
    public Book(String title, String author, String bookGenre, int amount) {
        this.title = title;
        this.author = author;
        this.bookGenre = bookGenre;
        this.amount = amount;
        try {
            isbn10 = Main.getBookInfo(title);
        } catch (Exception e){
            System.out.println("e happened for get ISBN");
        }
    }
}
