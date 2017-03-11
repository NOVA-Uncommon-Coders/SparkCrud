package com.theironyard.novauc;

/**
 * Created by jerieshasmith on 3/5/17.
 */
public class Movie {
    static int i = 0;
    int id;
    String name;
    String genre;
    String actor;
    String rating;



    public Movie (String name, String actor,String genre, String rating){
        this.id = i++;
        this.name = name;
        this.genre = actor;
        this.actor = genre;
        this.rating = rating;



    }

    public static int getI() {
        return i;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getActor() {
        return actor;
    }

    public String getRating() {
        return rating;
    }
}
