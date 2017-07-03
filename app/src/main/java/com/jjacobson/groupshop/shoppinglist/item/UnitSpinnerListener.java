package com.jjacobson.groupshop.shoppinglist.item;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Jeremiah on 7/1/2017.
 */

public class UnitSpinnerListener implements AdapterView.OnItemSelectedListener {

    private ItemEditActivity activity;

    public UnitSpinnerListener(ItemEditActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String content = adapterView.getItemAtPosition(i).toString();
        if (content == null || content.equals("")) {
            return;
        }
        activity.getItem().setUnit(content);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
