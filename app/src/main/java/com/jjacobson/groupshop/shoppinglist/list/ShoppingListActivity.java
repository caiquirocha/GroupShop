package com.jjacobson.groupshop.shoppinglist.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjacobson.groupshop.R;

public class ShoppingListActivity extends AppCompatActivity {

    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String name = intent.getStringExtra("list_name");
        setTitle(name);

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
                ItemEditDialog dialog = new ItemEditDialog(ShoppingListActivity.this, null);
                dialog.open();
            }
        });
    }

    /**
     * Initialize the recyclerview
     */
    private void initRecycler() {
        RecyclerView items = (RecyclerView) findViewById(R.id.shopping_list_recycler);
        items.setLayoutManager(new LinearLayoutManager(this));
    }
}
