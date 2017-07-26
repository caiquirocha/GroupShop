package com.jjacobson.groupshop;


import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jeremiah on 7/11/2017.
 */

public class GroupShop extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // todo anonymous sign in
    }

}
