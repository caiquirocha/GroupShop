package com.jjacobson.groupshop.shoppinglist.menu;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.User;
import com.jjacobson.groupshop.shoppinglist.list.List;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class MenuListHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private LinearLayout itemCount;
    private LinearLayout sharedUsers;

    private View itemView;
    private List list;

    public MenuListHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        name = (TextView) itemView.findViewById(R.id.list_name);
        itemCount = (LinearLayout) itemView.findViewById(R.id.layout_list_item_count);
        sharedUsers = (LinearLayout) itemView.findViewById(R.id.layout_list_shared);
        initDropdown();
    }

    /**
     * Set the display name of the list
     *
     * @param list to name
     */
    public void setName(String list) {
        name.setText(list);
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
     * Set the purchased count display
     *
     * @param checked number of items purchased
     * @param total   number of items
     */
    public void setItemCount(int checked, int total) {
        TextView checkedView = (TextView) itemCount.findViewById(R.id.list_item_count_checked);
        TextView totalView = (TextView) itemCount.findViewById(R.id.list_item_count_total);
        checkedView.setText(String.valueOf(checked));
        totalView.setText(String.valueOf(total));
    }

    /**
     * Add a user to the shared users layout
     *
     * @param user shared with
     */
    public void addSharedUser(User user) {
        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        View view = inflater.inflate(R.layout.shared_list, null);
        ImageView userImage = (ImageView) view.findViewById(R.id.shared_list_image);
        TextView userName = (TextView) view.findViewById(R.id.shared_list_name);
        //userImage.setImageDrawable(); // TODO: 7/20/2017
        //userName.setText(user.getName());
        sharedUsers.addView(view);
    }

    /**
     * Initialize the image button dropdown menu
     */
    private void initDropdown() {
        final ImageButton button = (ImageButton) itemView.findViewById(R.id.button_dropdown_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(itemView.getContext(), button);
                menu.getMenuInflater().inflate(R.menu.menu_shopping_list_row, menu.getMenu());
                menu.setOnMenuItemClickListener(new ListDropdownListener(itemView.getContext(), list));
                menu.show();
            }
        });
    }
}
