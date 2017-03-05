package com.theironyard.novauc;

import spark.Session;
import spark.Spark;

import java.util.HashMap;

public class Main {

    public static HashMap<String, User> globalAccounts = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", (request, response) -> {
            Session session = request.session();
            String name = request.attribute("userName");

            HashMap local = new HashMap();
            //if username
            //if ()
            return "";
        });

        Spark.post("/create-user", (request, response) -> {
            String name = request.queryParams("createUser");
            String password = request.queryParams("createPassword");

            Session session = request.session();

            if (!name.equals(globalAccounts.get(name))) {
                globalAccounts.put(name, new User(name, password));
                session.attribute("createUser", name);
                //should createUser on the line above, actually be userName?
            }
            else {
                if(name.equals(globalAccounts.get(name))) {
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
                    session.attribute("userLogin", name);

                }
            }
            else {
                return "If you would like to create an account, then create an account";
            }
            response.redirect("/");
            return "It's your fault";
        });
    }
}
