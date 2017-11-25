package com.jjacobson.groupshop.shoppinglist.list;

import com.google.firebase.database.Exclude;
import com.jjacobson.groupshop.shoppinglist.item.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Jeremiah on 7/2/2017.
 */

public class List implements Serializable {

    private String name;
    private String key;
    private Map<String, Boolean> users;
    private ArrayList<Item> items;

    public List() {
        // required for firebase
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Exclude
    public ArrayList<Item> getItems() {
        return items;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }
}
