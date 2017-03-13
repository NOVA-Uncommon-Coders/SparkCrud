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
        stated.execute("CREATE TABLE IF NOT EXISTS entries (entryID IDENTITY, entryName VARCHAR, entryModifier VARCHAR, entryNumber INT )");
        stated.execute("CREATE TABLE IF NOT EXISTS users (userID IDENTITY , userName VARCHAR, userPassword VARCHAR)");
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
        PreparedStatement ps = getConnection().prepareStatement("INSERT INTO users (userName, userPassword) VALUES (?,?)");
        ps.setString(1, userName);
        ps.setString(2, userPassword);
        ps.execute();
    }

    public static void deleteEntry(int entryID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("DELETE FROM entries WHERE entryID=?");
        ps.setInt(1, entryID);
        ps.execute();
    }

    public static ArrayList<Entry> selectEntry (int entryID) throws SQLException {
        ArrayList<Entry> entryAL = new ArrayList<>();
        PreparedStatement ps = getConnection().prepareStatement
                ("SELECT * FROM entries INNER JOIN users ON entries.entryName = users.userID WHERE entries.entryID = ?");
        ps.setInt(1, entryID);
        ResultSet results = ps.executeQuery();
        while (results.next()) {
            String entryNamei = results.getString("entries.name");
            int userIDi = results.getInt("users.userID");
            int entryIDi  = results.getInt("entries.entryID");
            entryAL(new Entry(entryIdi, entryName, entryModifier, entryNumber));
        }
        return entryAL;
    }


    public static ArrayList<Entry>  selectEntries() throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM entries ");
        ArrayList<Entry> entriesAL = new ArrayList<>();
        ResultSet results = ps.executeQuery();
        while (results.next()){
            int entryID = results.getInt("entryID");
            String entryName = results.getString("entryName");
            String entryModifier = results.getString("entryModifier");
            int entryNumber = results.getInt("entryNumber");

            entriesAL.add(new Entry(entryID, entryName, entryModifier, entryNumber));
        }
        return entriesAL;
    }

    public static User selectUser(String userName) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM users WHERE userName = ?");
        ps.setString(1, userName);
        ResultSet results = ps.executeQuery();
        if (results.next()) {
            int userID = results.getInt("userID");
            String userPassword = results.getString("userPassword");
            return new User(userID, userName, userPassword);
        }
        return null;
    }

    public static void updateEntry(int entryID, String entryName, String entryModifier, int entryNumber) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement
                ("UPDATE entries SET entryName = ?, entryModifier = ?, entryNumber = ? WHERE entryID = ?");
        ps.setString(1,entryName);
        ps.setString(2,entryModifier);
        ps.setInt(3,entryNumber);
        ps.setInt(4, entryID);
        ps.execute();
    }

    public static void main(String[] args) throws SQLException {
        createTables();
        //System.out.println(selectUser("mike").getUserName());
        Spark.init();
       //Server.createWebServer().start();



        Spark.get("/", ((request, response) -> {
                    Session session = request.session();
                    User user = session.attribute("user");
                    HashMap userActivityHM = new HashMap();

                    if(user == null) {
                        return new ModelAndView(userActivityHM,"index.html");
                    }
                    else {
                        userActivityHM.put("entries", selectEntries());
                        userActivityHM.put("user", user);
                        return new ModelAndView(userActivityHM, "index.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/login", (request, response) -> {
            String userName = request.queryParams("userName");
            String userPassword = request.queryParams("userPassword");
            Session session = request.session();
            User user = selectUser(userName);
            if (user != null) {
                if (user.getUserPassword().equals(userPassword)) {
                    session.attribute("user", user);
                }
            } else {
                insertUser(userName, userPassword);
                user = selectUser(userName);
                session.attribute("user", user);
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
            String entryName = request.queryParams("entryNameEdit");
            String entryModifier = request.queryParams("entryModifierEdit");
            int entryNumber = Integer.valueOf(request.queryParams("entryNumberEdit"));
            int entryID = Integer.valueOf(request.queryParams("entryID"));
            updateEntry(entryID, entryName, entryModifier, entryNumber);
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

