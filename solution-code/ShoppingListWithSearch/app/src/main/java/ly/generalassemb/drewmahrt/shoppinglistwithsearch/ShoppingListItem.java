package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

/**
 * Created by charlie on 10/28/16.
 */

public class ShoppingListItem {
    private int mId;
    private String mName, mDescription, mPrice, mType;

    public ShoppingListItem(int id, String name, String description, String price, String type) {
        mId = id;
        mName = name;
        mDescription = description;
        mPrice = price;
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getType() {
        return mType;
    }
}
