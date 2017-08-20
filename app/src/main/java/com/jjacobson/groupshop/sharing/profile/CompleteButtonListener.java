package com.jjacobson.groupshop.sharing.profile;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.User;
import com.jjacobson.groupshop.shoppinglist.menu.MenuListActivity;

/**
 * Created by Jeremiah on 8/15/2017.
 */

public class CompleteButtonListener implements View.OnClickListener {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
    private static final int USERNAME_MIN_LENGTH = 3;

    private ProfileSetupActivity activity;
    private EditText usernameText;
    private EditText nameText;

    public CompleteButtonListener(ProfileSetupActivity activity) {
        this.activity = activity;

        // ui
        this.usernameText = (EditText) activity.findViewById(R.id.profile_username);
        this.nameText = (EditText) activity.findViewById(R.id.profile_name);
    }

    @Override
    public void onClick(View view) {
        User user = activity.getUserProfile();
        String name = nameText.getText().toString();
        if (!name.equals("")) {
            user.setName(name);
        }
        String username = usernameText.getText().toString();
        if (!validateUsername(username)) {
            return;
        }
        verifyUsernameAvailable(username);
    }

    private boolean validateUsername(String username) {
        // check length
        if (username.length() < USERNAME_MIN_LENGTH) {
            String error = activity.getResources().getString(R.string.username_length_error, USERNAME_MIN_LENGTH);
            Snackbar.make(activity.findViewById(R.id.profile_setup_layout), error, Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (!username.matches(USERNAME_REGEX)) {
            String error = activity.getResources().getString(R.string.username_character_error);
            Snackbar.make(activity.findViewById(R.id.profile_setup_layout), error, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void verifyUsernameAvailable(final String username) {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("user_names")
                .orderByChild("username_sort")
                .equalTo(username.toLowerCase());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // username taken
                    String error = activity.getResources().getString(R.string.username_taken);
                    Snackbar.make(activity.findViewById(R.id.profile_setup_layout), error, Snackbar.LENGTH_LONG).show();
                    return;
                }
                // username available, all checks out
                onUsernameAvailable(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void onUsernameAvailable(String username) {
        activity.getUserProfile().setUsername(username);
        activity.saveProfile();
        Intent intent = new Intent(activity, MenuListActivity.class);
        activity.startActivity(intent);
    }

}
