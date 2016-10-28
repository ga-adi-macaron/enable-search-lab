package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by charlie on 10/28/16.
 */

public class ShoppingListRvAdapter extends RecyclerView.Adapter<ShoppingItemViewHolder> {

    List<ShoppingListItem> mShoppingListItems;

    public ShoppingListRvAdapter(List<ShoppingListItem> shoppingListItems) {
        mShoppingListItems = shoppingListItems;
    }

    @Override
    public ShoppingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ShoppingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShoppingItemViewHolder holder, int position) {
        holder.mNameTextView.setText(mShoppingListItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mShoppingListItems.size();
    }
}
