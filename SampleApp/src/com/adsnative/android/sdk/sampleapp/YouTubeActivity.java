package com.adsnative.android.sdk.sampleapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.adsnative.android.sdk.adapter.AdsNativeListAdapter;
import com.adsnative.android.sdk.sampleapp.adapter.YouTubeAdapter;
import com.adsnative.android.sdk.sampleapp.item.YouTubeItem;
import com.adsnative.android.sdk.sampleapp.util.Constants;
import com.adsnative.android.sdk.sampleapp.util.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YouTubeActivity extends ListActivity {

    private ProgressDialog progressDialog;

    private static final String JSON_URL = "https://gdata.youtube.com/feeds/api/users/devinsupertramp/uploads?alt=json";
    private static final String TAG_FEED = "feed";
    private static final String TAG_ENTRY = "entry";
    private static final String TAG_LINK = "link";
    private static final String TAG_VALUE = "$t";
    private static final String TAG_TITLE = "title";
    private static final String TAG_HREF = "href";
    private static final String TAG_URL = "url";
    private static final String TAG_MGROUP = "media$group";
    private static final String TAG_MTHUMB = "media$thumbnail";

    List<YouTubeItem> youTubeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        youTubeItems = new ArrayList<YouTubeItem>();

        new GetYouTubeFeed(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(YouTubeActivity.this);
                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.show();
            }

            @Override
            protected void onPostExecute(List<YouTubeItem> list) {
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
        YouTubeItem youTubeItem = (YouTubeItem) getListAdapter().getItem(position);
        String url = youTubeItem.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    protected void setItems(List<YouTubeItem> items) {
        youTubeItems = items;
        YouTubeAdapter youTubeAdapter = new YouTubeAdapter(YouTubeActivity.this, R.layout.youtube_list_item, youTubeItems);
        int[] sponsoredStoryPositions = {2, 6, 45, 69};
        AdsNativeListAdapter adsNativeListAdapter = new AdsNativeListAdapter(getBaseContext(), youTubeAdapter, sponsoredStoryPositions, Constants.AD_UNIT_ID);
        setListAdapter(adsNativeListAdapter);
        adsNativeListAdapter.loadSponsoredStories();
    }

    private class GetYouTubeFeed extends AsyncTask<String, Void, List<YouTubeItem>> {

        @Override
        protected List<YouTubeItem> doInBackground(String... params) {
            ServiceHandler serviceHandler = new ServiceHandler();
            String jsonStr = serviceHandler.makeServiceCall(params[0], ServiceHandler.GET);

            List<YouTubeItem> list = new ArrayList<YouTubeItem>();

            if (jsonStr != null) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONObject feed = jsonObject.getJSONObject(TAG_FEED);
                    JSONArray entries = feed.getJSONArray(TAG_ENTRY);

                    for (int i = 0; i < entries.length(); i++) {
                        JSONObject entry = entries.getJSONObject(i);

                        JSONObject title = entry.getJSONObject(TAG_TITLE);
                        String titleStr = title.getString(TAG_VALUE);

                        JSONArray links = entry.getJSONArray(TAG_LINK);
                        JSONObject link = links.getJSONObject(0);

                        String url = link.getString(TAG_HREF);

                        JSONObject mediaGroup = entry.getJSONObject(TAG_MGROUP);
                        JSONArray mediaThumbnail = mediaGroup.getJSONArray(TAG_MTHUMB);
                        JSONObject image = mediaThumbnail.getJSONObject(0);
                        String imageUrl = image.getString(TAG_URL);
                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openConnection().getInputStream());

                        YouTubeItem youTubeItem = new YouTubeItem(url, titleStr, bitmap);
                        list.add(youTubeItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }
    }
}
