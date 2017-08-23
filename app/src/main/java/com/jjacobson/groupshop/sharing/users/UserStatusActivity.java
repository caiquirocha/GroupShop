package com.jjacobson.groupshop.sharing.users;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.BaseActivity;

import java.util.Arrays;

public class UserStatusActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int action = intent.getIntExtra("action_extra", -1);
        switch (action) {
            case 1:
                signIn();
                break;
            case 0:
                signOut();
                break;
            case -1:
            default:
                break;
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == ResultCodes.OK) {
                onSignInComplete();
                return;
            }
            // user pressed back
            if (response == null) {

                return;
            }
            int error = response.getErrorCode();
            if (error == ErrorCodes.NO_NETWORK) {

            }
        }
    }

    /**
     * Sign a user in
     */
    public void signIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsAccountLinkingEnabled(true)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
    }

    /**
     * Sign a user out
     */
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void onSignInComplete() {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("user_profiles")
                .child(getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    createProfile();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /**
     * Create a newly registered users profile
     */
    private void createProfile() {
        User user = new User();
        user.setKey(getUser().getUid());

        String displayName = getUserDisplayName();
        if (displayName != null) {
            user.setName(displayName);
        }

        String email = getUserEmail();
        if (email != null) {
            user.setEmail(email);
        }

        Uri photo = getUserPhoto();
        if (photo != null) {
            user.setPhotoUri(photo.toString());
        }
        Intent intent = new Intent(this, UserSaveActivity.class);
        intent.putExtra("user_extra", user);
        startActivity(intent);
        finish();
    }

    /**
     * Get a users display name
     * @return the users display name
     */
    private String getUserDisplayName() {
        FirebaseUser user = getUser();
        String name = user.getDisplayName();
        for (UserInfo info : user.getProviderData()) {
            if (name == null && info.getDisplayName() != null) {
                name = info.getDisplayName();
            }
        }
        return name;
    }

    /**
     * Get a users email address
     * @return users email address
     */
    private String getUserEmail() {
        FirebaseUser user = getUser();
        String email = user.getEmail();
        for (UserInfo info : user.getProviderData()) {
            if (email == null && info.getEmail() != null) {
                email = info.getEmail();
            }
        }
        return email;
    }

    /**
     * Get a users photo uri
     * @return users photo uri
     */
    private Uri getUserPhoto() {
        FirebaseUser user = getUser();
        Uri photoUri = user.getPhotoUrl();
        for (UserInfo info : user.getProviderData()) {
            if (photoUri == null && user.getPhotoUrl() != null) {
                photoUri = info.getPhotoUrl();
            }
        }
        return photoUri;
    }
}
