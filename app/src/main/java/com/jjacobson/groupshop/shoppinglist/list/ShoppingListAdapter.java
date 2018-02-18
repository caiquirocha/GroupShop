package com.jjacobson.groupshop.shoppinglist.list;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.item.Item;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class ShoppingListAdapter extends FirebaseRecyclerAdapter<Item, ShoppingListHolder> {

    private ShoppingListActivity activity;

    private TextView emptyListView;

    public ShoppingListAdapter(Class<Item> modelClass, @LayoutRes int modelLayout,
                               Class<ShoppingListHolder> viewHolderClass, Query query, ShoppingListActivity activity) {
        super(modelClass, modelLayout, viewHolderClass, query);
        this.activity = activity;
        this.emptyListView = (TextView) activity.findViewById(R.id.empty_lists_text);
    }

    @Override
    public ShoppingListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_items_list, parent, false);
        return new ShoppingListHolder(view, activity);
    }

    @Override
    public void onDataChanged() {
        emptyListView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void populateViewHolder(ShoppingListHolder holder, Item item, int position) {
        item.setKey(getRef(position).getKey());
        holder.setItem(item);
        holder.setName(item.getName());
        holder.setItemCount(item.getCount());
        holder.setUnits(item.getUnit());
        holder.setBoxChecked(item.isPurchased());
    }
}
