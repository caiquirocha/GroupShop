package com.jjacobson.groupshop.shoppinglist.item;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeremiah on 6/26/2017.
 */

public class Item implements Serializable {

    private String name;
    private long lastEdited;
    private int count;
    private String unit;

    public Item() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastEdited() {
        return lastEdited;
    }

    @Exclude
    public Date getLastEditedDate() {
        return new Date(lastEdited);
    }

    public void setLastEdited(long lastEdited) {
        this.lastEdited = lastEdited;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
