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
            Session session = request.session();
            String name = request.attribute("userName");

            HashMap model = new HashMap();

            if(!globalAccounts.containsKey(name)) {
                return new ModelAndView(model,"index.html");
            }
            else {
                //model.put("aVector", globalAccounts.get(name).getAvector());
                return new ModelAndView(model, "another.html");
            }
        }),
        new MustacheTemplateEngine()
        );

        Spark.post("/create-user", (request, response) -> {
            String name = request.queryParams("createUser");
            String password = request.queryParams("createPassword");

            Session session = request.session();

            //if (!name.equals(globalAccounts.get(name)))
            if (!globalAccounts.containsKey(name)) {
                globalAccounts.put(name, new User(name, password));
                session.attribute("userName", name);
                //should createUser on the line above, actually be userName? I think yes
            }
            else {
                //if(name.equals(globalAccounts.get(name)))
                if(globalAccounts.containsKey(name)) {
                    response.redirect("/login");
                }
            }
            response.redirect("/");
            return "";
        });

        Spark.post("/login", (request, response) -> {
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
            return "It's your fault";
        });
    }
}
