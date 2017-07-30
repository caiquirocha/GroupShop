package com.jjacobson.groupshop.shoppinglist.item;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeremiah on 6/26/2017.
 */

public class Item implements Serializable {

    private String name;
    private String unit;
    private int count;
    private boolean purchased;
    private long lastEdited;

    private String key;

    public Item() {
        // required for firebase
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Exclude
    public Date getLastEditedDate() {
        return new Date(lastEdited);
    }

    public long getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(long lastEdited) {
        this.lastEdited = lastEdited;
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
