package com.jjacobson.groupshop.sharing.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.User;
import com.jjacobson.groupshop.sharing.users.UsernameModel;

public class ProfileSetupActivity extends AppCompatActivity {

    private User user;

    private DatabaseReference usernameRef;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        this.user = new User();

        // database
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setKey(firebaseUser.getUid());
        usernameRef = FirebaseDatabase.getInstance().getReference()
                .child("user-names")
                .child(user.getKey());

        userRef = FirebaseDatabase.getInstance().getReference()
                .child("user-profiles")
                .child(user.getKey());

        // ui
        initCompleteButton();

    }

    /**
     * Initialize the complete button
     */
    private void initCompleteButton() {
        Button button = (Button) findViewById(R.id.button_complete_profile);
        button.setOnClickListener(new CompleteButtonListener(this));
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
        UsernameModel model = new UsernameModel();
        model.setUsername(user.getUsername());
        model.setUsername_sort(user.getUsername().toLowerCase());
        usernameRef.setValue(model);
        userRef.setValue(user);
    }
}
