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
            //System.out.println("got into /");
            Session session = request.session();
            String name = session.attribute("userName");

            HashMap userActivity = new HashMap();
            ArrayList storage = new ArrayList();

            if(!accountInfo.containsKey(name)) {
                //System.out.println("1");
                return new ModelAndView(userActivity,"index.html");
            }
            else {
                //System.out.println("2");
                userActivity.put("entries", entries);
                userActivity.put("userName", name);
                //userActivity.put("messageID", entries.size());
                System.out.println("currently in userActivity, the entry is at " + entries.size());
                return new ModelAndView(userActivity, "tracker.html");
            }
        }),
        new MustacheTemplateEngine()
        );

        Spark.post("/getIn", (request, response) -> {
            //System.out.println("got into /getIn");
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
            Entry entryObj = new Entry(entries.size(), name, message);
            //System.out.println(entryObj);
            //session.attribute("messageID", entries.size());
            entries.add(entryObj);
            response.redirect("/");
            return "";
        }));

        Spark.post("/delete-message", (request, response) -> {
           Session session = request.session();
           String delete = request.queryParams("messageID");
           System.out.println(delete);





            //String name = session.attribute("userName");
           //session.attribute("messageID", entries.size());
           //String deletede = session.attribute("messageID");
           //entries.remove(Integer.valueOf(delete));  //removes only the latest post
           //get the number from messageID, then match it up against the
            // index in entries, -1
           //entries.remove(Integer.valueOf(delete));
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
