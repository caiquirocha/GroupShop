package com.jjacobson.groupshop.shoppinglist.list;

import com.google.firebase.database.Exclude;
import com.jjacobson.groupshop.shoppinglist.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jeremiah on 7/2/2017.
 */

public class List implements Serializable {

    private String name;
    private ArrayList<Item> items;
    private String key;

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

}
