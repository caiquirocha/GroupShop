package com.jjacobson.groupshop.sharing.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.jjacobson.groupshop.sharing.users.UsernameModel;

import java.io.ByteArrayOutputStream;

public class ProfileSetupActivity extends BaseActivity {

    // database references
    private DatabaseReference usernameRef;
    private DatabaseReference userRef;
    private StorageReference storageRef;

    private User user;

    private ImageButtonListener imageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        this.user = new User();

        // database
        user.setKey(getUid());
        usernameRef = FirebaseDatabase.getInstance().getReference()
                .child("user_names")
                .child(user.getKey());

        userRef = FirebaseDatabase.getInstance().getReference()
                .child("user_profiles")
                .child(user.getKey());

        storageRef = FirebaseStorage.getInstance().getReference()
                .child("profile_images")
                .child(user.getKey())
                .child("profile_image.jpg");

        // ui
        initCompleteButton();
        initProfileImageButton();

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

    /**
     * Save a profile and image to the database
     */
    public void saveProfile() {
        if (imageListener.isImageSet()) {
            saveProfileImage();
        } else {
            saveUser();
        }
    }

    /**
     * Save a user to the database
     */
    public void saveUser() {
        UsernameModel model = new UsernameModel();
        model.setUsername(user.getUsername());
        model.setUsername_sort(user.getUsername().toLowerCase());
        usernameRef.setValue(model);
        userRef.setValue(user);
    }

    /**
     * Save a users profile image
     */
    public void saveProfileImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        user.getProfileImage().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                user.setPhotoUri(uri.toString());
                saveUser();
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
