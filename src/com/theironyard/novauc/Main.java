package com.theironyard.novauc;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main {
    static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {


        Spark.init();
        Spark.get("/", ((request, response) -> {

            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            ArrayList<Beer> bob = user.beers;
            HashMap c = new HashMap();
//            Set set = c.entrySet();
            if (user == null) {
                return new ModelAndView(c,"login.html");
            } else {
                c.put("name",user.name);
                c.put("beer", bob);
                return new ModelAndView(c,"home.html");
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
        Spark.post("/create-beers", ((request, response) -> {
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);


                    if (user == null) {
                        throw new Exception("User is not logged in");
                    }
                    String id = request.queryParams("id");
                    String beerName = request.queryParams("beerName");
                    String beerType = request.queryParams("beerType");
                    String beerOrigin = request.queryParams("beerOrigin");
                    Beer beer = new Beer(beerName,beerType,beerOrigin);
                    user.beers.add(beer);
                    response.redirect("/");
                    return"";
                })
        );


        Spark.post("/edit-beer", ((request, response) -> {
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            String updateBeer = request.queryParams("updateBeer");
            String upName = request.queryParams("upName");
            String upType = request.queryParams("upType");
            String upOrigin = request.queryParams("upOrigin");
            int upBeer = Integer.valueOf(request.queryParams("upBeer"));
            Beer addBeer = new Beer();
            for (int i = 0; i < user.beers.size(); i++) {
                if (user.beers.get(i).getId() == upBeer) {
                    addBeer = user.beers.get(i);
                }
            }
            addBeer.setName(updateBeer);
            addBeer.setType(upType);
            addBeer.setOrigin(upOrigin);
            response.redirect("/");
            return "";
        })
        );
        Spark.post("/delete", ((request, response) -> {
            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            String deleteBeer = request.queryParams("deleteBeer");
            int i = Integer.valueOf(deleteBeer);
            Beer removeBeer = new Beer();
            for (int j = 0; j < user.beers.size(); j++) {
                if(user.beers.get(j).getId() == i) {
                  removeBeer = user.beers.get(j);
                }
            }
            user.beers.remove(removeBeer);
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
            return"";
        })
        );








        // write your code here
    }
}

