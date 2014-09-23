package com.adsnative.android.sdk.sampleapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsnative.android.sdk.sampleapp.R;
import com.adsnative.android.sdk.sampleapp.item.YouTubeItem;

import java.util.List;

public class YouTubeAdapter extends ArrayAdapter<YouTubeItem> {

    public YouTubeAdapter(Context context, int resource, List<YouTubeItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.youtube_list_item, null);

        }

        YouTubeItem element = getItem(position);

        if (element != null) {

            ImageView imageView = (ImageView) v.findViewById(R.id.yt_list_image);
            TextView title = (TextView) v.findViewById(R.id.yt_list_title);

            if (imageView != null) {
                if (element.getBitmap() != null)
                    imageView.setImageBitmap(element.getBitmap());
                else
                    imageView.setImageResource(R.drawable.twitter_egg);
            }

            if (title != null) {
                title.setText(element.getTitle());
            }
        }

        return v;

    }
}