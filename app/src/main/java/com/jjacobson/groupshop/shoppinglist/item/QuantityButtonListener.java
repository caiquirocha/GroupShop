package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.EditText;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.ItemEditDialog;
import com.jjacobson.groupshop.shoppinglist.list.ShoppingListActivity;

/**
 * Created by Jeremiah on 6/30/2017.
 */

public class QuantityButtonListener implements View.OnClickListener {

    private ItemEditDialog dialog;
    private EditText text;

    public QuantityButtonListener(View view, ItemEditDialog dialog) {
        this.dialog = dialog;
        text = (EditText) view.findViewById(R.id.number_picker_display);
    }

    @Override
    public void onClick(View view) {
        int value = Integer.parseInt(text.getText().toString());
        if (view.getId() == R.id.button_increase) {
            text.setText(String.valueOf(++value));
        }
        if (view.getId() == R.id.button_decrease) {
            if (value <= 1) {
                return;
            }
            text.setText(String.valueOf(--value));
        }
        dialog.getItem().setCount(value);
    }
}
