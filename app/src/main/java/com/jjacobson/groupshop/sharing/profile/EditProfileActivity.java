package com.jjacobson.groupshop.sharing.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.BaseActivity;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.profile.image.ImageButtonListener;
import com.jjacobson.groupshop.sharing.users.User;

public class EditProfileActivity extends BaseActivity {

    private User user;

    // ui
    private EditText nameText;
    private EditText emailText;
    private ImageButtonListener imageListener;
    private CompleteButtonListener completeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String key = intent.getStringExtra("user_key");

        // ui
        nameText = (EditText) findViewById(R.id.profile_name_text);
        emailText = (EditText) findViewById(R.id.profile_email_text);
        initCompleteButton();
        initProfileImageButton();

        DatabaseReference reference = database.getReference()
                .child("user_profiles")
                .child(key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                onUserLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageListener.onActivityResult(requestCode, resultCode, data);
        completeListener.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Called when the users profile is loaded
     */
    private void onUserLoaded() {
        String name = user.getName();
        if (name != null && !name.equals("")) {
            nameText.setText(name);
        }

        String email = user.getEmail();
        if (email != null && !email.equals("")) {
            emailText.setText(email);
        }

        String uri = user.getPhotoUri();
        if (uri != null && !uri.equals("")) {
            imageListener.updateImage(Uri.parse(uri));
        }
    }

    /**
     * Initialize the complete button
     */
    private void initCompleteButton() {
        Button button = (Button) findViewById(R.id.button_save_profile);
        this.completeListener = new CompleteButtonListener(this);
        button.setOnClickListener(completeListener);
    }

    /**
     * Initialize the profile image button
     */
    private void initProfileImageButton() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.profile_image_button);
        imageListener = new ImageButtonListener(this);
        imageButton.setOnClickListener(imageListener);
    }

    /**
     * Get the user
     *
     * @return user
     */
    public User getUserProfile() {
        return user;
    }

}
