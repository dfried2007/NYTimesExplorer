package com.example.dfrie.nytexplore.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dfrie on 3/16/2017.
 */

public class Article implements Parcelable {

    private static final String IMG_BASE_URL = "http://www.nytimes.com/";

    public static final String WEB_URL = "web_url";
    public static final String SNIPPET = "snippet";
    public static final String HEADLINE = "headline";
    public static final String NEWS_DESK = "news_desk";
    public static final String MAIN = "main";
    public static final String MULTIMEDIA = "multimedia";
    public static final String URL = "url";

    private String webUrl;
    private String headline;
    private String thumbnail;
    private String snippet;
    private String newsDesk;

    protected Article() {
    }

    protected Article(JSONObject jsonObj) {
        try {
            webUrl = jsonObj.getString(WEB_URL);
            headline = jsonObj.getJSONObject(HEADLINE).getString(MAIN);
            snippet = jsonObj.getString(SNIPPET);
            newsDesk = jsonObj.getString(NEWS_DESK);

            JSONArray multimedia = jsonObj.getJSONArray(MULTIMEDIA);
            if (multimedia.length() > 0) {
                JSONObject o = multimedia.getJSONObject(0);
                thumbnail = IMG_BASE_URL + o.getString(URL);
            } else {
                thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Article> results = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++) {
            try {
                results.add(new Article(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getNewsDesk() {
        return newsDesk;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected Article(Parcel in) {
        webUrl = in.readString();
        headline = in.readString();
        thumbnail = in.readString();
        snippet = in.readString();
        newsDesk = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(headline);
        dest.writeString(thumbnail);
        dest.writeString(snippet);
        dest.writeString(newsDesk);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

}
