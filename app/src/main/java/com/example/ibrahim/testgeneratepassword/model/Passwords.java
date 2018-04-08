package com.example.ibrahim.testgeneratepassword.model;

/**
 * Created by ibrahim on 08/04/18.
 */

public class Passwords {

    long id;
    String name;
    String password;

    public Passwords (long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public long getId () {
        return id;
    }

    public void setId (long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }
}
