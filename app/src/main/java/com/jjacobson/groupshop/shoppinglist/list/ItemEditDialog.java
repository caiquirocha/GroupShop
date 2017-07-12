package com.jjacobson.groupshop.shoppinglist.list;

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

    private ShoppingListActivity activity;
    private Item item;
    private View view;

    public ItemEditDialog(ShoppingListActivity activity, Item item) {
        this.activity = activity;
        this.item = item;
    }

    /**
     * Display the list name dialog prompt
     */
    private void displayItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        this.view = inflater.inflate(R.layout.dialog_edit_item, null);
        builder.setView(view);

        // ui
        initNameText();
        initSpinner();
        initCounter();
        if (item == null) {
            builder.setTitle(activity.getResources().getString(R.string.new_item_title_text));
            item = new Item();
        } else {
            builder.setTitle(item.getName());
            updateUI();
        }

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
        AlertDialog dialog = builder.create();
        dialog.show();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item) {

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
        adapter.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.units_array)));
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
     * Update the interface with the items properties
     */
    private void updateUI() {
        EditText quantity = ((EditText) view.findViewById(R.id.number_picker_display));
        quantity.setText(String.valueOf(item.getCount()));
    }

    /**
     * Save the item to the database
     */
    private void saveItem() {

    }

    public void open() {
        displayItemDialog();
    }

    public Item getItem() {
        return item;
    }
}
