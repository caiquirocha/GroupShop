package com.jjacobson.groupshop.shoppinglist.menu;

import android.support.annotation.LayoutRes;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class MenuListAdapter extends FirebaseRecyclerAdapter<List, MenuListHolder> {

    public MenuListAdapter(Class<List> modelClass, @LayoutRes int modelLayout, Class<MenuListHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    @Override
    protected void populateViewHolder(MenuListHolder viewHolder, List model, int position) {

    }
}
