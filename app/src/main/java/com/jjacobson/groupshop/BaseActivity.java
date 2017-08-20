package com.jjacobson.groupshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jeremiah on 8/16/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth auth;
    protected FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
    }

    protected String getUid() {
        return auth.getCurrentUser().getUid();
    }

    protected FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

}
