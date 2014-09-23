package com.adsnative.android.sdk.sampleapp.item;

public class MainListItem {
    private String text;
    private int imageResource;

    public MainListItem(String text, int imageResource) {
        this.text = text;
        this.imageResource = imageResource;
    }

    public String getText() {
        return text;
    }

    public int getImageResource() {
        return imageResource;
    }
}
