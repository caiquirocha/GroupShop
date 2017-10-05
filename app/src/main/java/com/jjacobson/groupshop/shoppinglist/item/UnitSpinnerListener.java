package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.list.ShoppingListActivity;

/**
 * Created by Jeremiah on 7/1/2017.
 */

public class UnitSpinnerListener implements AdapterView.OnItemSelectedListener {

    private ShoppingListActivity activity;
    private ItemPropertyDialog dialog;
    private Spinner spinner;

    public UnitSpinnerListener(ShoppingListActivity activity, ItemPropertyDialog dialog, Spinner spinner) {
        this.activity = activity;
        this.dialog = dialog;
        this.spinner = spinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            dialog.getItem().setUnit(null);
            ((TextView) spinner.getSelectedView()).setTextColor(activity.getResources().getColor(R.color.colorSpinnerUnitSelected)); //<----
            return;
        }
        String content = adapterView.getItemAtPosition(i).toString();
        if (content == null || content.equals("")) {
            return;
        }
        dialog.getItem().setUnit(content);
        spinner.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
