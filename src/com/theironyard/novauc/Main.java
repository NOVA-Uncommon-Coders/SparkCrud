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
            Session session = request.session();
            String name = session.attribute("userName");
            HashMap userActivity = new HashMap();
            if(!accountInfo.containsKey(name)) {
                return new ModelAndView(userActivity,"index.html");
            }
            else {
                userActivity.put("entries", entries);
                userActivity.put("userName", name);
                //System.out.println("currently in userActivity, the entry is at " + entries.size());
                return new ModelAndView(userActivity, "tracker.html");
            }
        }),
        new MustacheTemplateEngine()
        );

        Spark.post("/getIn", (request, response) -> {
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
            Entry entryObj = new Entry(name, message);
            entries.add(entryObj);
            response.redirect("/");
            return "";
        }));

        Spark.post("/edit-message", (request, response) -> {
            Session session = request.session();
            String editor = request.queryParams("editMessageT");

            int edit = Integer.valueOf(request.queryParams("messID"));

            Entry entrance = null;
            for (Entry picker : entries) {
                if (picker.getId() ==  edit) {
                    entrance = picker;
                    break;
                }
            }
            if (entrance != null && editor != null){
                entrance.setText(editor);
            }
            response.redirect("/");
            return "";
        });

        Spark.get("/anotherplace/:id", ((request, response) -> {
            String idJunk = request.params("id");
            HashMap whatever = new HashMap();
            whatever.put("id",idJunk);
            return new ModelAndView(whatever, "anotherplace.html");
        }), new MustacheTemplateEngine()
        );

        Spark.post("/delete-message", (request, response) -> {
           int delete = Integer.valueOf(request.queryParams("messy"));
           Entry entrance = new Entry();
           for (Entry picker : entries) {
               if (picker.getId() ==  delete) {
                   entrance = picker;
               }
           }
           entries.remove(entrance);
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
