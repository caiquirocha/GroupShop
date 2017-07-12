package com.jjacobson.groupshop.shoppinglist.item;

import android.text.Editable;
import android.text.TextWatcher;

import com.jjacobson.groupshop.shoppinglist.list.ItemEditDialog;

/**
 * Created by Jeremiah on 7/1/2017.
 */

public class ItemNameListener implements TextWatcher {

    private ItemEditDialog dialog;

    public ItemNameListener(ItemEditDialog dialog) {
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
        dialog.getItem().setName(editable.toString());
    }
}
