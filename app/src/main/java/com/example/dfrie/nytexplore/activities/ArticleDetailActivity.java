package com.example.dfrie.nytexplore.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dfrie.nytexplore.R;
import com.example.dfrie.nytexplore.models.Article;

public class ArticleDetailActivity extends AppCompatActivity {
    private WebView wvFullArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        // Fetch views
        wvFullArticle = (WebView) findViewById(R.id.wvFullArticle);

        // Extract article url from intent extras
        Article article = getIntent().getParcelableExtra(ArticleListActivity.EXTRA_ARTICLE);

        wvFullArticle.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wvFullArticle.loadUrl(article.getWebUrl());

  /*      // Use article object to populate data into views
        tvTitle.setText(article.getTitle());
        tvAuthor.setText(article.getAuthor());
        Picasso.with(this.getApplicationContext()).load(article.getCoverUrl())
 *//*               .placeholder(R.drawable.loading)
                .transform(new RoundedCornersTransformation(28, 28))*//*
                .into(ivArticleCover);
*/
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
