package com.theironyard.novauc;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    static HashMap<String, User> users = new HashMap<>();
    //static ArrayList<ComedyAlbum> comedyAlbum = new ArrayList<>();

    public static void main(String[] args) {
	// write your code here

        Spark.init();
        Spark.get(
                "/",
                    ((request, response) ->{
                    HashMap kv = new HashMap();

                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);

                    if (user ==null){
                        return new ModelAndView(kv, "login.html");
                    } else {

                        kv.put("name", user.name);
                        return new ModelAndView(kv, "home.html");

                    }



                }), new MustacheTemplateEngine()
                );

                Spark.post ("/login", ((request, response) ->{
                    String name = request.queryParams("LoginName");
                    User user = users.get(name);
                    if (user == null){
                        user = new User(name);
                        users.put(name, user);
                    }
                    Session session = request.session();
                    session.attribute("userName", user);

                    response.redirect("/");

                    return "";

                })
                );

                Spark.post("/create-comedy", ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    if (user == null){
                        throw new Exception ("User is not logged in");
                    }

                    String comedyName = request.queryParams("comedyName");
                    String comedyGenre = request.queryParams("comedyGenre");
                    String comedyFormat = request.queryParams("comedyFormat");
                    int comedyDuration = Integer.valueOf(request.queryParams("comedyDuration"));
                    ComedyAlbum comedyAlbum = new ComedyAlbum(comedyName, comedyGenre, comedyFormat, comedyDuration);

                    user.comedyAlbums.add(comedyAlbum);

                    response.redirect ("/");
                    return "";

                        })
                );

                Spark.post ("/logout", ((request, response) -> {
                    Session session =request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";

                })
                );













    }
}
