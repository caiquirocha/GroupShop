package com.jjacobson.groupshop.shoppinglist.item;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjacobson.groupshop.R;

import java.util.Arrays;

public class ItemEditActivity extends AppCompatActivity {

    // database
    private DatabaseReference databaseReference;
    private Item item;

    //ui


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // spinner
        initSpinner();
        initCounter();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        // check if existing activity
        Intent intent = getIntent();
        if (intent.hasExtra("item_key")) {
            loadItem(intent.getStringExtra("item_key"));
        } else {
            item = new Item();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // todo confirmation dialog
            finish();
            return true;
        }
        if (id == R.id.action_save_item) {
            saveItem();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize spinner with default hint
     */
    private void initSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.unit_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) view.findViewById(android.R.id.text1)).setText("");
                    ((TextView) view.findViewById(android.R.id.text1)).setHint(getItem(getCount())); // display hint
                }
                return view;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // don't display last item it's used as the hint
            }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(Arrays.asList(getResources().getStringArray(R.array.units_array)));
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); // set the hint the default selection
    }

    private void initCounter() {
        Button increase = (Button) findViewById(R.id.button_increase);
        Button decrease = (Button) findViewById(R.id.button_decrease);
        QuantityButtonListener listener = new QuantityButtonListener(this);
        increase.setOnClickListener(listener);
        decrease.setOnClickListener(listener);
    }

    /**
     * Load item from database given item key
     *
     * @param key for item to load
     */
    private void loadItem(String key) {
        // load
        DatabaseReference itemReference = databaseReference.child("items").child(key); // todo database reference

        ValueEventListener itemListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item = dataSnapshot.getValue(Item.class);
                updateInterface();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        itemReference.addListenerForSingleValueEvent(itemListener);
    }

    /**
     * Update the interface with the items properties
     */
    private void updateInterface() {
        setTitle(item.getName());
    }

    /**
     * Save the item to the database
     */
    private void saveItem() {

    }


}
