package com.adsnative.android.sdk.story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Handles displaying WebViews and manages progress bar while loading the page content.
 */

public class StoryWebViewClient extends WebViewClient {
    
    private ProgressBar progressBar;

    /**
     * Empty constructor for 1x1 drop pixel WebView.
     */
    public StoryWebViewClient() {
    }

    /**
     * Constructor for WebViews displaying content after clicking on Ad
     *
     * @param progressBar to be hided after loading finishes
     */
    public StoryWebViewClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

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
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
    
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            if ( url.startsWith("https://play.google.com") || ( !url.startsWith("http://") && !url.startsWith("https://") ) ) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).finish();
                return true;
            }
        } catch (Exception e) {
            return false;

        }

        return false;
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
