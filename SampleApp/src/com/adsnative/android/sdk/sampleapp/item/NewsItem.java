package com.adsnative.android.sdk.sampleapp.item;

import android.graphics.Bitmap;

public class NewsItem {

    private String url;
    private String title;
    private String byLine;
    private Bitmap bitmap;

    public NewsItem(String url, String title, String byLine, Bitmap bitmap) {
        this.url = url;
        this.title = title;
        this.byLine = byLine;
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getByLine() {
        return byLine;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
