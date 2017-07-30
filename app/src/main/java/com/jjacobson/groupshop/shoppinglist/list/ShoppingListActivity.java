package com.jjacobson.groupshop.shoppinglist.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.item.Item;

public class ShoppingListActivity extends AppCompatActivity {

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
                .child("list-items")
                .child(list.getKey()).getRef();

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
        if (id == R.id.action_list_settings) {
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
                ItemEditDialog dialog = new ItemEditDialog(view.getContext(), list);
                dialog.displayCreateItemDialog();
            }
        });
    }

    /**
     * Initialize the recyclerview
     */
    private void initRecycler() {
        RecyclerView items = (RecyclerView) findViewById(R.id.shopping_list_recycler);
        ShoppingListAdapter adapter = new ShoppingListAdapter(Item.class,
                R.layout.row_items_list, ShoppingListHolder.class, database, list);
        DividerItemDecoration divider = new DividerItemDecoration(items.getContext(), DividerItemDecoration.VERTICAL);
        items.addItemDecoration(divider);
        items.setLayoutManager(new LinearLayoutManager(this));
        items.setAdapter(adapter);
    }

}
