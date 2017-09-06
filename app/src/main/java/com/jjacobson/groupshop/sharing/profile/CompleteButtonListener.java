package com.jjacobson.groupshop.sharing.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.UserSaveActivity;
import com.jjacobson.groupshop.util.DialogUtil;

/**
 * Created by Jeremiah on 8/22/2017.
 */

class CompleteButtonListener implements View.OnClickListener {

    private static final int SAVE_REQUEST_CODE = 23;

    private EditProfileActivity activity;

    private EditText nameText;
    private EditText emailText;

    private ProgressDialog dialog;

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

        // dialog
        dialog = DialogUtil.createProgressDialog(activity, activity.getResources().getString(R.string.dialog_update_profile));
        dialog.show();

        Intent intent = new Intent(activity, UserSaveActivity.class);
        intent.putExtra("user_extra", activity.getUserProfile());
        activity.startActivityForResult(intent, SAVE_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == SAVE_REQUEST_CODE) {
            if (data.hasExtra("save_success")) {
                dialog.hide();
                activity.finish();
            } else {

            }
        }

    }
}
