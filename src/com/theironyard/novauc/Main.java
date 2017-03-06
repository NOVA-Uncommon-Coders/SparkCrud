package com.theironyard.novauc;

import jodd.json.JsonParser;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import sun.plugin.javascript.JSObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static HashMap<String, User> users = new HashMap<>();


    public static void main(String[] args) {
        Spark.staticFileLocation("/styles");
        Spark.init();
        users.put("Mike", new User("Mike", "m", "me@here.com", "sci-fi"));
        users.get("Mike").setAdminStatus(true);
        users.put("will", new User("will", "m", "you@here.com", "classic"));
        users.put("someoneElse", new User("someoneElse", "m", "else@here.com", "modern"));

        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    HashMap m = new HashMap();
//
//                    m.put("noUser", session.attribute("noUser"));
//                    m.put("badPass", session.attribute("badPass"));
//                    m.put("userName", userName);

                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.get("/home.html",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    String userName = session.attribute("userName");



                    if (session.attribute("userName") == null){
                        m.put("noUser", session.attribute("noUser"));
                        m.put("badPass", session.attribute("badPass"));
                        m.put("userName", null);
                    } else if (users.get(userName).isAdminStatus()){
                        m.put("users", users.get(userName));
                    }else {
                        users.get(userName).setUserViewAllPage(false);
                        users.get(userName).setUserHomePage(true);
                        users.get(userName).setAdminBooksPage(false);
                        users.get(userName).setAdminUsersPage(false);
                        m.put("user", users.get(userName));
                    }


                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.get(
                "/logout.html",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    if (session.attribute("userName")!=null) {
                        String userName = session.attribute("userName");
                        users.get(userName).setUserViewAllPage(false);
                        users.get(userName).setUserHomePage(false);
                        users.get(userName).setAdminBooksPage(false);
                        users.get(userName).setAdminUsersPage(false);
                    }
                    session.invalidate();

                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.get(
                "/registration.html",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    if(session.attribute("userName") == null){
                        m.put("registration", "registration");
                    }
                    if (!(session.attribute("userExist") == null)){
                        m.put("userExist", "userExist");
                    }
                    if (!(session.attribute("badEmail") == null)){
                        m.put("badEmail", "badEmail");
                    }
                    if (!(session.attribute("shortPassword") == null)){
                        m.put("shortPassword", "shortPassword");
                    }
                    if (!(session.attribute("missingBook") == null)){
                        m.put("missingBook", "missingBook");
                    }
                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.get(
                "/books.html",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    users.get(userName).setUserViewAllPage(false);
                    users.get(userName).setUserHomePage(false);
                    users.get(userName).setAdminBooksPage(false);
                    users.get(userName).setAdminUsersPage(false);
                    if(!(users.get(userName).isAdminStatus())){
                        m.put("noAdmin", "noAdmin");

                    } else {
                        users.get(userName).setAdminBooksPage(true);
                        ArrayList<User> adminUser = new ArrayList<>();
                        adminUser.add(users.get(userName));
                        m.put("users", adminUser);
                    }

                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.get(
                "/users.html",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    users.get(userName).setUserViewAllPage(false);
                    users.get(userName).setUserHomePage(false);
                    users.get(userName).setAdminBooksPage(false);
                    users.get(userName).setAdminUsersPage(false);
                    if (users.get(userName).isAdminStatus()){
                        users.get(userName).setAdminUsersPage(true);
                        //m.put("user", users.get(userName));
                        ArrayList<User> allUsers = new ArrayList<>();
                        for (User user: users.values()){
                            allUsers.add(user);
                        }
                        m.put("users", allUsers);

                    } else {
                        m.put("noAdmin", "noAdmin");
                    }
                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.get (
                "/viewall.html",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    String userName = session.attribute("userName");

                    if (users.get(userName) != null) {
                        users.get(userName).setUserViewAllPage(true);
                        users.get(userName).setUserHomePage(false);
                        users.get(userName).setAdminBooksPage(false);
                        users.get(userName).setAdminUsersPage(false);
                        m.put("user", users.get(userName));
                        m.put("bookKeeper", users.get("Mike"));
                    }

                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                ((request, response) -> {
                    Session session = request.session();
                    if (!(session.attribute("noUser")==null)){
                        session.removeAttribute("noUser");
                    }
                    if (!(session.attribute("badPass") == null)){
                        session.removeAttribute("badPass");
                    }
                    String userName = request.queryParams("userName");
                    String password = request.queryParams("password");
                    if (users.containsKey(userName)){
                        if(users.get(userName).getPassword().equals(password)){
                            session.attribute("userName", userName);
                        } else {
                            session.attribute("badPass", "badPass");
                        }
                    } else {
                        session.attribute("noUser", "noUser");
                    }
                    response.redirect("/home.html");
                    return "";
                })
        );
        Spark.post(
                "/register",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = request.queryParams("userName");
                    String password = request.queryParams("password");
                    String email = request.queryParams("email");
                    String bookInterest = request.queryParams("bookInterest");
                    session.removeAttribute("badEmail");
                    session.removeAttribute("shortPassword");
                    session.removeAttribute("userExist");
                    session.removeAttribute("missingBook");
                    if (users.containsKey(userName)){
                        session.attribute("userExist", "userExits");
                        response.redirect("/registration.html");
                        return "";
                    }
                    if (!(email.contains("@")) || !(email.contains("."))){
                        session.attribute("badEmail", "badEmail");
                        response.redirect("/registration.html");
                        return "";
                    }
                    if ((email.endsWith(".com")) || (email.endsWith(".net")) || (email.endsWith(".edu")) || (email.endsWith(".gov"))){

                    } else {
                        session.attribute("badEmail", "badEmail");
                        response.redirect("/registration.html");
                        return "";
                    }
                    if (!(password.length() > 7)){
                        session.attribute("shortPassword", "shortPassword");
                        response.redirect("/registration.html");
                        return "";
                    }
                    if (bookInterest == null){
                        session.attribute("missingBook", "missingBook");
                        response.redirect("/registration.html");
                        return "";
                    }
                    users.put(userName, new User(userName, password, email, bookInterest));
                    session.attribute("userName", userName);
                    response.redirect("/home.html");
                    return "";
                })
        );
        Spark.post(
                "/editPref",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");

                    if (!(request.queryParams("newPref") == null)){
                        users.get(userName).setBookInterest(request.queryParams("newPref"));
                    }
                    response.redirect("/home.html");
                    return "";
                })
        );
        Spark.post(
                "/returnBook",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    String isbn10 = request.queryParams("isbn10");

                    //CHANGE Author removal to ISBN when added!
                    //MAKE SURE to change the HTML as well

                    Book removalBook = null;

                    for(Book book: users.get(userName).getBooks()){
                        if (book.getIsbn10().equals(isbn10)){
                            removalBook = book;

                            //CHANGE author here as well to getISBN()
                        }
                    }
                    for(Book book: users.get("Mike").getBooks()){
                        if(book.getIsbn10().equals(isbn10)){
                            book.setCheckedOutAmount(book.getCheckedOutAmount()-1);
                        }
                    }
                    users.get(userName).getBooks().remove(removalBook);
                    response.redirect("/home.html");
                    return "";
                })
        );
        Spark.post(
                "/removeUser",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    String removalName = request.queryParams("removeUser");
                    if (users.get(userName).isAdminStatus()){
                        for (Book book: users.get(removalName).getBooks()){
                            String isbn10 = book.getIsbn10();
                            for(Book b: users.get(userName).getBooks()){
                                if (book.getIsbn10().equals(b.getIsbn10())){
                                    b.setCheckedOutAmount(b.getCheckedOutAmount()-1);
                                }
                            }
                        }
                        users.remove(removalName);
                    }
                    response.redirect("/users.html");
                    return "";
                })
        );
        Spark.post (
                "/forceBookReturn",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    String removalBook = request.queryParams("isbn10");
                    String userBook = request.queryParams("userCheckOut");

                    //THIS needs to be changed to ISBN if you can make it work!

                    if (users.get(userName).isAdminStatus()){
                        Book usersBook = null;
                        for(Book book: users.get(userBook).getBooks()){
                            if (book.getIsbn10().equals(removalBook)){
                                usersBook = book;
                            }
                        }
                        users.get(userBook).getBooks().remove(usersBook);
                        for (Book book: users.get(userName).getBooks()){
                            if (book.getIsbn10().equals(removalBook)){
                                book.setCheckedOutAmount(book.getCheckedOutAmount()-1);
                            }
                        }
                    }
                    response.redirect("/users.html");
                    return "";
                })
        );
        Spark.post(
                "/addBook",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");

                    if (users.get(userName).isAdminStatus()){
                        String bookTitle = request.queryParams("bookTitle");
                        String author = request.queryParams("author");
                        String genre = request.queryParams("bookGenre");
                        int amount = Integer.valueOf(request.queryParams("bookCount"));
                        Book book = new Book(bookTitle, author, genre, amount);
                        for (Book b: users.get(userName).getBooks()){
                            if (b.getIsbn10().equals(book.getIsbn10())){
                                b.setAmount(b.getAmount()+amount);
                                response.redirect("/books.html");
                                return "";
                            }
                        }
                        users.get(userName).getBooks().add(book);
                    }
                    response.redirect("/books.html");
                    return "";
                })
        );
        Spark.post(
                "/checkOut",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    String isbn10 = request.queryParams("isbn10");

                    // NEEDS ISBN style thing

                    if(!(users.get(userName).isAdminStatus())){
                        Book checkoutBook = null;
                        for (Book book: users.get("Mike").getBooks()){
                            if (book.getIsbn10().equals(isbn10)){
                                checkoutBook = book;
                                if (checkoutBook.getCheckedOutAmount() >= checkoutBook.getAmount()){
                                    response.redirect("/home.html");
                                    return "";
                                }
                                checkoutBook.setCheckedOutAmount(checkoutBook.getCheckedOutAmount()+1);
                                break;
                            }
                        }
                        if (checkoutBook == null){
                            response.redirect("/home.html");
                            return "";
                        }
                        String addTitle = checkoutBook.getTitle();
                        String addAuthor = checkoutBook.getAuthor();
                        String addPubDate = checkoutBook.getPubDate();

                        users.get(userName).getBooks().add(new Book(addTitle, addAuthor, addPubDate, userName));
                    }
                    response.redirect("/home.html");
                    return "";

                })
        );
        Spark.post(
                "/removeBook",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");

                    String isbn10 = request.queryParams("bookIsbn10");
                    if (users.get(userName).isAdminStatus()){
                        Book removalBook = null;
                        for (Book book: users.get(userName).getBooks()){
                            if (book.getIsbn10().equals(isbn10)){
                                removalBook = book;
                                break;
                            }
                        }
                        if (removalBook != null){
                            users.get(userName).getBooks().remove(removalBook);
                        }
                    }
                    response.redirect("/books.html");
                    return "";
                })
        );
    }
    public static String getBookInfo (String title) throws java.lang.Exception {
        String isbn = null;
        String url = "http://isbndb.com/api/v2/json/2NE3ECKO/book/";
        title = title.replace(" ", "_");
        url += title;

        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("isbn10")){
                isbn = inputLine.substring(21,31);
            }
        }
        in.close();
        return isbn;
    }
}
/**
 *
 * TODO: Remove (book) needs to be changed to ISBN
 * TODO: Finish admin HTML and Post methods
 *
 */
