package com.adsnative.android.sdk.testapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.adsnative.android.sdk.adapter.AdsNativeListAdapter;
import com.adsnative.android.sdk.testapp.adapter.NewsAdapter;
import com.adsnative.android.sdk.testapp.item.NewsItem;
import com.adsnative.android.sdk.testapp.util.Constants;
import com.adsnative.android.sdk.testapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends ListActivity {

    private ProgressDialog progressDialog;

    private static final String TAG_RESULTS = "results";
    private static final String TAG_URL = "url";
    private static final String TAG_BYLINE = "byline";
    private static final String TAG_TITLE = "title";
    private static final String TAG_MEDIA = "media";
    private static final String TAG_META = "media-metadata";
    private static final String JSON_URL = "http://api.nytimes.com/svc/mostpopular/v2/mostshared/all-sections/7.json?api-key=23a6fda4c6eef1f5fdb9ae956f1560c8:4:68269115";

    List<NewsItem> newsItems;

    private Button clearAds;
    private Button fetchAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        newsItems = new ArrayList<NewsItem>();

        new GetNews() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(NewsActivity.this);
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(List<NewsItem> list) {
                super.onPostExecute(list);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                setItems(list);

            }
        }.execute(JSON_URL);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        NewsItem newsItem = (NewsItem) getListAdapter().getItem(position);
        String url = newsItem.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    protected void setItems(List<NewsItem> items) {
        newsItems = items;
        final NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, R.layout.news_list_item, newsItems);
        int[] sponsoredStoryPositions = {38, 1, 5, 1, 1, -45, -34, 14, 22};
        final AdsNativeListAdapter adsNativeListAdapter = new AdsNativeListAdapter(getBaseContext(), newsAdapter, sponsoredStoryPositions, Constants.AD_UNIT_ID);
        setListAdapter(adsNativeListAdapter);
        adsNativeListAdapter.loadSponsoredStories();
        clearAds = (Button) findViewById(R.id.clear_ads);
        clearAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adsNativeListAdapter.clearSponsoredStories();
                clearAds.setVisibility(View.GONE);
                fetchAds.setVisibility(View.VISIBLE);
            }
        });

        fetchAds = (Button) findViewById(R.id.fetch_ads);
        fetchAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adsNativeListAdapter.loadSponsoredStories();
                fetchAds.setVisibility(View.GONE);
                clearAds.setVisibility(View.VISIBLE);
            }
        });

        Button addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.twitter_egg);
                NewsItem newsItem = new NewsItem("http://www.google.com", "TitleTitle", "byLine", bitmap);
                newsItems.add(newsItem);
                newsAdapter.notifyDataSetChanged();
            }
        });

        Button delItem = (Button) findViewById(R.id.delete_item);
        delItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsItems.size() > 0) {
                    newsItems.remove(newsItems.size() - 1);
                    newsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class GetNews extends AsyncTask<String, Void, List<NewsItem>> {

        @Override
        protected List<NewsItem> doInBackground(String... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String jsonStr = serviceHandler.makeServiceCall(params[0], ServiceHandler.GET);
            List<NewsItem> list = new ArrayList<NewsItem>();
            if (jsonStr != null) {
                try {

                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray results = jsonObject.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject news = results.getJSONObject(i);

                        String url = news.getString(TAG_URL);
                        String byLine = news.getString(TAG_BYLINE);
                        String title = news.getString(TAG_TITLE);

                        Bitmap bitmap = null;
                        if (!news.isNull(TAG_MEDIA) && !news.getString(TAG_MEDIA).isEmpty()) {
                            JSONArray media = news.getJSONArray(TAG_MEDIA);
                            JSONObject imageData = media.getJSONObject(0);
                            JSONArray metaData = imageData.getJSONArray(TAG_META);
                            JSONObject imageThumb = metaData.getJSONObject(0);
                            String imageUrl = imageThumb.getString(TAG_URL);
                            bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openConnection().getInputStream());
                        }

                        NewsItem item = new NewsItem(url, title, byLine, bitmap);
                        list.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return list;
        }
    }
}
