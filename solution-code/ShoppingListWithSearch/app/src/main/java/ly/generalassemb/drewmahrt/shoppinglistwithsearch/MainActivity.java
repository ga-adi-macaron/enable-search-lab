package ly.generalassemb.drewmahrt.shoppinglistwithsearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

import java.util.List;

import ly.generalassemb.drewmahrt.shoppinglistwithsearch.setup.DBAssetHelper;

public class MainActivity extends AppCompatActivity {

    private ShoppingSQLiteOpenHelper mDatabaseHelper;
    private List<ShoppingListItem> mShoppingItemsToDisplay;
    private RecyclerView.Adapter mShoppingListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        // Set up the RecyclerView
        RecyclerView shoppingRecyclerView = (RecyclerView) findViewById(R.id.shopping_recycler_view);

        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mDatabaseHelper = ShoppingSQLiteOpenHelper.getInstance(this);
        mShoppingItemsToDisplay = mDatabaseHelper.getShoppingList();
        mShoppingListAdapter = new ShoppingListRvAdapter(mShoppingItemsToDisplay);

        shoppingRecyclerView.setAdapter(mShoppingListAdapter);

        // Pass the intent that started the activity to handleIntent() in case it was a search action
        handleIntent(getIntent());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                // When the search is done, re-populate the RV with all items
                mShoppingItemsToDisplay.clear();
                mShoppingItemsToDisplay.addAll(mDatabaseHelper.getShoppingList());

                // Let the adapter know that we changed the List we gave it when it was created
                mShoppingListAdapter.notifyDataSetChanged();

                return true;
            }

        });

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Check if this intent is indeed the result of a search...
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            // If so, then get the user's input from the intent
            String query = intent.getStringExtra(SearchManager.QUERY);

            // Pass the user's query to the DB Helper to perform the actual search
            mShoppingItemsToDisplay.clear();
            mShoppingItemsToDisplay.addAll(mDatabaseHelper.searchShoppingList(query));

            // Let the adapter know that we changed the List we gave it when it was created
            mShoppingListAdapter.notifyDataSetChanged();
        }
    }
}
