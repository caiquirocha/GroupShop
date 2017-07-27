package com.jjacobson.groupshop.shoppinglist.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 7/27/2017.
 */

public class ListDropdownListener implements PopupMenu.OnMenuItemClickListener {

    private Context context;
    private List list;

    public ListDropdownListener(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_edit:
                displayEditDialog();
                break;
            case R.id.action_list_share:
                break;
            case R.id.action_list_delete:
                deleteList();
                break;
        }
        return false;
    }

    /**
     * Display the list name dialog prompt
     */
    private void displayEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
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
                saveList();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Rename the list
     *
     * @param name of list
     */
    private void renameList(String name) {
        list.setName(name);
    }

    /**
     * Save the list
     */
    private void saveList() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance()
                .getReference().child("user-lists").child(user.getUid()).child(list.getKey()).getRef();
        database.setValue(list);
    }

    /**
     * Delete the list
     */
    private void deleteList() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance()
                .getReference().child("user-lists").child(user.getUid()).child(list.getKey()).getRef();
        database.removeValue();
    }


}
