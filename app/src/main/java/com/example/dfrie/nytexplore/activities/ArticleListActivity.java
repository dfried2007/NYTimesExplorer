package com.example.dfrie.nytexplore.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dfrie.nytexplore.R;
import com.example.dfrie.nytexplore.adapters.ArticleAdapter;
import com.example.dfrie.nytexplore.adapters.EndlessScrollListener;
import com.example.dfrie.nytexplore.models.Article;
import com.example.dfrie.nytexplore.net.ArticleClient;
import com.example.dfrie.nytexplore.net.ArticleClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import cz.msebera.android.httpclient.Header;


public class ArticleListActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "com.example.dfrie.nytexplore.activities.ARTICLE";
    public static final String API_KEY_FILE = "api_key.properties";
    public static final String API_KEY_NAME = "nytimes_api_key";
    public static String API_KEY;

    private GridView gvArticles;
    private ArticleAdapter articleAdapter;
    private ArticleClient client;
    // Instance of the progress action-view
    private MenuItem miActionProgressItem;

    // Fetch this data remotely initially...
    private String currentQuery = "android";
    private boolean didCurrentQuerySucceed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        try {
            InputStream is = getBaseContext().getAssets().open(API_KEY_FILE);
            Properties props = new Properties();
            props.load(is);
            API_KEY = props.getProperty(API_KEY_NAME);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, API_KEY_FILE + getString(R.string.file_not_found), Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Enable up icon...
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // diable the default toolbar title...
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        gvArticles = (GridView) findViewById(R.id.gvArticles);
        ArrayList<Article> aArticles = new ArrayList<>();
        // initialize the adapter
        articleAdapter = new ArticleAdapter(this, aArticles);
        // attach the adapter to the ListView
        gvArticles.setAdapter(articleAdapter);

        gvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(ArticleListActivity.this, R.string.loading, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), ArticleDetailActivity.class);
                i.putExtra(EXTRA_ARTICLE, articleAdapter.getItem(position));
                startActivity(i);
            }
        });

        // Attach the Endless scroll listener to the AdapterView...
        gvArticles.setOnScrollListener(new EndlessScrollListener() {
            /**
             * Triggered only when new data needs to be appended to the list
             * Use  loadNextDataFromApi(page);  or  loadNextDataFromApi(totalItemsCount);
             *
             * @param page
             * @param totalItemsCount
             * @return  true ONLY if more data is actually being loaded; false otherwise.
             */
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                fetchArticles(currentQuery, page);
                // TODO: handle race condition here...
                return didCurrentQuerySucceed;
                //return true;
            }
        });

        // Fetch this data remotely initially...
        fetchArticles(currentQuery, 0);
    }

    // Executes an API call to the NYT search endpoint, parses the results,
    //  converts them into an array of article objects, filters them, and adds them to the adapter.
    private void fetchArticles(String query, int pageOffset) {
        // Clear items from the list adapter on initial page requests...
        if (pageOffset==0) {
            articleAdapter.clear();
        }
        client = new ArticleClient();
        client.getArticles(this, query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs;
                    if(response != null) {
                        didCurrentQuerySucceed = ArticleClientHelper.executeNYTQuery(
                                ArticleListActivity.this, response, articleAdapter);
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                    didCurrentQuerySucceed = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                didCurrentQuerySucceed = false;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ////ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }
    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                fetchArticles(query, 0);
                currentQuery = query;

                // workaround to avoid issues with some emulators and keyboard devices firing
                // twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Toast.makeText(this, "Settings was Selected...", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), SetupSearchActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_profile) {
            Toast.makeText(this, "Profile was Selected...", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
