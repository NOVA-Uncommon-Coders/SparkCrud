package com.theironyard.novauc;

import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();
        Spark.get("/",((request, response) ->{
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);

            HashMap c = new HashMap();
            if(user == null){
                return new ModelAndView(c, "login.html");

            } else {
                c.put("name", user.name);
                c.put("movie", user.movies);
                return new ModelAndView(c,"home.html");
            }


        }),
                new MustacheTemplateEngine()



        );

        Spark.post("/create-account", ((request, response) ->{
            String name = request.queryParams("accountName");
            String password = request.queryParams("passWord");
            User user = users.get(name);

            if(user == null){
                user = new User(name,password);
                users.put(name, user);

            }
                if (password.equalsIgnoreCase(user.password)){

                }
                Session session = request.session();
                session.attribute("userName", name);
                response.redirect("/");
                return "";

        })


        );

        Spark.post("/create-movie",((request, response) ->{
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);

            if(user == null){
                throw new Exception("User is not logged in");
            }

            String movieName = request.queryParams("movieName");
            String actorName = request.queryParams("actorName");
            String movieGenre = request.queryParams("movieGenere");
            String movieRating = request.queryParams("movieRating");
            Movie movie = new Movie(movieName,movieGenre,actorName,movieRating);

            user.movies.add(movie);

            response.redirect("/");
            return"";


        })

        );


        Spark.post("/edit-movie", (( request,  response) ->{
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            String updateMovie = request.queryParams("updateMovie");
            int l = Integer.valueOf(updateMovie);
            user.movies.remove(l -1);


            String editMovie = request.queryParams("editMovie");
            Movie movie= new Movie("","","","");
            user.movies.add(l -1, movie);
            response.redirect("/");
            return "";






        })

        );

        Spark.post("/delete",((request, response) ->{
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    String deleteMovie = request.queryParams("deleteMovie");
                    int l = Integer.parseInt(deleteMovie);
                    user.movies.remove(l -1);
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
