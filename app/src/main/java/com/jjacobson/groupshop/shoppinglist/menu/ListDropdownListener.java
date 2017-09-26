package com.jjacobson.groupshop.shoppinglist.menu;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;
import com.jjacobson.groupshop.util.ShareUtil;

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
                ShareUtil.shareList(activity, list);
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

        builder.setTitle(activity.getResources().getString(R.string.rename_list_title_text));
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
     * Rename the list
     *
     * @param name of list
     */
    private void renameList(String name) {
        list.setName(name);
    }

}
