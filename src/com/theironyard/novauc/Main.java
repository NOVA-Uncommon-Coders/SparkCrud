package com.theironyard.novauc;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static HashMap<String, User> accountInfo = new HashMap<>();
    public static ArrayList<Entry> entries = new ArrayList<>();


    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", ((request, response) -> {
            System.out.println("got into /");
            Session session = request.session();
            String name = session.attribute("userName");

            HashMap userActivity = new HashMap();
            ArrayList storage = new ArrayList();

            if(!accountInfo.containsKey(name)) {
                System.out.println("1");
                return new ModelAndView(userActivity,"index.html");
            }
            else {
                System.out.println("2");
                userActivity.put("entries", entries);
                userActivity.put("userName", name);
                return new ModelAndView(userActivity, "tracker.html");
            }
        }),
        new MustacheTemplateEngine()
        );

        Spark.post("/getIn", (request, response) -> {
            System.out.println("got into /getIn");
            accountInfo.put("default", new User("default", "d"));
            String name = request.queryParams("userLogin");
            String password = request.queryParams("passwordLogin");
            Session session = request.session();

            if(accountInfo.containsKey(name)) {
                if(password.equals(accountInfo.get(name).getPassword())) {
                    session.attribute("userName", name);
                }
            }
            else {
                session.attribute("userName", name);
                accountInfo.put(name, new User(name, password));
            }
            response.redirect("/");
            return "failure at the end of /getIn";
        });


        Spark.post("/new-entry", ((request, response) -> {
            Session session = request.session();
            String name = session.attribute("userName");
            String message = request.queryParams("newEntry");
            String messageId = request.queryParams("messageId");

            int IdForMessage =

            Entry entryObj = new Entry(entries.size(), replyId, name, message);
            //System.out.println(entryObj);
            entries.add(entryObj);
            response.redirect("/");
            return "";
        }));

        Spark.post("/delete-message", (request, response) -> {
           Session session = request.session();
           String name = session.attribute("userName");
           entries.remove(entries.size()-1);  //removes only the latest post
           response.redirect("/");
           return "";
        });

        Spark.post("/logout", ((request, response) -> {
           Session session = request.session();
           session.invalidate();
           response.redirect("/");
           return "";
        }));
    }
}
