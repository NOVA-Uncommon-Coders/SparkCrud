package com.theironyard.novauc;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    public static HashMap<String, User> globalAccounts = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", ((request, response) -> {
            System.out.println("got into ///");
            Session session = request.session();
            String name = session.attribute("userName");

            HashMap model = new HashMap();

            if(!globalAccounts.containsKey(name)) {
                System.out.println("1");
                return new ModelAndView(model,"index.html");
            }
            else {
                System.out.println("2");
                model.put("aVector", globalAccounts.get(name).getAvector());
                return new ModelAndView(model, "tracker.html");
            }
        }),
        new MustacheTemplateEngine()
        );

        System.out.println("A");

        Spark.post("/create-user", (request, response) -> {
            System.out.println("got into /create-user");
            String name = request.queryParams("createUser");
            String password = request.queryParams("createPassword");

            Session session = request.session();

            //if (!name.equals(globalAccounts.get(name)))
            if (!globalAccounts.containsKey(name)) {
                System.out.println("into create new");
                session.attribute("userName", name);
                globalAccounts.put(name, new User(name, password));
                //should createUser on the line above, actually be userName? I think yes
            }
            /*
            else {
                //if(name.equals(globalAccounts.get(name)))
                if(globalAccounts.containsKey(name)) {
                    response.redirect("/login");
                }
            }
            */
            response.redirect("/");
            return "failure at the end of /create-user";
        });

        Spark.post("/login", (request, response) -> {
            System.out.println("got into /login");
            globalAccounts.put("mike", new User("mike", "f"));
            String name = request.queryParams("userLogin");
            String password = request.queryParams("passwordLogin");
            Session session = request.session();

            //if(name.equals(globalAccounts.get(name)))
            if(globalAccounts.containsKey(name)) {
                if(password.equals(globalAccounts.get(name).getPassword())) {
                    session.attribute("userName", name);

                }
            }
            else {
                return "If you would like to create an account, go back and create an account";
            }
            response.redirect("/");
            return "failure at the end of /login";
        });

        Spark.post("/logout", ((request, response) -> {
           Session session = request.session();
           session.invalidate();
           response.redirect("/");
           return "";
        }));
    }
}
