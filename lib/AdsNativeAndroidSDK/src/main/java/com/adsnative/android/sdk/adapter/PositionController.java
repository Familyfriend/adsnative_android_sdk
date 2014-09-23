package com.adsnative.android.sdk.adapter;

import com.adsnative.android.sdk.story.SponsoredStory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controls positions of SponsoredStories and original data inside ListView.
 * Adjusted positions of items of original list are mapped in originalPositionList.
 */
public class PositionController {
    private List<SponsoredStory> sponsoredStoriesList;
    private List<Integer> sponsoredStoriesPositionsList;
    private List<Integer> sponsoredStoriesPositionsListAdjusted;
    private HashMap<Integer, Integer> originalPositionsList;
    private int originalSize;
    private int mergedListSize;

    /**
     * Constructor initialize properties and maps in 1:1 ratio default original positions for specified size.
     *
     * @param originalSize original size of ListView (without SponsoredStories)
     */
    public PositionController(int originalSize) {
        this.originalSize = originalSize;
        this.mergedListSize = originalSize;
        this.sponsoredStoriesList = new ArrayList<SponsoredStory>();
        this.sponsoredStoriesPositionsList = new ArrayList<Integer>();
        this.sponsoredStoriesPositionsListAdjusted = new ArrayList<Integer>();
        this.originalPositionsList = new HashMap<Integer, Integer>();
        for (int i = 0; i < this.originalSize; i++) {
            this.originalPositionsList.put(i, i);
        }
    }

    /**
     * Original size setter
     *
     * @param originalSize specified size
     */
    private void setOriginalSize(int originalSize) {
        this.originalSize = originalSize;
    }

    /**
     * Updates size of the original list.
     * Creates default mapping in 1:1 ratio for new originals.
     * Adjusts positions of SponsoredStories.
     *
     * @param newSize new size of the original list
     */
    public void updateOriginalSize(int newSize) {
        setOriginalSize(newSize);
        createDefaultOriginals();
        adjustSponsoredStoriesPositionsList();
    }

    /**
     * Adjusted count getter.
     * Provides total size of merged list of original items and SponsoredStories.
     *
     * @return size of merged lists
     */
    public int getAdjustedCount() {
        return this.mergedListSize;
    }

    /**
     * Original position getter. Provides properly mapped position of original item of the list.
     *
     * @param position current position on the list with {@link com.adsnative.android.sdk.story.SponsoredStory} attached
     * @return mapped position (original position)
     */
    public int getOriginalPosition(int position) {
        return originalPositionsList.get(position);
    }

    /**
     * SponsoredStoriesPositionsListAdjusted getter. Provides list of adjusted positions of the {@link com.adsnative.android.sdk.story.SponsoredStory}.
     *
     * @return list of adjusted positions of the {@link com.adsnative.android.sdk.story.SponsoredStory}
     */
    public List<Integer> getSponsoredStoriesPositionsListAdjusted() {
        return this.sponsoredStoriesPositionsListAdjusted;
    }

    /**
     * Checks if there is a SponsoredStory at specified position.
     *
     * @param position
     * @return {@code true} if there is SponsoredStory at @param position, otherwise {@code false}
     */
    public boolean isAd(int position) {
        return sponsoredStoriesPositionsListAdjusted.contains(position);
    }

    /**
     * Provides SponsoredStory from specified position of the ListView.
     *
     * @param position specified position
     * @return SponsoredStory if there is one in @param position, otherwise {@code null}
     */
    public SponsoredStory getSponsoredStory(int position) {
        if (isAd(position)) {
            int index = 0;
            for (Integer i : sponsoredStoriesPositionsListAdjusted) {
                if (i == position)
                    return sponsoredStoriesList.get(index);
                index++;
            }
        }
        return null;
    }

    /**
     * Clears all SponsoredStories and sets default/original values.
     */
    public void clearSponsoredStories() {
        sponsoredStoriesList.clear();
        sponsoredStoriesPositionsList.clear();
        sponsoredStoriesPositionsListAdjusted.clear();
        createDefaultOriginals();
        mergedListSize = originalSize;
    }

    /**
     * Creates default mapping for original positions in 1:1 ratio.
     */
    private void createDefaultOriginals() {
        this.originalPositionsList.clear();
        for (int i = 0; i < this.originalSize; i++) {
            this.originalPositionsList.put(i, i);
        }
    }

    /**
     * Replaces list of SponsoredStories accordingly to positions specified in second parameter.
     *
     * @param newSponsoredStories
     * @param newPositions
     */
    public void replaceSponsoredStories(List<SponsoredStory> newSponsoredStories, List<Integer> newPositions) {

        if (newSponsoredStories.size() != newPositions.size())
            return;

        clearSponsoredStories();
        sponsoredStoriesList = newSponsoredStories;
        sponsoredStoriesPositionsList = newPositions;
        adjustSponsoredStoriesPositionsList();
    }

    /**
     * Adjusts all SponsoredStories positions.
     * If any position of SponsoredStory is bigger than original list size
     * it will be placed in the end of the list.
     */
    private void adjustSponsoredStoriesPositionsList() {
        this.sponsoredStoriesPositionsListAdjusted.clear();
        mergedListSize = originalSize;
        for (Integer i : sponsoredStoriesPositionsList) {
            mergedListSize++;
            if (i > mergedListSize - 1)
                i = mergedListSize - 1;
            sponsoredStoriesPositionsListAdjusted.add(i);
        }
    }

    /**
     * Inserts single SponsoredStory into specified position.
     *
     * @param sponsoredStory specified {@link com.adsnative.android.sdk.story.SponsoredStory} to be added
     * @param position       specified position of the {@link com.adsnative.android.sdk.story.SponsoredStory} on the list
     */
    public void insertSponsoredStory(SponsoredStory sponsoredStory, int position) {
        sponsoredStoriesList.add(sponsoredStory);
        sponsoredStoriesPositionsList.add(position);
        adjustSponsoredStoriesPositionsList(position);
    }

    /**
     * Inserts specified {@link com.adsnative.android.sdk.story.SponsoredStory} position into {@code SponsoredStoriesPositionsListAdjusted}
     *
     * @param position specified position of the {@link com.adsnative.android.sdk.story.SponsoredStory} on the list
     */
    private void adjustSponsoredStoriesPositionsList(int position) {
        mergedListSize++;
        if (position > mergedListSize - 1) {
            position = mergedListSize - 1;
        }
        sponsoredStoriesPositionsListAdjusted.add(position);
    }

    /**
     * Updates mapping of original positions. It has to be done after any data changes on the ListView.
     */
    public void updateLists() {
        HashMap<Integer, Integer> tmpHashMap = new HashMap<Integer, Integer>();
        int range = mergedListSize - 1;
        for (Integer i : sponsoredStoriesPositionsListAdjusted) {

            if (originalPositionsList.containsKey(i)) {
                for (Integer j = i; j < range; j++) {
                    tmpHashMap.put(j, originalPositionsList.get(j));
                }

                for (Integer j = i; j < range; j++)
                    originalPositionsList.put(j + 1, tmpHashMap.get(j));

                originalPositionsList.remove(i);
                range++;
            }
        }
    }
}