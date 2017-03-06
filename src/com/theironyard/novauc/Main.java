package com.theironyard.novauc;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    static Map<String, User> users = new HashMap<>();
    static Map<String,Food> foodz = new HashMap<>();
    //static User user;


    public static void main(String[] args) {

        Spark.staticFileLocation("/templates");


        Spark.init();
        addTestUsers();
        addBlanks();
        Spark.get(
                "/",
                ((request, response) -> {

                    HashMap m = new HashMap();
                    ArrayList<Food> breakfastList = new ArrayList<>();
                    ArrayList<Food> brunchList = new ArrayList<>();
                    ArrayList<Food> lunchList = new ArrayList<>();
                    ArrayList<Food> dinnerList = new ArrayList<>();

                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User currentUser = users.get(userName);

                    //the first page is ready to receive and redirect to the correct location if you
                    // are not currently logged in you will be thrown into the login menu
                    if (currentUser == null) {
                        return new ModelAndView(m, "start.html");
                    }

//                    String replyId = request.queryParams("replyId");
//                    int replyIdNum = -1;
//                    if (replyId != null) {
//                        replyIdNum = Integer.valueOf(replyId);
//                    }



                    for(Food foodRotation: currentUser.foodList){
                        if(foodRotation.getMealName().equalsIgnoreCase("breakfast")){
                            breakfastList.add(foodRotation);
                        }
                    }
                    for(Food foodRotation: currentUser.foodList){
                        if(foodRotation.getMealName().equalsIgnoreCase("brunch")){
                            brunchList.add(foodRotation);
                        }
                    }
                    for(Food foodRotation: currentUser.foodList){
                        if(foodRotation.getMealName().equalsIgnoreCase("lunch")){
                            lunchList.add(foodRotation);
                        }
                    }
                    for(Food foodRotation: currentUser.foodList){
                        if(foodRotation.getMealName().equalsIgnoreCase("dinner")){
                            dinnerList.add(foodRotation);
                        }
                    }

//                    for(User allusers: users.values())

                    m.put("breakfast", breakfastList);
                    m.put("brunch",brunchList);
                    m.put("lunch",lunchList);
                    m.put("dinner",dinnerList);
                    m.put("userName", userName);

                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/create-entry",
                ((request, response) -> {
                    Session session=request.session();
                    String userName = session.attribute("userName");
                    User currentUser = users.get(userName);
                    if(currentUser == null){
                        throw new Exception("User is not logged in");
                    }
                    String mealName = request.queryParams("mealName");
                    int calories = Integer.valueOf(request.queryParams("calories"));
                    int fat = Integer.valueOf(request.queryParams("fat"));
                    int carbs = Integer.valueOf(request.queryParams("carbs"));
                    int protein = Integer.valueOf(request.queryParams("protein"));
                    String typeOfMeal = request.queryParams("typeOfMeal");
                    Food foodEntry = new Food(mealName,calories,carbs,fat,protein,typeOfMeal,userName);

                    currentUser.foodList.add(foodEntry);
                    response.redirect("/");
                    return "";
                })
                );

        Spark.post("/delete-entry",
                ((request, response) -> {
                    Session session=request.session();
                    String userName = session.attribute("userName");
                    User currentUser = users.get(userName);
                    if(currentUser == null){
                        throw new Exception("User is not logged in");
                    }
                    if(currentUser.getUsername().equalsIgnoreCase(currentUser.foodList.get(request.port()).getAuthor())){
                        currentUser.foodList.remove(request.port());
                    }
                    response.redirect("/");
                    return "";
                })
                );

        Spark.post("/edit-entry",
                ((request, response) -> {
                    Session session=request.session();
                    String userName = session.attribute("userName");
                    User currentUser = users.get(userName);
                    if(currentUser == null){
                        throw new Exception("User is not logged in");
                    }
                    if(currentUser.getUsername().equalsIgnoreCase(currentUser.foodList.get(request.port()).getAuthor())){
                        currentUser.foodList.remove(request.port());
                    }
                    response.redirect("/edit-page");
                    return "";
                })
                );

        Spark.post("/edit-page",
                ((request, response) -> {
                    Session session=request.session();
                    String userName = session.attribute("userName");
                    User currentUser = users.get(userName);
                    if(currentUser == null){
                        throw new Exception("User is not logged in");
                    }
                    String mealName = request.queryParams("mealName");
                    int calories = Integer.valueOf(request.queryParams("calories"));
                    int fat = Integer.valueOf(request.queryParams("fat"));
                    int carbs = Integer.valueOf(request.queryParams("carbs"));
                    int protein = Integer.valueOf(request.queryParams("protein"));
                    String typeOfMeal = request.queryParams("typeOfMeal");
                    Food foodEntry = new Food(mealName,calories,carbs,fat,protein,typeOfMeal,userName);

                    currentUser.foodList.add(foodEntry);
                    response.redirect("/");
                    return "";
                })
                );


        Spark.post(
                "/login",
                ((request, response) -> {
                    String userName = request.queryParams("loginName").toLowerCase();
                    String password = request.queryParams("password");

                    if (userName == null) {
                        response.redirect("/login");
                    }
                    if(!users.containsKey(userName)){
                        response.redirect("/login");
                    }
                    if(users.containsKey(userName)&&!users.get(userName).getPassword().equals(password)){
                        response.redirect("/login");
                    }

//                    User user = users.get(userName);
//                    if (user == null) {
//                        user = new User(userName,password);
//                        users.put(userName, user);
//                    }

                    Session session = request.session();
                    session.attribute("userName", userName);

                    response.redirect("/");
                    return "";
                })
        );

//        Spark.get("/users/:name", (request, response) ->
//          "Selected user: " + request.params(":name"));

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String userName = request.queryParams("createLoginName");
                    String password = request.queryParams("createPassword");

                    if (userName == null) {
                        response.redirect("/create-user");
                    }
                    if (users.containsValue(userName)){
                        response.redirect("/create-user");
                    }

                    User currentUser=new User(userName,password);
                    users.put(userName, currentUser);
//                    User user = users.get(userName);
//                    if (user == null) {
//                        user = new User(userName,password);
//                        users.put(userName, user);
//                    }
                    Session session = request.session();
                    session.attribute("userName", userName);

                    response.redirect("/");
                    return "";
                })
        );
    }

    static void addTestUsers() {
        users.put("blank", new User("Alice","1234"));
    }

    static void addBlanks() {
        foodz.put("blank",new Food("blank" , 0, 0, 0,0,"dinner","blank"));
    }
}
