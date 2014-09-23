package com.adsnative.android.sdk.sampleapp.item;

import android.graphics.Bitmap;

public class YouTubeItem {

    private String url;
    private String title;
    private Bitmap bitmap;

    public YouTubeItem(String url, String title, Bitmap bitmap) {
        this.url = url;
        this.title = title;
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
