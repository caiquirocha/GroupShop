package com.jjacobson.groupshop.shoppinglist.item;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Jeremiah on 8/10/2017.
 */

public class QuanitityTextListener implements TextWatcher {

    private ItemPropertyDialog dialog;

    public QuanitityTextListener(ItemPropertyDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        dialog.getItem().setCount(Integer.valueOf(editable.toString()));
    }
}
