package com.jjacobson.groupshop.shoppinglist.list;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.item.Item;
import com.jjacobson.groupshop.shoppinglist.item.ItemNameListener;
import com.jjacobson.groupshop.shoppinglist.item.QuantityButtonListener;
import com.jjacobson.groupshop.shoppinglist.item.UnitSpinnerListener;

import java.util.Arrays;

/**
 * Created by Jeremiah on 7/11/2017.
 */

public class ItemEditDialog {

    private Context context;
    private List list;
    private Item item;
    private View view;

    public ItemEditDialog(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    /**
     * Display new item creation dialog
     */
    public void displayCreateItemDialog() {
        this.item = new Item();
        AlertDialog.Builder builder = initItemDialog();
        builder.setTitle(context.getResources().getString(R.string.new_item_title_text));
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                createItem();
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
     * Display item edit dialog
     *
     * @param item to edit
     */
    public void displayEditItemDialog(Item item) {
        this.item = item;
        AlertDialog.Builder builder = initItemDialog();
        builder.setTitle(item.getName());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                saveItem();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        updateUI();
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Display the list name dialog prompt
     */
    private AlertDialog.Builder initItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        this.view = inflater.inflate(R.layout.dialog_edit_item, null);
        builder.setView(view);

        // ui
        initNameText();
        initSpinner();
        initCounter();

        return builder;
    }

    /**
     * Initialize name edit text
     */
    private void initNameText() {
        EditText name = (EditText) view.findViewById(R.id.dialog_item_name);
        name.addTextChangedListener(new ItemNameListener(this));
    }

    /**
     * Initialize spinner with default hint
     */
    private void initSpinner() {
        Spinner spinner = (Spinner) view.findViewById(R.id.unit_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item) {

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
        adapter.addAll(Arrays.asList(context.getResources().getStringArray(R.array.units_array)));
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getCount()); // set the hint the default selection
        spinner.setOnItemSelectedListener(new UnitSpinnerListener(this));

    }

    /**
     * Initialize the quantity counter
     */
    private void initCounter() {
        Button increase = (Button) view.findViewById(R.id.button_increase);
        Button decrease = (Button) view.findViewById(R.id.button_decrease);
        QuantityButtonListener listener = new QuantityButtonListener(view, this);
        increase.setOnClickListener(listener);
        decrease.setOnClickListener(listener);
    }

    /**
     * Update dialog UI with existing values
     */
    private void updateUI() {
        EditText name = (EditText) view.findViewById(R.id.dialog_item_name);
        EditText count = (EditText) view.findViewById(R.id.number_picker_display);
        Spinner spinner = (Spinner) view.findViewById(R.id.unit_spinner);

        name.setText(item.getName());
        count.setText(String.valueOf(item.getCount()));
        ArrayAdapter<String> items = (ArrayAdapter<String>) spinner.getAdapter();
        spinner.setSelection(items.getPosition(item.getUnit()));
    }

    /**
     * Save new item to database
     */
    private void createItem() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference()
                .child("list-items")
                .child(list.getKey()).getRef();
        database.push().setValue(item);
    }

    /**
     * Update existing item in the database
     */
    private void saveItem() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference()
                .child("list-items")
                .child(list.getKey())
                .child(item.getKey()).getRef();
        database.setValue(item);
    }

    /**
     * Get the item from this dialog
     *
     * @return item
     */
    public Item getItem() {
        return item;
    }
}
