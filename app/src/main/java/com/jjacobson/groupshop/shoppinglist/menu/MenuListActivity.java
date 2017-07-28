package com.jjacobson.groupshop.shoppinglist.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.List;
import com.jjacobson.groupshop.shoppinglist.list.ShoppingListActivity;

public class MenuListActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;
    private MenuListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("user-lists").child(user.getUid()).getRef(); // todo

        //ui
        initDrawer();
        initFab();
        initRecycler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {

        }
    }

    /**
     * Initialize the actionbar drawer
     */
    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        FirebaseUser user = auth.getCurrentUser();
        if (!user.isAnonymous()) {
            findViewById(R.id.drawer_button_view).setVisibility(View.GONE);
            LinearLayout profileLayout = (LinearLayout) findViewById(R.id.drawer_profile_view);
            // todo populate profile
        }
    }

    /**
     * Initialize the new list floating action button
     */
    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCreateDialog();
            }
        });
    }

    /**
     * Initialize the recyclerview
     */
    private void initRecycler() {
        RecyclerView lists = (RecyclerView) findViewById(R.id.menu_list_recycler);
        MenuListAdapter adapter = new MenuListAdapter(List.class, R.layout.row_shopping_list, MenuListHolder.class, database);
        DividerItemDecoration divider = new DividerItemDecoration(lists.getContext(), DividerItemDecoration.VERTICAL);
        lists.addItemDecoration(divider);
        this.adapter = adapter;
        lists.setLayoutManager(new LinearLayoutManager(this));
        lists.setAdapter(adapter);
    }

    /**
     * Display the list name dialog prompt
     */
    private void displayCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_list_name, null);
        builder.setView(dialogView);

        final EditText text = (EditText) dialogView.findViewById(R.id.dialog_list_name);

        builder.setTitle(getResources().getString(R.string.new_list_title_text));
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = text.getText().toString();
                if (name.equals("")) {
                    return;
                }
                List list = createNewList(name);
                Intent intent = new Intent(MenuListActivity.this, ShoppingListActivity.class);
                intent.putExtra("list_extra", list);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Create and save a new list
     *
     * @param name of list
     * @return the created and saved list
     */
    private List createNewList(String name) {
        List list = new List();
        list.setName(name);
        saveList(list);
        return list;
    }

    /**
     * Save a list to the database
     *
     * @param list to save
     */
    private void saveList(List list) {
        database.push().setValue(list);
    }
}
