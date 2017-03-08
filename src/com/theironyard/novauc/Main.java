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

    public static void main(String[] args) {

        Spark.staticFileLocation("/templates");

        Spark.init();

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
                    if(request.queryParams("calories").isEmpty()||request.queryParams("fat").isEmpty()||request.queryParams("mealName").isEmpty()||request.queryParams("carbs").isEmpty()||request.queryParams("protein").isEmpty()||request.queryParams("typeOfMeal").isEmpty()){
                        response.redirect("/");
                        return"";
                    }
                    String mealName = request.queryParams("mealName");
                    int calories = Integer.valueOf(request.queryParams("calories"));
                    int fat = Integer.valueOf(request.queryParams("fat"));
                    int carbs = Integer.valueOf(request.queryParams("carbs"));
                    int protein = Integer.valueOf(request.queryParams("protein"));
                    String typeOfMeal = request.queryParams("typeOfMeal");
                    int postId = currentUser.foodList.size();

                    Food foodEntry = new Food(mealName,calories,carbs,fat,protein,typeOfMeal,userName,postId);

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
                    if(request.queryParams("postdelete").equalsIgnoreCase("delete")){
                        int deleteId = Integer.valueOf(request.queryParams("postId"));
                        currentUser.foodList.remove(deleteId);
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
                    String deleteFood = request.queryParams("deleteName");

                    for(Food looper: currentUser.foodList){
                        if(looper.getMealName().equalsIgnoreCase(deleteFood)){
                            currentUser.foodList.remove(looper.getPostId());
                        }
                    }
                    String mealName = request.queryParams("mealName");
                    int calories = Integer.valueOf(request.queryParams("calories"));
                    int fat = Integer.valueOf(request.queryParams("fat"));
                    int carbs = Integer.valueOf(request.queryParams("carbs"));
                    int protein = Integer.valueOf(request.queryParams("protein"));
                    String typeOfMeal = request.queryParams("typeOfMeal");
                    int postId = currentUser.foodList.size();

                    Food foodEntry = new Food(mealName,calories,carbs,fat,protein,typeOfMeal,userName,postId);

                    currentUser.foodList.add(foodEntry);

                    response.redirect("/");
                    return"";
                })
                );

//        Spark.post("/edit-entry",
//                ((request, response) -> {
//                    Session session=request.session();
//                    String userName = session.attribute("userName");
//                    User currentUser = users.get(userName);
//                    if(currentUser == null){
//                        throw new Exception("User is not logged in");
//                    }
//                    if(currentUser.getUsername().equalsIgnoreCase(currentUser.foodList.get(request.port()).getAuthor())){
//                        currentUser.foodList.remove(request.port());
//                    }
//                    response.redirect("/edit-page");
//                    return "";
//                })
//                );
//
//        Spark.get("/edit-message/",
//                ((request, response) -> {
//                    Session session=request.session();
//                    String userName = session.attribute("userName");
//                    User currentUser = users.get(userName);
//                    if(currentUser == null){
//                        throw new Exception("User is not logged in");
//                    }
//                    if(request.queryParams("calories").isEmpty()||request.queryParams("fat").isEmpty()||request.queryParams("mealName").isEmpty()||request.queryParams("carbs").isEmpty()||request.queryParams("protein").isEmpty()||request.queryParams("typeOfMeal").isEmpty()){
//                        response.redirect("/edit-message/");
//                        return"";
//                    }
//                    String mealName = request.queryParams("mealName");
//                    int calories = Integer.valueOf(request.queryParams("calories"));
//                    int fat = Integer.valueOf(request.queryParams("fat"));
//                    int carbs = Integer.valueOf(request.queryParams("carbs"));
//                    int protein = Integer.valueOf(request.queryParams("protein"));
//                    String typeOfMeal = request.queryParams("typeOfMeal");
//                    int postId=Integer.valueOf(request.queryParams("postId"));
//                    Food foodEntry = new Food(mealName,calories,carbs,fat,protein,typeOfMeal,userName,postId);
//
//                    currentUser.foodList.add(foodEntry);
//                    response.redirect("/");
//                    return "";
//                })
//                );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String userName = request.queryParams("loginName").toLowerCase();
                    String password = request.queryParams("password");

                    if (userName == null) {
                        response.redirect("/");
                    }
                    if(!users.containsKey(userName)){
                        response.redirect("/");
                    }
                    if(users.containsKey(userName)&&!users.get(userName).getPassword().equals(password)){
                        response.redirect("/");
                    }

                    Session session = request.session();
                    session.attribute("userName", userName);

                    response.redirect("/");
                    return "";
                })
        );

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
                        response.redirect("/");
                        return"";
                    }
                    if (users.containsValue(userName)){
                        response.redirect("/");
                        return "";
                    }

                    User currentUser=new User(userName,password);
                    users.put(userName, currentUser);
                    Session session = request.session();
                    session.attribute("userName", userName);

                    response.redirect("/");
                    return "";
                })
        );
    }
}
