package com.jjacobson.groupshop.sharing.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.User;

public class ProfileSetupActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        this.user = new User();

        // ui
        initCompleteButton();

    }

    /**
     * Initialize the complete button
     */
    private void initCompleteButton() {

    }

    /**
     * Get the user
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Save the user to the database
     */
    public void saveUser() {

    }
}
