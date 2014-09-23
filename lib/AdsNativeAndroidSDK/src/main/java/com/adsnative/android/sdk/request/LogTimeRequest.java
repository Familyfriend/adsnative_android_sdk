package com.adsnative.android.sdk.request;

import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Builds url for time logging request and performs GET call to API
 */
public class LogTimeRequest {
    private long loggedTime;
    private String creativeId;
    private String sessionId;

    /**
     * Constructor
     *
     * @param loggedTime
     * @param creativeId
     * @param sessionId
     */
    public LogTimeRequest(long loggedTime, String creativeId, String sessionId) {
        this.loggedTime = loggedTime;
        this.creativeId = creativeId;
        this.sessionId = sessionId;
    }

    /**
     * Builds parameters for url
     *
     * @return parameters
     */
    protected String getParams() {
        return "crid=" + this.creativeId + "&sid=" + this.sessionId + "&time_spent=" + loggedTime;
    }

    /**
     * Builds complete url for logging time request
     *
     * @return complete url
     */
    private String getUrl() {
        return "http://" + Constants.URL_HOST + "/" + Constants.VERSION + "/log/time?" + getParams();
    }


    /**
     * Performs GET logging time request operation on API and returns response code
     *
     * @return response code
     */
    public int logTime() {
        try {
            return HttpRequest.get(getUrl()).code();
        } catch (HttpRequest.HttpRequestException exception) {
            Log.e(Constants.ERROR_TAG, exception.getMessage());
        }
        return 0;
    }
}
