package com.theironyard.novauc;

import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Main {
    static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {

        Spark.init();
        Spark.get("/", ((request, response) -> {
                   // String id = request.queryParams("id");
                    //int idNum = -1;
                    //if (id != null) {
                       // idNum = Integer.valueOf(id);



                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);

                    ArrayList<Movie> yamama = user.movies;

                    HashMap c = new HashMap();
                    Set set = c.entrySet();
                    if (user == null) {
                        return new ModelAndView(c, "login.html");

                    } else {
                        c.put("name", user.name);
                        c.put("movie", yamama);
                        return new ModelAndView(c, "home.html");
                    }


                }),
                new MustacheTemplateEngine()


        );

        Spark.post("/create-account", ((request, response) -> {
                    String name = request.queryParams("accountName");
                    String password = request.queryParams("passWord");
                    User user = users.get(name);

                    if (user == null) {
                        user = new User(1, name, password);
                        users.put(name, user);

                    }
                    if (password.equalsIgnoreCase(user.password)) {

                    }
                    Session session = request.session();
                    session.attribute("userName", name);
                    response.redirect("/");
                    return "";

                })


        );

        Spark.post("/create-movie", ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);

                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }
                    String id = request.queryParams("id");
                    String movieName = request.queryParams("movieName");
                    String actorName = request.queryParams("actorName");
                    String movieGenre = request.queryParams("movieGenre");
                    String movieRating = request.queryParams("movieRating");
                    Movie movie = new Movie(movieName, actorName, movieGenre, movieRating);

                    user.movies.add(movie);

                    response.redirect("/");
                    return "";


                })

        );


        Spark.post("/edit-movie", ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    String updateMovie = request.queryParams("updateMovie");
                    int l = Integer.valueOf(updateMovie);
                    Movie addMovie = new Movie();
                    response.redirect("/");
                    return "";


                })

        );

        Spark.post("/delete", ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    String deleteMovie = request.queryParams("deleteMovie");
                    int i = Integer.valueOf(deleteMovie);
                    Movie removeMovie = new Movie();
            for (int j = 0; j < user.movies.size(); j++) {
                if(user.movies.get(j).getId() == i) {
                    removeMovie = user.movies.get(j);
                }
            }
            user.movies.remove(removeMovie);




                            response.redirect("/");
                            return "";

                    })

        );


        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );


    }


}




