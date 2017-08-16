package com.jjacobson.groupshop.sharing.users;

/**
 * Created by Jeremiah on 8/15/2017.
 */

public class UsernameModel {

    private String username;
    private String username_sort;

    public UsernameModel() {
        // required for firebase
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_sort() {
        return username_sort;
    }

    public void setUsername_sort(String username_sort) {
        this.username_sort = username_sort;
    }
}
