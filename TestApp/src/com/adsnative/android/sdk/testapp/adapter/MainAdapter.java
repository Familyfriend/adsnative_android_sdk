package com.adsnative.android.sdk.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adsnative.android.sdk.testapp.R;
import com.adsnative.android.sdk.testapp.item.MainListItem;

import java.util.List;


public class MainAdapter extends ArrayAdapter<MainListItem> {

    public MainAdapter(Context context, int resource, List<MainListItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.main_list_item, parent, false);

        }

        MainListItem element = getItem(position);

        if (element != null) {

            ImageView imageView = (ImageView) v.findViewById(R.id.main_list_image);
            TextView textView = (TextView) v.findViewById(R.id.main_list_text);

            if (imageView != null) {
                imageView.setImageResource(element.getImageResource());
            }

            if (textView != null) {
                textView.setText(element.getText());
            }
        }

        return v;
    }

}