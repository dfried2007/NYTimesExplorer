package com.example.dfrie.nytexplore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dfrie.nytexplore.R;
import com.example.dfrie.nytexplore.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {
    // View lookup cache
    private static class ViewHolder {
        public ImageView ivArticleImage ;
        public TextView tvTitle;
        public TextView tvSnippet;
    }

    public ArticleAdapter(Context context, List<Article> aArticles) {
        super(context, 0, aArticles);
    }

    // Translates a particular `Article` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_article, parent, false);
            viewHolder.ivArticleImage = (ImageView)convertView.findViewById(R.id.ivArticleImage);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            viewHolder.tvSnippet = (TextView)convertView.findViewById(R.id.tvSnippet);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data into the template view using the data object

        viewHolder.tvTitle.setText(article.getHeadline());
        viewHolder.tvSnippet.setText(article.getSnippet());
        viewHolder.ivArticleImage.setImageResource(0);

        String thumbStr = article.getThumbnail();
        if (thumbStr.length()>0) {
            Picasso.with(getContext()).load(thumbStr)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .into(viewHolder.ivArticleImage);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
