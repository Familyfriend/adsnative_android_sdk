package com.adsnative.android.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adsnative.android.sdk.request.LogTimeRequest;
import com.adsnative.android.sdk.story.StoryWebViewClient;

import java.util.Calendar;

/**
 * Activity for displaying WebView and log time after clicking the ad.
 */

public class WebViewActivity extends Activity {

    private WebView webView;
    private String creativeId;
    private String sessionId;
    private String url;
    private long startTime;
    private long endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = getIntent();
        creativeId = intent.getStringExtra("crid");
        sessionId = intent.getStringExtra("sid");
        url = intent.getStringExtra("url");
        RelativeLayout relativeLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.getSettings().setJavaScriptEnabled(true);
        relativeLayout.addView(webView);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams progressBarParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setLayoutParams(progressBarParams);
        relativeLayout.addView(progressBar);

        setContentView(relativeLayout, layoutParams);

        webView.setWebViewClient(new StoryWebViewClient(progressBar));
        webView.loadUrl(url);
    }

    /**
     * Get time when Activity starts
     */
    @Override
    protected void onResume() {
        super.onResume();
        startTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Get time when Activity is no longer visible and log time to server using
     * {@link com.adsnative.android.sdk.WebViewActivity.LogTime} Task
     */
    @Override
    protected void onPause() {
        super.onPause();
        endTime = Calendar.getInstance().getTimeInMillis();
        new LogTime(creativeId, sessionId).execute(endTime - startTime);
    }

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
