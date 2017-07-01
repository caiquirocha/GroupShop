package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.EditText;

import com.jjacobson.groupshop.R;

/**
 * Created by Jeremiah on 6/30/2017.
 */

public class QuantityButtonListener implements View.OnClickListener {

    private EditText text;

    public QuantityButtonListener(ItemEditActivity activity) {
        text = (EditText) activity.findViewById(R.id.number_picker_display);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_increase) {
            int value = Integer.parseInt(text.getText().toString());
            text.setText(String.valueOf(++value));
            return;
        }

        if (view.getId() == R.id.button_decrease) {
            int value = Integer.parseInt(text.getText().toString());
            if (value <= 1) {
                return;
            }
            text.setText(String.valueOf(--value));
        }
    }
}
