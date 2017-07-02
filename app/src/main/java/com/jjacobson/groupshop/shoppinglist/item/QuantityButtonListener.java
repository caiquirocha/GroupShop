package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.EditText;

import com.jjacobson.groupshop.R;

/**
 * Created by Jeremiah on 6/30/2017.
 */

public class QuantityButtonListener implements View.OnClickListener {

    private ItemEditActivity activity;
    private EditText text;

    public QuantityButtonListener(ItemEditActivity activity) {
        this.activity = activity;
        text = (EditText) activity.findViewById(R.id.number_picker_display);
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
        activity.getItem().setCount(value);
    }
}
