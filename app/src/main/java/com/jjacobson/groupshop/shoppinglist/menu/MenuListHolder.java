package com.jjacobson.groupshop.shoppinglist.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjacobson.groupshop.R;
import com.jjacobson.groupshop.sharing.users.User;

/**
 * Created by Jeremiah on 7/12/2017.
 */

public class MenuListHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private LinearLayout itemCount;
    private LinearLayout sharedUsers;

    private LayoutInflater inflater;

    public MenuListHolder(View itemView, LayoutInflater inflater) {
        super(itemView);
        this.inflater = inflater;
        name = (TextView) itemView.findViewById(R.id.list_name);
        itemCount = (LinearLayout) itemView.findViewById(R.id.layout_list_item_count);
        //sharedUsers = (LinearLayout) itemView.findViewById(R.id.layout_list_shared);
    }

    public void setName(String list) {
        name.setText(list);
    }

    public void setItemCount(int checked, int total) {
        TextView checkedView = (TextView) itemCount.findViewById(R.id.list_item_count_checked);
        TextView totalView = (TextView) itemCount.findViewById(R.id.list_item_count_total);
        checkedView.setText(String.valueOf(checked));
        totalView.setText(String.valueOf(total));
    }

    public void addSharedUser(User user) {
        View view = inflater.inflate(R.layout.shared_list, null);
        ImageView userImage = (ImageView) view.findViewById(R.id.shared_list_image);
        TextView userName = (TextView) view.findViewById(R.id.shared_list_name);
        //userImage.setImageDrawable(); // TODO: 7/20/2017
        //userName.setText(user.getName());
        sharedUsers.addView(view);
    }
}
