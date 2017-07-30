package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.AdapterView;

import com.jjacobson.groupshop.shoppinglist.list.ItemEditDialog;

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
        if (adapterView.getCount() == i) {
            return;
        }
        String content = adapterView.getItemAtPosition(i).toString();
        if (content == null || content.equals("")) {
            return;
        }
        System.out.println("selected item is " + content);
        dialog.getItem().setUnit(content);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
