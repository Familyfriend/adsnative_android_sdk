package com.adsnative.android.sdk.story;

import java.util.ArrayList;

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
    
    /***************************************************
     * ATTRIBUTES
     ****************************************************/
    
    private ProgressBar mProgressBar;
    private ArrayList<String> mRedirectPrefixUrls;

    /***************************************************
     * CONSTRUCTORS
     ****************************************************/
    
    /**
     * Constructor for WebViews displaying content after clicking on Ad
     *
     * @param progressBar to be hided after loading finishes
     */
    public StoryWebViewClient(ProgressBar progressBar) {
        this( progressBar, null );
    }
    
    /**
     * Constructor for WebViews displaying content after clicking on Ad
     *
     * @param progressBar to be hided after loading finishes
     * @param redirectPrefixUrls if the url loaded by the webView starts with one of these strings will be redirected to the android system trough an intent
     */
    public StoryWebViewClient(ProgressBar progressBar, ArrayList<String> redirectPrefixUrls) {
        mProgressBar = progressBar;
        mRedirectPrefixUrls = redirectPrefixUrls;
    }
    
    /***************************************************
     * CALLBACKS
     ****************************************************/

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
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }
    
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            if ( redirect( url ) ) {
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
    
    /***************************************************
     * CLASS METHOD
     ****************************************************/
    
    private boolean redirect( String url ) {
        boolean redirect = url.startsWith("https://play.google.com");
        redirect |= ( !url.startsWith("http://") && !url.startsWith("https://") );
        
        if( mRedirectPrefixUrls != null && !mRedirectPrefixUrls.isEmpty() )
            for( String prefix : mRedirectPrefixUrls )
                redirect |= url.startsWith(prefix);
        
        return redirect;
    }
}
