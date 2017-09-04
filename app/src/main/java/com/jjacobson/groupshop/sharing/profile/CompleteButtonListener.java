package com.jjacobson.groupshop.sharing.profile;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.UserSaveActivity;

/**
 * Created by Jeremiah on 8/22/2017.
 */

class CompleteButtonListener implements View.OnClickListener {

    private EditProfileActivity activity;

    private EditText nameText;
    private EditText emailText;

    public CompleteButtonListener(EditProfileActivity activity) {
        this.activity = activity;
        this.nameText = (EditText) activity.findViewById(R.id.profile_name_text);
        this.emailText = (EditText) activity.findViewById(R.id.profile_email_text);
    }

    @Override
    public void onClick(View view) {
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        activity.getUserProfile().setName(name);
        activity.getUserProfile().setEmail(email);

        Intent intent = new Intent(activity, UserSaveActivity.class);
        intent.putExtra("user_extra", activity.getUserProfile());
        activity.startActivity(intent);
        activity.finish();
    }
}
