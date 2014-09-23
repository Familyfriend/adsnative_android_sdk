package com.adsnative.android.sdk.testapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adsnative.android.sdk.story.FailureMessage;
import com.adsnative.android.sdk.story.OnSponsoredStoryListener;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryController;

public class CustomViewsActivity extends Activity {
    private static final String ERROR_TAG = "error";
    private TextView textView;
    private RelativeLayout parent;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        parent = (RelativeLayout) findViewById(R.id.linear_main);
        textView = (TextView) findViewById(R.id.test_text);
        relativeLayout = (RelativeLayout) findViewById(R.id.sponsored);


        final SponsoredStoryController sponsoredStoryControllerT = new SponsoredStoryController(getBaseContext());
        sponsoredStoryControllerT.fetchSponsoredStory("Uw8JRh5gifh9sxZKZ-IRgVC0WNcgOGWxSyEFjObs");
        sponsoredStoryControllerT.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
            @Override
            public void onSponsoredStory(SponsoredStory sponsoredStory) {
                textView.setText("This text is a sponsored story! Click on me!\n" + sponsoredStory.getSponsoredStoryData().getTitle());
                relativeLayout.setBackgroundColor(Color.YELLOW);
                relativeLayout.setTag(sponsoredStory.getSponsoredStoryData().getSessionId());
                sponsoredStoryControllerT.getSponsoredStoryView(sponsoredStory, relativeLayout, parent);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                Log.d(ERROR_TAG, failureMessage.getMessage());
            }
        });

        final SponsoredStoryController sponsoredStoryController = new SponsoredStoryController(getBaseContext());
        sponsoredStoryController.fetchSponsoredStory("Uw8JRh5gifh9sxZKZ-IRgVC0WNcgOGWxSyEFjObs");
        sponsoredStoryController.setOnSponsoredStoryListener(new OnSponsoredStoryListener() {
            @Override
            public void onSponsoredStory(SponsoredStory sponsoredStory) {
                View view = sponsoredStoryController.getSponsoredStoryView(sponsoredStory);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                parent.addView(view, layoutParams);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                Log.e(ERROR_TAG, failureMessage.getMessage());
            }
        });


    }
}
