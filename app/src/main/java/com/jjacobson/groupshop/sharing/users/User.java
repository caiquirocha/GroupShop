package com.jjacobson.groupshop.sharing.users;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

/**
 * Created by Jeremiah on 7/20/2017.
 */

public class User {

    private String name;
    private String photoUri;
    private String email;

    private String key;
    private Bitmap profileImage;

    public User() {
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

    @Exclude
    public Bitmap getProfileImage() {
        return profileImage;
    }

    @Exclude
    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

}
