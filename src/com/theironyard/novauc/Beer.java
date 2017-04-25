package com.theironyard.novauc;

/**
 * Created by kevinallen on 4/25/17.
 */
public class Beer {
    static int i = 0;
    int id;
    String name;
    String type;
    String origin;

    public Beer() {
    }

    public Beer(String name, String type, String origin) {
        this.id =i++;
        this.name = name;
        this.type = type;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        Beer.i = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}







