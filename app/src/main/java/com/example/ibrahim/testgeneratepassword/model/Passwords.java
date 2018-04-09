package com.example.ibrahim.testgeneratepassword.model;

import android.content.Context;

/**
 * Created by ibrahim on 08/04/18.
 */

public class Passwords {

    String id;
    String name;
    String password;
    private boolean isSelected;


    public boolean isSelected () {
        return isSelected;
    }

    public void setSelected (boolean selected) {
        isSelected = selected;
    }

    public Passwords (String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
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
