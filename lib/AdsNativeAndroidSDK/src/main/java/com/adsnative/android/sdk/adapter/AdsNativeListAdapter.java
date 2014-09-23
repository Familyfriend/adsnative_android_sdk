package com.adsnative.android.sdk.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.adsnative.android.sdk.story.FailureMessage;
import com.adsnative.android.sdk.story.OnSponsoredStoryDataListener;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Adapter for ListView for proper displaying SponsoredStories
 *
 * @param <T>
 */
public class AdsNativeListAdapter<T extends ListAdapter> extends BaseAdapter {

    private T originalAdapter;
    private List<Integer> sponsoredStoriesPositions;
    private String adUnitId;
    private PositionController positionController;
    private SponsoredStoryController sponsoredStoryController;
    private List<String> adRequestKeywords;

    /**
     * Constructor initializes properties and converts table of integers into List and sorts it in
     * ascending way. It also registers data observer for original adapter.
     *
     * @param context                   context of an Application
     * @param originalAdapter           instance of an original adapter of the ListView
     * @param sponsoredStoriesPositions positions of the {@link com.adsnative.android.sdk.story.SponsoredStory} on the ListView, indexing starts with '0'
     * @param adUnitId                  AdsNative user ID
     */
    public AdsNativeListAdapter(Context context, T originalAdapter, int[] sponsoredStoriesPositions, String adUnitId) {
        this.originalAdapter = originalAdapter;
        this.sponsoredStoriesPositions = new ArrayList<Integer>();
        for (Integer i : sponsoredStoriesPositions) {
            if (i >= 0 && !this.sponsoredStoriesPositions.contains(i)) {
                this.sponsoredStoriesPositions.add(i);
            }
        }
        Collections.sort(this.sponsoredStoriesPositions);
        this.positionController = new PositionController(this.originalAdapter.getCount());
        this.sponsoredStoryController = new SponsoredStoryController(context);
        this.adUnitId = adUnitId;
        originalAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                AdsNativeListAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                AdsNativeListAdapter.this.notifyDataSetInvalidated();
            }
        });
    }

    /**
     * Base constructor with additional option of putting a list of {@link com.adsnative.android.sdk.request.AdRequest} keywords
     *
     * @param context                   context of an Application
     * @param originalAdapter           instance of an original adapter of the ListView
     * @param sponsoredStoriesPositions positions of the {@link com.adsnative.android.sdk.story.SponsoredStory} on the ListView, indexing starts with '0'
     * @param adUnitId                  AdsNative user ID
     * @param adRequestKeywords         list of keywords to attach to {@link com.adsnative.android.sdk.request.AdRequest}
     */
    public AdsNativeListAdapter(Context context, T originalAdapter, int[] sponsoredStoriesPositions, String adUnitId, List<String> adRequestKeywords) {
        this(context, originalAdapter, sponsoredStoriesPositions, adUnitId);
        this.adRequestKeywords = new ArrayList<String>();
        this.adRequestKeywords = adRequestKeywords;
    }

    /**
     * Base constructor with additional option of putting an array of {@link com.adsnative.android.sdk.request.AdRequest} keywords
     *
     * @param context                   context of an Application
     * @param originalAdapter           instance of an original adapter of the ListView
     * @param sponsoredStoriesPositions positions of the {@link com.adsnative.android.sdk.story.SponsoredStory} on the ListView, indexing starts with '0'
     * @param adUnitId                  AdsNative user ID
     * @param adRequestKeywords         an array of keywords to attach to {@link com.adsnative.android.sdk.request.AdRequest}
     */
    public AdsNativeListAdapter(Context context, T originalAdapter, int[] sponsoredStoriesPositions, String adUnitId, String[] adRequestKeywords) {
        this(context, originalAdapter, sponsoredStoriesPositions, adUnitId);
        this.adRequestKeywords = new ArrayList<String>();
        for (String s : adRequestKeywords)
            this.adRequestKeywords.add(s);
    }

    /**
     * Updates positions of items of the list and notifies ListView about it.
     */
    private void internalNotifyDataSetChanged() {
        this.positionController.updateLists();
        super.notifyDataSetChanged();
    }

    /**
     * Triggered when data changes in originalAdapter and there was call of .notifyDataSetChanged() on
     * original adapter. It also updates original data in AdsNativeListAdapter.positionController.
     */
    public void notifyDataSetChanged() {
        this.positionController.updateOriginalSize(this.originalAdapter.getCount());
        internalNotifyDataSetChanged();
    }

    /**
     * Clears all Ads attached to ListView
     */
    public void clearSponsoredStories() {
        this.sponsoredStoryController.clearSponsoredStories();
        this.positionController.clearSponsoredStories();
        internalNotifyDataSetChanged();
    }

    /**
     * Fetches and loads SponsoredStories to positions specified by Constructor.
     */
    public void loadSponsoredStories() {
        this.sponsoredStoryController.clearSponsoredStories();
        if (sponsoredStoriesPositions.size() > 0) {
            for (int i = 0; i < sponsoredStoriesPositions.size(); i++) {
                final int position = sponsoredStoriesPositions.get(i);
                final SponsoredStory sponsoredStory = sponsoredStoryController.fetchSponsoredStory(adUnitId, adRequestKeywords);
                addSponsoredStory(sponsoredStory, position);
                sponsoredStory.setOnSponsoredStoryDataListener(new OnSponsoredStoryDataListener() {
                    @Override
                    public void onSponsoredStoryData() {
                        AdsNativeListAdapter.super.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(FailureMessage failureMessage) {
                    }
                });
            }
        }
    }

    /**
     * Adds SponsoredStory into specified position, then check if there are any
     * {@link com.adsnative.android.sdk.story.SponsoredStory} left to fetch
     *
     * @param sponsoredStory specified {@link com.adsnative.android.sdk.story.SponsoredStory} to be added to the ListView
     * @param position       specified position
     */
    private void addSponsoredStory(SponsoredStory sponsoredStory, int position) {
        this.positionController.insertSponsoredStory(sponsoredStory, position);
        this.internalNotifyDataSetChanged();
    }

    /**
     * SponsoredStoriesPositions list getter
     *
     * @return SponsoredStoriesPositions list
     */
    public List<Integer> getSponsoredStoriesPositions() {
        return this.sponsoredStoriesPositions;
    }

    /**
     * Returns the size of the whole list attached to ListView.
     *
     * @return the size of the whole list attached ListView
     */
    @Override
    public int getCount() {
        return this.positionController.getAdjustedCount();
    }

    /**
     * Returns the amount of the object types included into ListView.
     *
     * @return the amount of the object types included into ListView
     */
    public int getViewTypeCount() {
        return this.originalAdapter.getViewTypeCount() + 1;
    }

    /**
     * Gets code of the object type at specified position of the ListView.
     * The type code of SponsoredStory is '0'.
     *
     * @param position of the object
     * @return code of the object type at position.
     */
    public int getItemViewType(int position) {
        return this.positionController.isAd(position) ? 0 : this.originalAdapter.getItemViewType(this.positionController.getOriginalPosition(position)) + 1;
    }

    /**
     * Gets the object at specified position of the ListView.
     *
     * @param position of the object
     * @return object at position of the ListView.
     */
    @Override
    public Object getItem(int position) {
        SponsoredStory sponsoredStory = this.positionController.getSponsoredStory(position);
        return sponsoredStory != null ? sponsoredStory : this.originalAdapter.getItem(this.positionController.getOriginalPosition(position));
    }

    /**
     * Gets id of the ListView item at specified position.
     *
     * @param position of the object
     * @return -1 if there is SponsoredStory at position, otherwise returns mapped position of the original item
     */
    @Override
    public long getItemId(int position) {
        if (this.positionController.isAd(position))
            return -1;
        else
            return this.positionController.getOriginalPosition(position);
    }

    /**
     * Provides View to be displayed at specified position.
     *
     * @param position    of a View
     * @param convertView
     * @param parent
     * @return View to be displayed at specified position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SponsoredStory sponsoredStory = this.positionController.getSponsoredStory(position);
        if (sponsoredStory == null) {
            int originalPosition = this.positionController.getOriginalPosition(position);
            return this.originalAdapter.getView(originalPosition, convertView, parent);
        }
        return this.sponsoredStoryController.getSponsoredStoryView(this.positionController.getSponsoredStory(position), convertView);
    }
}


