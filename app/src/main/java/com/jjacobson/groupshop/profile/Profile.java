package com.jjacobson.groupshop.profile;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by Jeremiah on 7/20/2017.
 */

public class Profile implements Serializable {

    private String name;
    private String photoUri;
    private String email;

    private String key;

    public Profile() {
        // required for firebase
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
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
