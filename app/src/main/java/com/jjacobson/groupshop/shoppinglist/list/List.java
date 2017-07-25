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
    private int totalItems;
    private int checkedItems;

    private ArrayList<Item> items;

    public List() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(int checkedItems) {
        this.checkedItems = checkedItems;
    }

    @Exclude
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Exclude
    public ArrayList<Item> getItems() {
        return items;
    }

}
