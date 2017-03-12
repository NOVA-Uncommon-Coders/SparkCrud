package com.theironyard.novauc;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:./main");
    }

    public static void createTables() throws SQLException {
        Statement stated = getConnection().createStatement();
        stated.execute("CREATE TABLE IF NOT EXISTS entries (id IDENTITY, entryName VARCHAR, entryModifier VARCHAR, entryNumber INT )");
        stated.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY , userName VARCHAR, password VARCHAR)");
    }

    public static void insertEntry(String entryName, String entryModifier, int entryNumber) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("INSERT INTO entries (entryName, entryModifier, entryNumber) VALUES (?, ?, ?)");
        ps.setString(1,entryName);
        ps.setString(2,entryModifier);
        ps.setInt(3,entryNumber);
        ps.execute();
    }

    public static void insertUser (String userName, String userPassword) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("INSERT INTO users (userName, userPassword) VALUES (?,?)");
        ps.setString(1, userName);
        ps.setString(2, userPassword);
        ps.execute();
    }

    public static void deleteEntry(int entryID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("DELETE FROM entries WHERE id=?");
        ps.setInt(1, entryID);
        ps.execute();
    }

    public static void deleteUser (int userID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("DELETE FROM users WHERE id = ?");
        ps.setInt(1, userID);
        ps.execute();
    }

    public static ArrayList<Entry> selectEntry() throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM entries ");
        ArrayList<Entry> entriesAL = new ArrayList<>();
        ResultSet results = ps.executeQuery();
        while (results.next()){
            int id = results.getInt("id");
            String entryName = results.getString("entryName");
            String entryModifier = results.getString("entryModifier");
            int entryNumber = results.getInt("entryNumber");

            entriesAL.add(new Entry(id, entryName, entryModifier, entryNumber));
        }
        return entriesAL;
    }

    public static void updateEntry(String entryName, String entryModifier, int entryNumber) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("UPDATE entries SET entryModifier = ?, entryNumber = ? WHERE entryName = ?");
        ps.setString(3,entryName);
        ps.setString(1,entryModifier);
        ps.setInt(2,entryNumber);
        ps.execute();
    }

    public static User currentUser() {
        return new User(1, "billyray");
    }

    public static HashMap<String, User> accountInfo = new HashMap<>();
    public static ArrayList<Entry> restAL = new ArrayList<>();

    public static void main(String[] args) throws SQLException {

        Spark.init();
        Server.createWebServer().start();
        createTables();

        Spark.get("/", ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    HashMap userActivity = new HashMap();
                    if(!accountInfo.containsKey(name)) {
                        return new ModelAndView(userActivity,"index.html");
                    }
                    else {
                        userActivity.put("entries", selectEntry());
                        userActivity.put("userName", name);
                        return new ModelAndView(userActivity, "index.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/login", (request, response) -> {
            String name = request.queryParams("userName");
            String password = request.queryParams("passwordLogin");
            Session session = request.session();

            if(accountInfo.containsKey(name)) {
                if(password.equals(accountInfo.get(name).getUserPassword())) {
                    session.attribute("userName", name);
                }
            }
            else {
                session.attribute("userName", name);
                accountInfo.put(name, new User(name, password));
            }
            response.redirect("/");
            return "";
        });

        Spark.post("/create-entry", (request, response) -> {

            String entryName = request.queryParams("entryName");
            String entryModifier = request.queryParams("entryModifier");
            int entryNumber = Integer.valueOf(request.queryParams("entryNumber"));

            insertEntry(entryName, entryModifier,entryNumber);

            response.redirect("/");
            return "";
        });

        Spark.post("/edit-entry", (request, response) -> {

            //int id = Integer.valueOf(request.queryParams("restEditId"));
            String entryName = request.queryParams("entryNameEdit");
            String entryModifier = request.queryParams("entryModifierEdit");
            int entryNumber = Integer.valueOf(request.queryParams("entryNumberEdit"));

            updateEntry(entryName, entryModifier, entryNumber);

            response.redirect("/");
            return "";
        });

        Spark.post("/delete-entry", (request, response) -> {
            deleteEntry(Integer.valueOf(request.queryParams("entryDelete")));
            response.redirect("/");
            return "";
        });

        Spark.post("/logout", (request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";
        });
    }
}

