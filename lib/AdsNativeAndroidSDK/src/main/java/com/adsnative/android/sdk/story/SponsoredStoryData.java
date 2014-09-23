package com.adsnative.android.sdk.story;

import android.graphics.Bitmap;

/**
 * Container class for SponsoredStory data fetched from server and parsed from JSON response
 */

public class SponsoredStoryData {

    private String title;
    private String trackingTags;
    private String summary;
    private String backgroundColor;
    private String brandImageUrl;
    private String url;
    private String type;
    private String promotedBy;
    private String promotedByTag;
    private String promotedByUrl;
    private String thumbnailUrl;
    private String embedUrl;
    private String creativeId;
    private String campaignId;
    private String sessionId;
    private String zoneId;
    private String uuid;
    private Bitmap thumbnailBitmap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrackingTags() {
        return trackingTags;
    }

    public void setTrackingTags(String trackingTags) {
        this.trackingTags = trackingTags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBrandImageUrl() {
        return brandImageUrl;
    }

    public void setBrandImageUrl(String brandImageUrl) {
        this.brandImageUrl = brandImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPromotedBy() {
        return promotedBy;
    }

    public void setPromotedBy(String promotedBy) {
        this.promotedBy = promotedBy;
    }

    public String getPromotedByTag() {
        return promotedByTag;
    }

    public void setPromotedByTag(String promotedByTag) {
        this.promotedByTag = promotedByTag;
    }

    public String getPromotedByUrl() {
        return promotedByUrl;
    }

    public void setPromotedByUrl(String promotedByUrl) {
        this.promotedByUrl = promotedByUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Bitmap getThumbnailBitmap() {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.thumbnailBitmap = thumbnailBitmap;
    }
}

