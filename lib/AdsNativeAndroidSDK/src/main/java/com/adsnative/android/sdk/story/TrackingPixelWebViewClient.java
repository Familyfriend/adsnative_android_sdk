package com.adsnative.android.sdk.story;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TrackingPixelWebViewClient extends WebViewClient {

    /**
     * Empty constructor for 1x1 drop pixel WebView.
     */
    public TrackingPixelWebViewClient() {}

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    /**
     * If progress bar is not {@code null} it is hided when the page id loaded
     * 
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    /**
     * Page stops loading when receives any error
     * 
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.stopLoading();
    }

}
