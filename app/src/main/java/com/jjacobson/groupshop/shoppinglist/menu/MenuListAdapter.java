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

public class MenuListAdapter extends FirebaseRecyclerAdapter<Object, MenuListHolder> {

    private MenuListActivity activity;
    private Query checkedQuery;
    private Query totalQuery;
    private Query listQuery;
    private ValueEventListener checkedListener;
    private ValueEventListener totalListener;
    private ValueEventListener listListener;

    public MenuListAdapter(MenuListActivity activity, Class<Object> modelClass, @LayoutRes int modelLayout,
                           Class<MenuListHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
        this.activity = activity;
    }

    @Override
    public MenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_shopping_list, parent, false);
        return new MenuListHolder(view, activity);
    }

    @Override
    protected void populateViewHolder(final MenuListHolder holder, final Object value, int position) {
        final String key = getRef(position).getKey();
        listQuery = FirebaseDatabase.getInstance().getReference()
                .child("lists")
                .child(key);
        listListener = listQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List list = dataSnapshot.getValue(List.class);
                list.setKey(key);
                onListLoaded(holder, list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void onListLoaded(final MenuListHolder holder, final List list) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("list_items")
                .child(list.getKey());

        checkedQuery = ref.orderByChild("purchased").equalTo(true);
        checkedListener = checkedQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                holder.setCheckedItems((int) count);
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
                holder.setTotalItems((int) count);
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
        listQuery.removeEventListener(listListener);
    }
}
