package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.AdapterView;

import com.jjacobson.groupshop.shoppinglist.list.ItemEditDialog;
import com.jjacobson.groupshop.shoppinglist.list.ShoppingListActivity;

/**
 * Created by Jeremiah on 7/1/2017.
 */

public class UnitSpinnerListener implements AdapterView.OnItemSelectedListener {

    private ItemEditDialog dialog;

    public UnitSpinnerListener(ItemEditDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String content = adapterView.getItemAtPosition(i).toString();
        if (content == null || content.equals("")) {
            return;
        }
        dialog.getItem().setUnit(content);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
