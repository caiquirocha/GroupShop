package com.jjacobson.groupshop.shoppinglist.menu;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class MenuListAdapter extends FirebaseRecyclerAdapter<List, MenuListHolder> {

    public MenuListAdapter(Class<List> modelClass, @LayoutRes int modelLayout, Class<MenuListHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    @Override
    public MenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_shopping_list, parent, false);
        MenuListHolder holder = new MenuListHolder(view, inflater);
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(MenuListHolder viewHolder, List model, int position) {

    }
}
