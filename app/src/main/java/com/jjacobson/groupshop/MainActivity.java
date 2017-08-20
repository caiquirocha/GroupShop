package com.jjacobson.groupshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.sharing.profile.ProfileSetupActivity;
import com.jjacobson.groupshop.shoppinglist.menu.MenuListActivity;

/**
 * Created by Jeremiah on 8/18/2017.
 */

public class MainActivity extends BaseActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            signInAnonymously();
        } else {
            verifyProfile();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void signInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onAuthSuccess();
                } else {

                }
            }
        });
    }

    private void verifyProfile() {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("user_profiles")
                .child(getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Intent intent = new Intent(MainActivity.this, ProfileSetupActivity.class);
                    startActivity(intent);
                } else {
                    onAuthSuccess();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void onAuthSuccess() {
        Intent intent = new Intent(MainActivity.this, MenuListActivity.class);
        startActivity(intent);
        finish();
    }

}
