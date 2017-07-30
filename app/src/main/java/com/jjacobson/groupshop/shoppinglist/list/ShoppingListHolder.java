package com.jjacobson.groupshop.shoppinglist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jjacobson.groupshop.R;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class ShoppingListHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView itemCount;
    private TextView units;
    private CheckBox box;


    public ShoppingListHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_name);
        itemCount = (TextView) itemView.findViewById(R.id.item_count);
        units = (TextView) itemView.findViewById(R.id.item_count_units);
        box = (CheckBox) itemView.findViewById(R.id.checkbox_item);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setItemCount(int count) {
        this.itemCount.setText(String.valueOf(count));
    }

    public void setUnits(String units) {
        this.units.setText(units);
    }

    public void setBoxChecked(boolean checked) {
        this.box.setChecked(checked);
    }
}
