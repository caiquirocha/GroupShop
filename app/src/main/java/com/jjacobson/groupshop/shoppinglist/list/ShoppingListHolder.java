package com.jjacobson.groupshop.shoppinglist.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.shoppinglist.item.Item;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class ShoppingListHolder extends RecyclerView.ViewHolder {

    private LinearLayout countLayout;

    private TextView name;
    private TextView itemCount;
    private TextView units;
    private CheckBox box;

    private List list;
    private Item item;

    public ShoppingListHolder(View itemView) {
        super(itemView);
        countLayout = (LinearLayout) itemView.findViewById(R.id.layout_item_count);
        name = (TextView) itemView.findViewById(R.id.item_name);
        itemCount = (TextView) itemView.findViewById(R.id.item_count);
        units = (TextView) itemView.findViewById(R.id.item_count_units);
        box = (CheckBox) itemView.findViewById(R.id.checkbox_item);
        initClickListener();
    }

    /**
     * Set name of item
     *
     * @param name of item
     */
    public void setName(String name) {
        this.name.setText(name);
    }

    /**
     * Set count for item
     *
     * @param count
     */
    public void setItemCount(int count) {
        if (count == 0) {
            countLayout.setVisibility(View.GONE);
        } else {
            countLayout.setVisibility(View.VISIBLE);
        }
        this.itemCount.setText(String.valueOf(count));
    }

    /**
     * Set item units
     *
     * @param units
     */
    public void setUnits(String units) {
        this.units.setText(units);
    }

    /**
     * Set whether purchased box is checked
     *
     * @param checked
     */
    public void setBoxChecked(boolean checked) {
        this.box.setChecked(checked);
    }

    /**
     * Set the list for this holder
     *
     * @param list for this holder
     */
    public void setList(List list) {
        this.list = list;
    }

    /**
     * Set the item for this holder
     *
     * @param item for this holder
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Initialize the click listener for the row
     */
    private void initClickListener() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemEditDialog dialog = new ItemEditDialog(itemView.getContext(), list);
                dialog.displayEditItemDialog(item);
            }
        });
    }
}
