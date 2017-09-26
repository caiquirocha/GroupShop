package com.jjacobson.groupshop.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 9/25/2017.
 */

public class ShareUtil {

    /**
     * Share a list
     */
    public static void shareList(final Context context, final List list) {
        String link = context.getResources().getString(R.string.share_link) + list.getKey();
        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
                .setDynamicLinkDomain(context.getResources().getString(R.string.dynamic_link))
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
                            Uri shortLink = task.getResult().getShortLink();
                            onShareLinkReady(context, shortLink);
                        } else {
                            // todo toast error msg
                        }
                    }
                });
    }

    /**
     * Called when share link ready
     */
    private static void onShareLinkReady(final Context context, Uri link) {
        Intent shareIntent = new Intent();
        String msg = "Hey, check this out: " + link;
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        shareIntent.setType("text/plain");
        context.startActivity(shareIntent);
    }

}
