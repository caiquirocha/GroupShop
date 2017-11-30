package com.jjacobson.groupshop.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.jjacobson.groupshop.BaseActivity;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 9/25/2017.
 */

public class ShareUtil {

    public static final int INVITATION = 5;

    /**
     * Share a list
     */
    public static void shareList(final BaseActivity activity, final List list) {
        DatabaseReference reference = activity.getDatabase().getReference()
                .child("lists")
                .child(list.getKey())
                .child("invitations");

        final String key = reference.push().getKey();
        reference.child(key).setValue(true); // todo move this to shareComplete
        Uri uri = Uri.parse(activity.getResources().getString(R.string.share_link_uri));

        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(uri)
                .setDynamicLinkDomain(activity.getResources().getString(R.string.dynamic_link))
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.jjacobson.groupshop")
                                .setMinimumVersion(125)
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink()
                                    .buildUpon()
                                    .appendQueryParameter("list_id", list.getKey())
                                    .appendQueryParameter("invite_id", key)
                                    .build();
                            onShareLinkReady(activity, shortLink, list);
                        } else {
                            // todo toast error msg
                        }
                    }
                });
    }

    /**
     * Called when share link ready
     */
    private static void onShareLinkReady(final BaseActivity activity, Uri link, List list) {
        Intent intent = new AppInviteInvitation.IntentBuilder(activity.getResources().getString(R.string.invitation_title))
                .setMessage(String.format(activity.getResources().getString(R.string.invitation_message), list.getName()))
                .setDeepLink(link)
                .setCallToActionText(activity.getResources().getString(R.string.invitation_cta))
                .build();

        activity.startActivityForResult(intent, INVITATION);
    }

    public static void shareComplete(BaseActivity activity, int result, Intent data) {
        System.out.println("Share completed!");
        if (result == Activity.RESULT_OK) {
            // Get the invitation IDs of all sent messages
            String[] ids = AppInviteInvitation.getInvitationIds(result, data);
            for (String id : ids) {
                //    Log.d(TAG, "onActivityResult: sent invitation " + id);
            }
        } else {
            // Sending failed or it was canceled, show failure message to the user
            // [START_EXCLUDE]
            // showMessage(getString(R.string.send_failed));
            // [END_EXCLUDE]
        }

    }

}
