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


    public Movie() {
    }

    public Movie (String name, String actor, String genre, String rating){
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

    public static void setI(int i) {
        Movie.i = i;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", actor='" + actor + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }



}

