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
        return new MenuListHolder(view);
    }

    @Override
    protected void populateViewHolder(MenuListHolder holder, List list, int position) {
        list.setKey(getRef(position).getKey());
        holder.setList(list);
        holder.setName(list.getName());
        holder.setItemCount(list.getCheckedItems(), list.getTotalItems());
        // todo check shared users row
    }


}
