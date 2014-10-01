package com.adsnative.android.sdk.request;

import java.io.UnsupportedEncodingException;

import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.device.DeviceInfo;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Builds url for sponsored story request and makes GET call to API
 */
public class GetSponsoredStoryRequest {

    private AdRequest adRequest;
    private String uuid;
    private DeviceInfo deviceInfo;

    /**
     * Constructor
     *
     * @param adRequest  base request with AdUnitID
     * @param uuid       value of AdvertisingId
     * @param deviceInfo object with all device info data
     */
    public GetSponsoredStoryRequest(AdRequest adRequest, String uuid, DeviceInfo deviceInfo) {
        this.adRequest = adRequest;
        this.uuid = uuid;
        this.deviceInfo = deviceInfo;
    }

    /**
     * Builds complete url for getting sponsored story request
     *
     * @return complete url for request
     */
    private String getUrl() throws UnsupportedEncodingException {

        Uri uri = Uri.parse("http://" + Constants.URL_HOST + "/" + Constants.VERSION + "/ad.json?");
        Builder uriBuilder = uri.buildUpon();
        
        String zid = adRequest.getAdUnitID();
        String ua = deviceInfo.getUserAgent();
        String al = deviceInfo.getLocale();
        String tz = deviceInfo.getTimeZone();
        String bd = deviceInfo.getConnectionType();
        String odin1 = deviceInfo.getODIN1();
        
        uriBuilder.appendQueryParameter("zid", zid);
        uriBuilder.appendQueryParameter("app", "1");
        uriBuilder.appendQueryParameter("us", ua);
        uriBuilder.appendQueryParameter("al", al);
        uriBuilder.appendQueryParameter("tz", tz);
        uriBuilder.appendQueryParameter("uuid", uuid);
        uriBuilder.appendQueryParameter("bd", bd);
        uriBuilder.appendQueryParameter("odin1", odin1);

        if (adRequest.getKeywordsListSize() > 0) {
            for (String s : adRequest.getKeywordsList())
                uriBuilder.appendQueryParameter("keywords[]", s);
        }
        
        return uriBuilder.build().toString();
    }

    /**
     * Performs GET sponsored story request operation on API and returns response
     *
     * @return response
     */
    public HttpRequest get() {
        try {
            return HttpRequest.get(getUrl());
        } catch (HttpRequest.HttpRequestException exception) {
            Log.e(Constants.ERROR_TAG, exception.getMessage());
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
