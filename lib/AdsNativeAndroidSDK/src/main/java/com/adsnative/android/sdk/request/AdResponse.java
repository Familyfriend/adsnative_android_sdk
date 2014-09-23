package com.adsnative.android.sdk.request;

import android.util.Log;

import com.adsnative.android.sdk.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base class for API response.
 */
public class AdResponse {

    protected String status;
    protected String error;
    protected JSONObject data;

    /**
     * Constructor
     *
     * @param data JSONObject received as a response from API
     */
    public AdResponse(JSONObject data) {
        this.data = data;
        try {
            this.status = data.getString("status");
        } catch (JSONException e) {
            Log.e(Constants.ERROR_TAG, e.getMessage());
        }
    }

    /**
     * Status getter
     *
     * @return status string value of the server response
     */
    public String getStatus() {
        return this.status;
    }
}
