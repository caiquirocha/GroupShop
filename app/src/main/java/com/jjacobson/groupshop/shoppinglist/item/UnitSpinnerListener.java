package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Jeremiah on 7/1/2017.
 */

public class UnitSpinnerListener implements AdapterView.OnItemClickListener {

    private ItemEditActivity activity;

    public UnitSpinnerListener(ItemEditActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        activity.getItem().setUnit(adapterView.getItemAtPosition(i).toString());
    }
}
