package com.example.dfrie.nytexplore.net;

import android.support.v7.app.AppCompatActivity;

import com.example.dfrie.nytexplore.activities.SetupSearchActivity;
import com.example.dfrie.nytexplore.adapters.ArticleAdapter;
import com.example.dfrie.nytexplore.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by dfrie on 3/19/2017.
 */

public class ArticleClientHelper {

    private static final String RESPONSE_KEY = "response";
    private static final String DOCS_KEY = "docs";

    // Execute an API call to the NYT search endpoint, parses the results,
    //  convert them into an array of article objects, filter them, and add them to the adapter.
    public static boolean executeNYTQuery(AppCompatActivity activity,
                                          JSONObject response,
                                          ArticleAdapter articleAdapter) throws JSONException {
        // Get the docs json array
        JSONArray docs = response.getJSONObject(RESPONSE_KEY).getJSONArray(DOCS_KEY);
        // Parse json array into array of model objects
        final ArrayList<Article> articles = Article.fromJsonArray(docs);
        if (articles.size()==0) {
            return false;
        }

        // Read and filter results per params...
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream(new File(activity.getFilesDir(),
                    SetupSearchActivity.SETUPS_FILENAME));
            props.load(is);
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        String strArts = props.getProperty("cbArts");
        String strSports = props.getProperty("cbSports");
        String strFashion = props.getProperty("cbFashion");
        if ((strArts!=null && strArts.equals("1")) ||
                (strSports!=null && strSports.equals("1")) ||
                (strFashion!=null && strFashion.equals("1"))) {
            for (Article article : articles) {
                if ((strArts!=null && strArts.equals("1") && article.getNewsDesk().contains("Arts")) ||
                        (strSports!=null && strSports.equals("1") && article.getNewsDesk().contains("Sports")) ||
                        (strFashion!=null && strFashion.equals("1") &&
                                (article.getNewsDesk().contains("Fashion") || article.getNewsDesk().contains("Style")))) {
                    // add an article through the adapter
                    articleAdapter.add(article);
                }
            }
        } else {
            // Load model objects into the adapter
            articleAdapter.addAll(articles);
        }
        // not needed...
        //articleAdapter.notifyDataSetChanged();
        return true;
    }
}
