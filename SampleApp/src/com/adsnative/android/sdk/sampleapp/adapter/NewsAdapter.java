package com.adsnative.android.sdk.sampleapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsnative.android.sdk.sampleapp.R;
import com.adsnative.android.sdk.sampleapp.item.MainListItem;
import com.adsnative.android.sdk.sampleapp.item.NewsItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public NewsAdapter(Context context, int resource, List<NewsItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.news_list_item, null);

        }

        NewsItem element = getItem(position);

        if (element != null) {

            ImageView imageView = (ImageView) v.findViewById(R.id.news_list_image);
            TextView title = (TextView) v.findViewById(R.id.news_list_title);
            TextView byLine = (TextView) v.findViewById(R.id.news_list_by_line);

//            if (element.getImgUrl() != null) {
//                try {
//                    URL url = new URL(element.getImgUrl());
//                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

            if (imageView != null ) {
                if (element.getBitmap() != null)
                    imageView.setImageBitmap(element.getBitmap());
                else
                    imageView.setImageResource(R.drawable.twitter_egg);
            }

            if (title != null) {
                title.setText(element.getTitle());
            }

            if (byLine != null) {
                byLine.setText(element.getByLine());
            }
        }

        return v;

    }
}
