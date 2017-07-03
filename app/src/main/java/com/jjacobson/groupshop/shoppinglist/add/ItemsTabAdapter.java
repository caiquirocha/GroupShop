package com.jjacobson.groupshop.shoppinglist.add;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jjacobson.groupshop.shoppinglist.add.fragments.PopularTabFragment;
import com.jjacobson.groupshop.shoppinglist.add.fragments.RecentTabFragment;

/**
 * Created by Jeremiah on 7/2/2017.
 */

public class ItemsTabAdapter extends FragmentStatePagerAdapter {

    private int tabs;

    public ItemsTabAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularTabFragment();
        }
        if (position == 1) {
            return new RecentTabFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
