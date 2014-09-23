package com.adsnative.android.sdk.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for API requests.
 */
public class AdRequest {

    private static final String SDK_VERSION = "1.0";
    private String adUnitID;
    private List<String> keywords;
    private Map<String, String> parameters;
    private boolean downloadThumbnailBitmap = true;
    private boolean downloadBrandBitmap = true;

    /**
     * Constructor
     *
     * @param adUnitID AdsNative user ID
     */
    public AdRequest(String adUnitID) {
        this.adUnitID = adUnitID;
        keywords = new ArrayList<String>();
        parameters = new HashMap<String, String>();
    }

    /**
     * Constructor with a list of keywords
     *
     * @param adUnitID AdsNative user ID
     * @param keywords list of keywords
     */
    public AdRequest(String adUnitID, List<String> keywords) {
        this(adUnitID);
        if (keywords != null)
            this.keywords = keywords;
    }

    /**
     * SDK version getter
     *
     * @return sdk version string
     */
    public static String getSdkVersion() {
        return SDK_VERSION;
    }

    /**
     * AdUnitID getter
     *
     * @return AdsNative user ID
     */
    public String getAdUnitID() {
        return adUnitID;
    }

    /**
     * Puts keyword string to the list
     *
     * @param keyword
     * @return {@code true} if list has been modified with insertion, {@code false} otherwise
     */
    public boolean putKeyword(String keyword) {
        return keywords.add(keyword);
    }

    /**
     * Returns the list of keywords
     *
     * @return the list of keywords
     */
    public List<String> getKeywordsList() {
        return this.keywords;
    }

    /**
     * Removes first found keyword string from the list
     *
     * @param keyword
     * @return {@code true} if list has been modified with this operation, {@code false} otherwise
     */
    public boolean removeKeyword(String keyword) {
        return keywords.remove(keyword);
    }

    /**
     * Returns the size of keywords list
     *
     * @return the size of keywords list
     */
    public int getKeywordsListSize() {
        return this.keywords.size();
    }

    /**
     * Maps the specified key to the specified value.
     *
     * @param key
     * @param value
     * @return the value of any previous mapping with the specified key or
     * {@code null} if there was no mapping.
     */
    public String putParameter(String key, String value) {
        return parameters.put(key, value);
    }

    /**
     * Removes a mapping with the specified key.
     *
     * @param key
     * @return the value of the removed mapping or {@code null} if no mapping
     * for the specified key was found.
     */
    public String removeParameter(String key) {
        return parameters.remove(key);
    }
    
    /**
     * set if the thumbnail bitmap will be downloaded right before the success callback
     * 
     * @param download true to let the SDK download the thumbanil bitmap
     */
    public void setDownloadThumbnailBitmap( boolean download ) {
        downloadThumbnailBitmap = download;
    }
    
    /**
     * @return true if the sdk has to download the thumbnail bitmap
     */
    public boolean hasToDownloadThumbnailBitmap() {
        return downloadThumbnailBitmap;
    }
        
}
