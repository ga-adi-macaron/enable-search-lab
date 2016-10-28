package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drewmahrt on 12/28/15.
 */
public class ShoppingSQLiteOpenHelper extends SQLiteOpenHelper{
    private static final String TAG = ShoppingSQLiteOpenHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String SHOPPING_LIST_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "ITEM_NAME";
    public static final String COL_ITEM_PRICE = "PRICE";
    public static final String COL_ITEM_DESCRIPTION = "DESCRIPTION";
    public static final String COL_ITEM_TYPE = "TYPE";

    public static final String[] SHOPPING_COLUMNS = {COL_ID,COL_ITEM_NAME,COL_ITEM_DESCRIPTION,COL_ITEM_PRICE,COL_ITEM_TYPE};

    private static final String CREATE_SHOPPING_LIST_TABLE =
            "CREATE TABLE " + SHOPPING_LIST_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_ITEM_DESCRIPTION + " TEXT, " +
                    COL_ITEM_PRICE + " REAL, " +
                    COL_ITEM_TYPE + " TEXT )";

    private static ShoppingSQLiteOpenHelper mInstance;

    public static ShoppingSQLiteOpenHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new ShoppingSQLiteOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private ShoppingSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_LIST_TABLE_NAME);
        this.onCreate(db);
    }

    public List<ShoppingListItem> getShoppingList(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SHOPPING_LIST_TABLE_NAME, // a. table
                SHOPPING_COLUMNS, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<ShoppingListItem> shoppingListItems = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COL_ITEM_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndex(COL_ITEM_PRICE));
                String type = cursor.getString(cursor.getColumnIndex(COL_ITEM_TYPE));

                shoppingListItems.add(new ShoppingListItem(id, name, description, price, type));

                cursor.moveToNext();
            }
        }

        cursor.close();
        return shoppingListItems;
    }

    public List<ShoppingListItem> searchShoppingList(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        // NOTE - the LIKE operator is case-insensitive in SQLite, meaning it ignores uppercase
        // vs lowercase while making its comparisons

        Cursor cursor = db.query(SHOPPING_LIST_TABLE_NAME, // a. table
                SHOPPING_COLUMNS, // b. column names
                COL_ITEM_NAME + " LIKE ?", // c. selections
                new String[]{"%"+query+"%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<ShoppingListItem> searchResults = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COL_ITEM_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndex(COL_ITEM_PRICE));
                String type = cursor.getString(cursor.getColumnIndex(COL_ITEM_TYPE));

                searchResults.add(new ShoppingListItem(id, name, description, price, type));

                cursor.moveToNext();
            }
        }

        cursor.close();
        return searchResults;
    }
}
