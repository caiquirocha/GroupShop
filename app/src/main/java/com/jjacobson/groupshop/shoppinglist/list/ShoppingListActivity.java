package com.jjacobson.groupshop.shoppinglist.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.ShopActivity;
import com.jjacobson.groupshop.shoppinglist.item.Item;
import com.jjacobson.groupshop.shoppinglist.item.ItemPropertyDialog;

public class ShoppingListActivity extends ShopActivity {

    private DatabaseReference database;
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this.list = (List) intent.getSerializableExtra("list_extra");
        setTitle(list.getName());

        database = FirebaseDatabase.getInstance().getReference()
                .child("list_items")
                .child(list.getKey());

        //ui
        initFab();
        initRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_list_delete_purchased) {
            deletePurchased();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize the new list floating action button
     */
    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open dialog
                ItemPropertyDialog dialog = new ItemPropertyDialog(ShoppingListActivity.this);
                dialog.displayCreateItemDialog();
            }
        });
    }

    /**
     * Initialize the recyclerview
     */
    private void initRecycler() {
        RecyclerView items = (RecyclerView) findViewById(R.id.shopping_list_recycler);
        Query query = database.orderByChild("purchased");
        ShoppingListAdapter adapter = new ShoppingListAdapter(Item.class,
                R.layout.row_items_list, ShoppingListHolder.class, query, this);
        DividerItemDecoration divider = new DividerItemDecoration(items.getContext(), DividerItemDecoration.VERTICAL);
        items.addItemDecoration(divider);
        items.setLayoutManager(new LinearLayoutManager(this));
        items.setAdapter(adapter);
    }

    /**
     * Delete all purchased items
     */
    private void deletePurchased() {
        Query query = database.orderByChild("purchased").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                String text = getResources().getString(R.string.snackbar_delete_purchased, count);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
                CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.list_coordinator_layout);
                Snackbar.make(layout, text, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List getList() {
        return list;
    }

    /**
     * Save new item to database
     */
    public void createItem(Item item) {
        database.push().setValue(item);
    }

    /**
     * Update existing item in the database
     */
    public void saveItem(Item item) {
        database.child(item.getKey()).setValue(item);
    }

}
