package com.jjacobson.groupshop.shoppinglist.menu;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;


/**
 * Created by Jeremiah on 7/12/2017.
 */

public class MenuListAdapter extends FirebaseRecyclerAdapter<List, MenuListHolder> {

    private Query checkedQuery;
    private Query totalQuery;
    private ValueEventListener checkedListener;
    private ValueEventListener totalListener;

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
    protected void populateViewHolder(final MenuListHolder holder, final List list, int position) {
        list.setKey(getRef(position).getKey());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("list-items")
                .child(list.getKey());

        checkedQuery = ref.orderByChild("purchased").equalTo(true);
        checkedListener = checkedQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                list.setCheckedItems((int) count);
                holder.setCheckedItems(list.getCheckedItems());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        totalQuery = ref;
        totalListener = totalQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                list.setTotalItems((int) count);
                holder.setTotalItems(list.getTotalItems());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        holder.setList(list);
        holder.setName(list.getName());
        // todo check shared users row
    }

    @Override
    public void cleanup() {
        super.cleanup();
        checkedQuery.removeEventListener(checkedListener);
        totalQuery.removeEventListener(totalListener);
    }
}
