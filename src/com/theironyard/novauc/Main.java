package com.theironyard.novauc;

import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    static HashMap<String, User> usersList = new HashMap<>();

    public static User getUserFromRequest(Request request) {
        Session session = request.session();
        String name = session.attribute("user");
        return usersList.get(name);
    }

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", ((request, response) -> {
                    HashMap log = new HashMap<>(); //what is this hashmap doing at this point?
                    return new ModelAndView(log, "login.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.get("/create-user", ((request, response) -> {
                    HashMap log = new HashMap<>(); //what is this hashmap doing at this point?
                    return new ModelAndView(log, "register.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/create-user",
                ((request, response) -> {
                    String name = request.queryParams("user");
                    String password = request.queryParams("password");

                    User user = new User(name, password, new ArrayList<Message>());
                    usersList.put(name, user);
                    Session session = request.session();
                    session.attribute("user", name);
                    response.redirect("/");
                    return "";
                }
                ));

        Spark.post("/login", ((request, response) -> {
                    String name = request.queryParams("user");
                    String password = request.queryParams("password");

                    User user = usersList.get(name);
                    if (user == null) {
                        response.redirect("/create-user");
                    } else if (password.equals(user.password)) {
                        Session session = request.session();
                        session.attribute("user", name);
                        response.redirect("/home"); //can i redrect to an html instead of an action?
                    } else {
                        if (!password.equals(user.password)) ;
                        response.redirect("/create-user");
                    }
                    return null;  //or delete the mustache template engine and make ""
                }
                ), new MustacheTemplateEngine()
        );

        Spark.post("/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.get("/home", ((request, response) -> {
                    HashMap log = new HashMap<>();
                    User user = getUserFromRequest(request);
                    log.put("user", user.name);
                    log.put("password", user.password);
                    log.put("m", user.messageList);
                    return new ModelAndView(log, "home.html");
                }), new MustacheTemplateEngine()
        );

        Spark.post("/create",
                ((request, response) -> {
                    HashMap log = new HashMap<>();
                    User user = getUserFromRequest(request);
                    String message = request.queryParams("message");
                    //use the text of our message to create a new message object
                    Message m = new Message(message); //have to construct both the message and id.
                    //store our message in the user object's messagelist
                    log.put("m", user.messageList);
                    user.messageList.add(m);
                    //send user back to /messages
                    response.redirect("/home");
                    return new ModelAndView(log, "home.html");
                }), new MustacheTemplateEngine()
        );

        /* lets see if this works now that I have the user session enabled, and the deleteNumber
         query parameter is now noted as String m, and when it is pushed, m, is removed, and we are redirected
          back home*/
        Spark.post("/deleteNumber", ((request, response) -> {
                    User user = getUserFromRequest(request);
                    int m = Integer.valueOf(request.queryParams("deleteNumber"));
            //int i = Integer.parseInt(m) - 1;

            Message messy = null;
                    for (Message mess : user.messageList) {
                        if (mess.midentifier == m) {
                            messy = mess;
                            break;
                        }
                    }
                    user.messageList.remove(messy);

                    response.redirect("/home");
                    return "";
                })
        );
//
//        /*  this code below is a button that is supposed to
//        redirect the user to the user to the update.html page. But I only get a 500 error due to "NumberFromatException: null, and something on line 148 */
//        Spark.get("/update", ((request, response) -> {
//                    User user = getUserFromRequest(request);
//                    response.redirect("home.html");
//                    return "";
//                })
//        );

        Spark.post("/update", ((request, response) -> {
                    User user = getUserFromRequest(request);
                    int m = Integer.valueOf(request.queryParams("updateNumber"));
                    //int i = Integer.parseInt(m) - 1;
                    String updateMessage = request.queryParams("newMessage");
                    Message messy = null;
                    for (Message mess : user.messageList) {
                        if (mess.midentifier == m) {
                            messy = mess;
                            break;
                        }
                    }
                    messy.setText(updateMessage);

                    response.redirect("/home");
                    return "";
                })
        );
//        Spark.post("/update",((request, response) -> {
//                    User user = getUserFromRequest(request);
//
////                    Message m = new Message(message);
////                    log.put("m", user.messageList);
////                    user.messageList.add(m);
//
//                    response.redirect("/home");
//                    return "";
//                }),
//        );
//    }
    }
}




//    User user = getUserFromRequest(request);
//    HashMap log = new HashMap();
//    String updateNumber = request.queryParams("updateNumber");
//    String newMessage = request.queryParams("newMessage");
//    int messageNum = Integer.parseInt(updateNumber);
//                    messageNum--;
//                            user.messageList.get(messageNum).text = newMessage;
//                            response.redirect("home.html");
//                            //     return ModelAndView(log, "home.html");
//                            return "";
//                            })
//                            );




//
//            copied this code to save it from when i change it
//
//        Spark.post("/update",((request, response) -> {
//                User user = getUserFromRequest(request);
//                HashMap log = new HashMap<>();
//        String message = request.queryParams("newMessage");
//        Message m = new Message(message);
//        log.put("m", user.messageList);
//        user.messageList.add(m);
//        response.redirect("/update");
//        return new ModelAndView(log, "home.html");
//        }), new MustacheTemplateEngine()
//        );
//        }