package com.adsnative.android.sdk.request;

import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.story.FailureMessage;
import com.adsnative.android.sdk.story.SponsoredStoryData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles and parse response from get operation requested on API
 */
public class GetSponsoredStoryResponse extends AdResponse {

    private int count;
    private SponsoredStoryData storyData;
    private FailureMessage failureMessage;

    /**
     * Constructor
     *
     * @param rawData - body of the response
     * @throws JSONException
     */
    public GetSponsoredStoryResponse(String rawData) throws JSONException {
        super(new JSONObject(rawData));
    }

    /**
     * Parses JSON data from response
     *
     * @return {@link com.adsnative.android.sdk.story.SponsoredStoryData} object with parsed data from response,
     * if something went wrong with parsing returns {@code null}
     */
    public SponsoredStoryData parseJson() {
        if (status.equals("OK")) {
            try {
                this.count = data.getInt("count");

                storyData = new SponsoredStoryData();

                JSONObject ad = data.getJSONObject("ad");

                if (ad != null) {
                    storyData.setTrackingTags(buildHtmlCode(ad.getString("trackingTags")));
                    if (ad.isNull("backgroundColor") || ad.getString("backgroundColor").isEmpty())
                        storyData.setBackgroundColor("#00FFFFFF");
                    else
                        storyData.setBackgroundColor(ad.getString("backgroundColor"));
                    storyData.setBrandImageUrl(checkHttp(ad.getString("brandImageUrl")));
                    storyData.setUrl(checkHttp(ad.getString("url")));
                    storyData.setTitle(ad.getString("title"));
                    storyData.setSummary(ad.getString("summary"));
                    storyData.setThumbnailUrl(checkHttp(ad.getString("imageSrc")));
                    storyData.setEmbedUrl(checkHttp(ad.getString("embedUrl")));
                    storyData.setType(ad.getString("type"));
                    storyData.setPromotedBy(ad.getString("promotedBy"));
                    storyData.setPromotedByTag(ad.getString("promotedByTag"));
                    storyData.setPromotedByUrl(checkHttp(ad.getString("promotedByUrl")));
                }

                storyData.setCampaignId(data.getString("cid"));
                storyData.setCreativeId(data.getString("crid"));
                storyData.setSessionId(data.getString("sid"));
                storyData.setUuid(data.getString("uuid"));
                storyData.setZoneId(data.getString("zid"));

                return storyData;
            } catch (JSONException e) {
                Log.e(Constants.ERROR_TAG, e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Parses JSON data from failure response
     *
     * @return {@link com.adsnative.android.sdk.story.FailureMessage} object with parsed data from failure response,
     * if something went wrong with parsing returns {@code null}
     */
    public FailureMessage getFailureMessage() {
        if (status.equalsIgnoreCase("FAIL")) {
            try {
                failureMessage = new FailureMessage();
                failureMessage.setMessage(data.getString("message"));
                failureMessage.setUuid(data.getString("uuid"));
                failureMessage.setZid(data.getString("zid"));

                return failureMessage;
            } catch (JSONException e) {
                Log.e(Constants.ERROR_TAG, e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Builds HTML String code from JSON response
     *
     * @param trackingTags - value of JSON field 'trackingTags'
     * @return build HTML string code
     */
    private String buildHtmlCode(String trackingTags) {
        return "<html><head></head><body>" + trackingTags + "</body></html>";
    }

    /**
     * Checks if specified string (usually url) starts with "http:"
     *
     * @param url - specified url
     * @return String with 'http:' in the beginning
     */
    private String checkHttp(String url) {
        if (url.startsWith("http:"))
            return url;
        else
            return "http:" + url;
    }
}