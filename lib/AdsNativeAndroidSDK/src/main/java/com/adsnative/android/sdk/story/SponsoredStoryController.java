package com.adsnative.android.sdk.story;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adsnative.android.sdk.WebViewActivity;
import com.adsnative.android.sdk.request.AdRequest;

/**
 * Controller and renderer of the SponsoredStories attached to ListView
 */
public class SponsoredStoryController {

    private final Context context;
    private List<String> impressionsList;
    private List<SponsoredStory> sponsoredStories;
    private OnSponsoredStoryListener onSponsoredStoryListener;

    /**
     * Constructor
     *
     * @param context context of an Application
     */
    public SponsoredStoryController(Context context) {
        this.context = context;
        this.impressionsList = new ArrayList<String>();
        this.sponsoredStories = new ArrayList<SponsoredStory>();
    }

    /**
     * Fetches {@link com.adsnative.android.sdk.story.SponsoredStory} without any keywords
     *
     * @param adUnitId AdsNative user ID
     * @return fetched {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public SponsoredStory fetchSponsoredStory(String adUnitId) {
        return this.fetchSponsoredStory(adUnitId, null);
    }

    /**
     * Fetches {@link com.adsnative.android.sdk.story.SponsoredStory} with specified keywords
     *
     * @param adUnitId          AdsNative user ID
     * @param adRequestKeywords list of keywords to attach to {@link com.adsnative.android.sdk.request.AdRequest}
     * @return fetched {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public SponsoredStory fetchSponsoredStory(String adUnitId, List<String> adRequestKeywords) {
        return fetchSponsoredStory( new AdRequest(adUnitId, adRequestKeywords) );
    }
    
    /**
     * Fetches {@link com.adsnative.android.sdk.story.SponsoredStory} with specified AdRequest
     *
     * @param AdRequest the {@link com.adsnative.android.sdk.request.AdRequest} used to load the ads
     * @return fetched {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public SponsoredStory fetchSponsoredStory( AdRequest adRequest ) {
        final SponsoredStory sponsoredStory = new SponsoredStory( adRequest, context);
        sponsoredStory.loadRequest();
        sponsoredStory.setOnSponsoredStoryDataListener(new OnSponsoredStoryDataListener() {
            @Override
            public void onSponsoredStoryData() {
                addSponsoredStory(sponsoredStory);
            }

            @Override
            public void onFailure(FailureMessage failureMessage) {
                onSponsoredStoryListener.onFailure(failureMessage);
            }
        });
        return sponsoredStory;
    }

    /**
     * Adds specified SponsoredStory to the property list of SponsoredStories
     *
     * @param sponsoredStory provided {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    private void addSponsoredStory(SponsoredStory sponsoredStory) {
        this.sponsoredStories.add(sponsoredStory);
        onSponsoredStoryListener.onSponsoredStory(sponsoredStory);
    }

    /**
     * Sets OnSponsoredStoryListener to know when the story is completely added to the {@link SponsoredStoryController}
     *
     * @param onSponsoredStoryListener
     */
    public void setOnSponsoredStoryListener(OnSponsoredStoryListener onSponsoredStoryListener) {
        this.onSponsoredStoryListener = onSponsoredStoryListener;
    }

    /**
     * Gets OnSponsoredStoryListener
     */
    public OnSponsoredStoryListener getOnSponsoredStoryListener() {
        return this.onSponsoredStoryListener;
    }

    /**
     * * Check {@link SponsoredStoryController}.getSponsoredStoryView(SponsoredStory sponsoredStory)
     *
     * @return default View combined with recently fetched {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public View getSponsoredStoryView() {
        if (sponsoredStories.size() > 0) {
            return this.getSponsoredStoryView(sponsoredStories.get(sponsoredStories.size() - 1));
        }
        return null;
    }

    /**
     * * Check {@link SponsoredStoryController}.getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView)
     *
     * @param sponsoredStory provided {@link com.adsnative.android.sdk.story.SponsoredStory}
     * @return default View combined with specified {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public View getSponsoredStoryView(SponsoredStory sponsoredStory) {
        return this.getSponsoredStoryView(sponsoredStory, null);
    }

    /**
     * Check {@link SponsoredStoryController}.getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView, ViewGroup parent)
     *
     * @param sponsoredStory provided {@link com.adsnative.android.sdk.story.SponsoredStory}
     * @param convertView    must be an intance of {@link android.widget.RelativeLayout}, if is {@code null} default layout will be rendered for View
     * @return specified View combined with specified {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public View getSponsoredStoryView(SponsoredStory sponsoredStory, View convertView) {
        return this.getSponsoredStoryView(sponsoredStory, convertView, null);
    }

    /**
     * Uses getStoryView(SponsoredStoryData) to render default layout of {@link com.adsnative.android.sdk.story.SponsoredStory}.
     * Method also maps {@link com.adsnative.android.sdk.story.SponsoredStory} to its proper View. It sets SponsoredStory click listener
     * and logs impression by displaying 1x1 drop pixel.
     *
     * @param sponsoredStory provided {@link com.adsnative.android.sdk.story.SponsoredStory}
     * @param convertView    must be an intance of {@link android.widget.RelativeLayout}, if {@code null} default layout will be rendered for View
     * @param parent         if {@code null} generated View is not going to be attached to any parent, if convertView is already attached to any parent it's going to be removed and attached to the specified one
     * @return specified View attached to specified parent combined with specified {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public View getSponsoredStoryView(final SponsoredStory sponsoredStory, View convertView, ViewGroup parent) {
        if (sponsoredStory.getSponsoredStoryData() == null)
            return new View(context);

        View view = convertView;
        if (view == null || (view.getTag() == null || !view.getTag().equals(sponsoredStory.getSponsoredStoryData().getSessionId()))) {
            view = getStoryView(sponsoredStory.getSponsoredStoryData());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.open(context, sponsoredStory.getSponsoredStoryData() );
            }
        });

        String sid = sponsoredStory.getSponsoredStoryData().getSessionId();
        if (!impressionsList.contains(sid)) {
            if (!sponsoredStory.getSponsoredStoryData().getTrackingTags().isEmpty())
                ((RelativeLayout) view).addView(getImpressionPixel(sponsoredStory.getSponsoredStoryData()));
            impressionsList.add(sid);
        }

        if (parent != null) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            parent.addView(view);
        }

        return view;
    }

    /**
     * Provides 1x1 drop pixel WebView
     *
     * @param sponsoredStoryData provided {@link com.adsnative.android.sdk.story.SponsoredStoryData}
     * @return created 1x1 size WebView
     */
    private View getImpressionPixel(SponsoredStoryData sponsoredStoryData) {
        WebView webView = new WebView(context);
        webView.setLayoutParams(new ViewGroup.LayoutParams(1, 1));
        webView.setWebViewClient(new TrackingPixelWebViewClient());
        webView.loadData(sponsoredStoryData.getTrackingTags(), "text/html", null);
        
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType( View.LAYER_TYPE_SOFTWARE, null);
        }
        
        return webView;
    }

    /**
     * Renders proper View for specified SponsoredStoryData
     *
     * @param sponsoredStoryData
     * @return rendered View
     */
    private View getStoryView(SponsoredStoryData sponsoredStoryData) {
        float density = context.getResources().getDisplayMetrics().density;

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int m = (int) density * 15;
        imageParams.setMargins(m, m, m, m);
        imageView.setLayoutParams(imageParams);
        imageView.setImageBitmap(sponsoredStoryData.getThumbnailBitmap());
        linearLayout.addView(imageView);

        LinearLayout textLayout = new LinearLayout(context);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textLayoutParams.weight = 1;
        textLayoutParams.setMargins(m, m, m, m);
        textLayout.setLayoutParams(textLayoutParams);

        TextView title = new TextView(context);
        title.setSingleLine(true);
        title.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setLayoutParams(titleParams);
        title.setText(sponsoredStoryData.getTitle());
        textLayout.addView(title);

        TextView byLine = new TextView(context);
        byLine.setSingleLine(true);
        byLine.setTextColor(Color.LTGRAY);
        LinearLayout.LayoutParams byLineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        byLine.setLayoutParams(byLineParams);
        byLine.setText("by " + sponsoredStoryData.getPromotedBy());
        textLayout.addView(byLine);

        linearLayout.addView(textLayout);
        linearLayout.setBackgroundColor(Color.parseColor(sponsoredStoryData.getBackgroundColor()));
        relativeLayout.addView(linearLayout);

        relativeLayout.setTag(sponsoredStoryData.getSessionId());
        return relativeLayout;
    }

    /**
     * Clears all SponsoredStory mappings
     */
    public void clearSponsoredStories() {
        this.sponsoredStories.clear();
        this.impressionsList.clear();
    }

    /**
     * Return the amount of fetched SponsoredStory
     *
     * @return number of fetched SponsoredStory
     */
    public int getSponsoredStoriesCount() {
        return this.sponsoredStories.size();
    }
}
