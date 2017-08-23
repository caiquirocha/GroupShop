package com.jjacobson.groupshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    private void onAuthSuccess() {
        Intent intent = new Intent(MainActivity.this, MenuListActivity.class);
        startActivity(intent);
        finish();
    }

}
