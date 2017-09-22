package com.jjacobson.groupshop.shoppinglist.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 7/27/2017.
 */

public class ListDropdownListener implements PopupMenu.OnMenuItemClickListener {

    private MenuListActivity activity;
    private List list;

    public ListDropdownListener(MenuListActivity activity, List list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_edit:
                displayEditDialog();
                break;
            case R.id.action_list_share:
                shareList();
                break;
            case R.id.action_list_delete:
                activity.deleteList(list);
                break;
        }
        return false;
    }

    /**
     * Display the list name dialog prompt
     */
    private void displayEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.dialog_list_name, null);
        builder.setView(dialogView);

        final EditText text = (EditText) dialogView.findViewById(R.id.dialog_list_name);
        text.setText(list.getName());

        builder.setTitle(context.getResources().getString(R.string.rename_list_title_text));
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = text.getText().toString();
                if (name.equals("")) {
                    return;
                }
                renameList(name);
                activity.saveList(list);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }


    /**
     * Share the list
     */
    private void shareList() {
        String id = "";
        String link = activity.getResources().getString(R.string.share_link) + id;
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
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
                            Uri shortLink = task.getResult().getShortLink();
                            onShareLinkReady(shortLink);
                        } else {
                            // Error
                        }
                    }
                });
    }

    /**
     * Share link ready
     */
    private void onShareLinkReady(Uri link) {
        Intent shareIntent = new Intent();
        String msg = "Hey, check this out: " + link;
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        shareIntent.setType("text/plain");
        activity.startActivity(shareIntent);
    }

    /**
     * Rename the list
     *
     * @param name of list
     */
    private void renameList(String name) {
        list.setName(name);
    }

}
