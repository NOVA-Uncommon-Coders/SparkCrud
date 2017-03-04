package com.theironyard.novauc;

import spark.Session;
import spark.Spark;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", (request, response) -> {
            Session session = request.session();
            String name = request.attribute("userName");

            HashMap local = new HashMap();
        }));
    }
}
