package com.jjacobson.groupshop.shoppinglist.menu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.profile.edit.EditProfileActivity;

/**
 * Created by Jeremiah on 9/5/2017.
 */

public class DrawerItemListener implements NavigationView.OnNavigationItemSelectedListener {

    private MenuListActivity activity;

    public DrawerItemListener(MenuListActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_drawer_edit_profile:
                Intent editProfileIntent = new Intent(activity, EditProfileActivity.class);
                editProfileIntent.putExtra("user_key", activity.getUid());
                activity.startActivity(editProfileIntent);
                break;
            case R.id.nav_drawer_settings:
                Intent settingsIntent = new Intent(activity, SettingsActivity.class);
                activity.startActivity(settingsIntent);
                break;
        }
        return false;
    }
}
