package com.adsnative.android.sdk.story;

/**
 * Container class for Failure data fetched from server and parsed from JSON response
 */

public class FailureMessage {
    private String message;
    private String uuid;
    private String zid;

    public FailureMessage() {
    }

    public FailureMessage(String message) {
        this.message = message;
        uuid = "";
        zid = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }
}
