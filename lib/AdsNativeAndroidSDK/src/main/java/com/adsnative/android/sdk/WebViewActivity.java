package com.adsnative.android.sdk;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adsnative.android.sdk.request.LogTimeRequest;
import com.adsnative.android.sdk.story.SponsoredStoryData;
import com.adsnative.android.sdk.story.StoryWebViewClient;

/**
 * Activity for displaying WebView and log time after clicking the ad.
 */
public class WebViewActivity extends Activity {
    
    /***************************************************
     * STATIC ATTRIBUTE
     ****************************************************/
    
    private final static String EXTRA_CREATIVE_ID = "crid";
    private final static String EXTRA_SESSION_ID = "sid";
    private final static String EXTRA_URL = "url";
    private final static String EXTRA_REDIRECT_PREFIX_URL_LIST = WebViewActivity.class.getName() + "EXTRA_REDIRECT_PREFIX_URL_LIST";
    
    /***************************************************
     * STATIC METHODS
     ****************************************************/

    public static void open( Context context, SponsoredStoryData data ) {
        open(context, data, null);
    }
    
    public static void open(Context context, SponsoredStoryData data, ArrayList<String> redirectUrls) {
        if (context == null || data == null)
            return;

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_CREATIVE_ID, data.getCreativeId());
        intent.putExtra(EXTRA_SESSION_ID, data.getSessionId());
        intent.putExtra(EXTRA_URL, data.getUrl());

        if (redirectUrls != null && !redirectUrls.isEmpty()) {
            intent.putExtra(EXTRA_REDIRECT_PREFIX_URL_LIST, redirectUrls);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    /***************************************************
     * ATTRIBUTES
     ****************************************************/
    
    //VIEW
    private WebView webView;
    
    //DATA
    private String mCreativeId;
    private String mSessionId;
    private String mUrl;
    private ArrayList<String> mRedirectUrls;
    
    //TIME
    private long mStartTime;
    private long mEndTime;

    /***************************************************
     * LIFECYCLE
     ****************************************************/
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = getIntent();
        mCreativeId = intent.getStringExtra( EXTRA_CREATIVE_ID );
        mSessionId = intent.getStringExtra( EXTRA_SESSION_ID );
        mUrl = intent.getStringExtra( EXTRA_URL );
        mRedirectUrls = intent.getStringArrayListExtra( EXTRA_REDIRECT_PREFIX_URL_LIST );
        
        RelativeLayout relativeLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.getSettings().setJavaScriptEnabled(true);

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType( View.LAYER_TYPE_SOFTWARE, null);
        }

        relativeLayout.addView(webView);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams progressBarParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setLayoutParams(progressBarParams);
        relativeLayout.addView(progressBar);

        setContentView(relativeLayout, layoutParams);

        webView.setWebViewClient(new StoryWebViewClient(progressBar, mRedirectUrls));
        webView.loadUrl(mUrl);
    }

    /**
     * Get time when Activity starts
     */
    @Override
    protected void onResume() {
        super.onResume();
        mStartTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Get time when Activity is no longer visible and log time to server using
     * {@link com.adsnative.android.sdk.WebViewActivity.LogTime} Task
     */
    @Override
    protected void onPause() {
        super.onPause();
        mEndTime = Calendar.getInstance().getTimeInMillis();
        new LogTime(mCreativeId, mSessionId).execute(mEndTime - mStartTime);
    }
    
    /***************************************************
     * INNER CLASSES
     ****************************************************/

    /**
     * Background task for logging time to server. execute @param is amount of time to be logged
     */
    private class LogTime extends AsyncTask<Long, Void, Integer> {

        private String creativeId;
        private String sessionId;

        public LogTime(String creativeId, String sessionId) {
            this.creativeId = creativeId;
            this.sessionId = sessionId;
        }

        @Override
        protected Integer doInBackground(Long... params) {
            return new LogTimeRequest(params[0], creativeId, sessionId).logTime();
        }

        @Override
        protected void onPostExecute(Integer code) {
            super.onPostExecute(code);
            if (code == 200)
                Log.d("AdsNative", "Time logged");
        }
    }
}
