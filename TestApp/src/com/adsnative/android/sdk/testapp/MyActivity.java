package com.adsnative.android.sdk.testapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.adsnative.android.sdk.testapp.adapter.MainAdapter;
import com.adsnative.android.sdk.testapp.item.MainListItem;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        List<MainListItem> listItems = new ArrayList<MainListItem>();
        listItems.add(new MainListItem("News", R.drawable.twitter_egg));
        listItems.add(new MainListItem("CustomViews", R.drawable.ic_launcher));

        MainAdapter listAdapter = new MainAdapter(this, R.layout.main_list_item, listItems);

        setListAdapter(listAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, CustomViewsActivity.class));
                break;
        }
    }
}
