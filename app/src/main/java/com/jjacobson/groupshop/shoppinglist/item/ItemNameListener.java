package com.jjacobson.groupshop.shoppinglist.item;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Jeremiah on 7/1/2017.
 */

class ItemNameListener implements TextWatcher {

    private ItemEditActivity activity;

    public ItemNameListener(ItemEditActivity activity) {
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        activity.getItem().setName(editable.toString());
    }
}
