package com.example.dfrie.nytexplore.net;

import android.widget.Toast;

import com.example.dfrie.nytexplore.activities.ArticleListActivity;
import com.example.dfrie.nytexplore.activities.SetupSearchActivity;
import com.example.dfrie.nytexplore.enums.SortOrderEnum;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.example.dfrie.nytexplore.utils.NYTUtils.formatNYTDate;

public class ArticleClient {
    private static final String API_BASE_URL =
            "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private AsyncHttpClient client;

    public ArticleClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getArticles(final ArticleListActivity activity, final String query, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("api-key", ArticleListActivity.API_KEY);
        params.put("page", 0);
        params.put("q", query);

        // Read and set filter params into URL...
        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream(new File(activity.getFilesDir(),
                    SetupSearchActivity.SETUPS_FILENAME));
            props.load(is);
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        String strBeginDate = props.getProperty("etBeginDate");
        if (strBeginDate!=null && strBeginDate.length()>4) {
            params.put("begin_date", formatNYTDate(strBeginDate));
        }
        String strEndDate = props.getProperty("etEndDate");
        if (strEndDate!=null && strEndDate.length()>4) {
            params.put("end_date", formatNYTDate(strEndDate));
        }

        String strHighlight = props.getProperty("cbHighlight");
        if (strHighlight!=null) {
            params.put("hl", strHighlight.equals("1")? "true": "false");
        }
        String strSort = props.getProperty("spSort");
        if (strSort!=null) {
            params.put("sort", strSort.equals(SortOrderEnum.DATE_ASC.getName())? "oldest": "newest");
        }

        client.get(API_BASE_URL, params, handler);
/*
        try {
            String url = getApiUrl("?api-key=" + ArticleListActivity.API_KEY + "&q=" + query);
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
*/
    }
}
