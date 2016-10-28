package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by charlie on 10/28/16.
 */

public class ShoppingItemViewHolder extends RecyclerView.ViewHolder {

    TextView mNameTextView;

    public ShoppingItemViewHolder(View itemView) {
        super(itemView);

        mNameTextView = (TextView) itemView.findViewById(android.R.id.text1);
    }
}
