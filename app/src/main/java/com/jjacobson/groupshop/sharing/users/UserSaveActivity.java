package com.jjacobson.groupshop.sharing.users;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jjacobson.groupshop.BaseActivity;

/**
 * Created by Jeremiah on 8/22/2017.
 */

public class UserSaveActivity extends BaseActivity {

    private DatabaseReference profileRef;
    private StorageReference photoRef;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.profileRef = database.getReference()
                .child("user_profiles")
                .child(getUser().getUid());

        this.photoRef = storage.getReference()
                .child("profile_images")
                .child(getUser().getUid())
                .child("profile_image.jpg");

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra("user_extra");
        saveUser();
    }

    private void saveUser() {
        if (user.getPhotoUri() != null) {
            saveImage(Uri.parse(user.getPhotoUri()));
        } else {
            saveInfo();
        }
    }

    private void saveImage(Uri photo) {
        UploadTask task = photoRef.putFile(photo);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                user.setPhotoUri(uri.toString());
                saveInfo();
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void saveInfo() {
        profileRef.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                onSaveComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void onSaveComplete() {
        finish();
    }
}
