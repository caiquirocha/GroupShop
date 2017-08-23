package com.jjacobson.groupshop.sharing.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jjacobson.groupshop.BaseActivity;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.profile.image.ImageButtonListener;
import com.jjacobson.groupshop.sharing.users.User;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends BaseActivity {

    private ImageButtonListener imageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User user = database.getReference()

        // ui
        initCompleteButton();
        initProfileImageButton();
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
    }


    /**
     * Initialize the complete button
     */
    private void initCompleteButton() {
        Button button = (Button) findViewById(R.id.button_complete_profile);
        button.setOnClickListener(new CompleteButtonListener(this));
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
