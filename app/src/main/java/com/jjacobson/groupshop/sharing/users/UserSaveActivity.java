package com.jjacobson.groupshop.sharing.users;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Jeremiah on 8/22/2017.
 */

public class UserSaveActivity extends Activity {

    private DatabaseReference profileRef;
    private StorageReference photoRef;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        this.profileRef = database.getReference()
                .child("user_profiles")
                .child(user.getUid());

        this.photoRef = storage.getReference()
                .child("profile_images")
                .child(user.getUid())
                .child("profile_image.jpg");

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra("user_extra");
        saveUser();
    }

    private void saveUser() {
        if (user.getPhotoUri() != null) {
            try {
                saveImage(Uri.parse(user.getPhotoUri()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveInfo();
        }
    }

    private void saveImage(Uri photo) throws IOException {
        Glide.with(this).asBitmap().load(photo).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] data = stream.toByteArray();
                onUploadReady(data);
            }
        });
    }

    private void onUploadReady(byte[] data) {
        UploadTask task = photoRef.putBytes(data);
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
        Intent resultIntent = new Intent();
        resultIntent.putExtra("save_success", true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
