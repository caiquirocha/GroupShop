package com.jjacobson.groupshop.shoppinglist.list;

import android.support.annotation.LayoutRes;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.jjacobson.groupshop.shoppinglist.item.Item;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class ShoppingListAdapter extends FirebaseRecyclerAdapter<Item, ShoppingListHolder> {

    public ShoppingListAdapter(Class<Item> modelClass, @LayoutRes int modelLayout, Class<ShoppingListHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    @Override
    protected void populateViewHolder(ShoppingListHolder viewHolder, Item model, int position) {

    }
}
