package com.jjacobson.groupshop.sharing.profile.image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.profile.EditProfileActivity;

/**
 * Created by Jeremiah on 8/15/2017.
 */

public class ImageButtonListener implements View.OnClickListener {

    private static final int REQUEST_CODE = 10;

    private EditProfileActivity activity;
    private ImageButton imageButton;

    public ImageButtonListener(EditProfileActivity activity) {
        this.activity = activity;
        this.imageButton = (ImageButton) activity.findViewById(R.id.profile_image_button);
    }

    @Override
    public void onClick(View view) {
        // filesystem
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        // add delete option
        if (isImageSet()) {
            Intent removeImageIntent = new Intent(activity, DeleteImageActivity.class);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{removeImageIntent});
        }
        activity.startActivityForResult(chooserIntent, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("remove_image") && isImageSet()) {
                updateImage(null);
                return;
            }
            Uri selectedImage = data.getData();
            updateImage(selectedImage);
        }
    }

    public void updateImage(Uri image) {
        activity.getUserProfile().setPhotoUri(image == null ? null : image.toString());
        if (image == null) {
            imageButton.setImageResource(0);
            return;
        }
        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(activity).asBitmap().apply(options).load(image).into(new BitmapImageViewTarget(imageButton) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageButton.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    private boolean isImageSet() {
        return activity.getUserProfile().getPhotoUri() != null;
    }

}
