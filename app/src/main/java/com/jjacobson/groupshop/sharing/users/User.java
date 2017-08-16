package com.jjacobson.groupshop.sharing.users;

import com.google.firebase.database.Exclude;

/**
 * Created by Jeremiah on 7/20/2017.
 */

public class User {

    private String username;
    private String name;

    private String key;

    public User() {
        // required for firebase
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }
}
