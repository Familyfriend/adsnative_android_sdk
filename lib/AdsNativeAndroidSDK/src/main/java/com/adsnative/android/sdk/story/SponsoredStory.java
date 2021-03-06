package com.adsnative.android.sdk.story;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.device.DeviceInfo;
import com.adsnative.android.sdk.device.GetAdvertisingId;
import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryResponse;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class handles fetching sponsored story and stores all data of sponsored story, IDs and device info
 */
public class SponsoredStory {

    private String uuid;
    private SponsoredStoryData sponsoredStoryData;
    private AdRequest adRequest;
    private Context context;
    private DeviceInfo deviceInfo;
    private OnSponsoredStoryDataListener onSponsoredStoryDataListener;

    /**
     * Constructor
     *
     * @param adRequest with AdUnitId
     * @param context   context of an Application
     */
    public SponsoredStory(AdRequest adRequest, Context context) {
        this.adRequest = adRequest;
        this.context = context;
        this.deviceInfo = new DeviceInfo(context);
        this.sponsoredStoryData = null;
    }

    /**
     * Fetches AdvertisingId from device and after that starts task that fetches sponsored story
     */
    protected void loadRequest() {
        new GetAdvertisingId(context) {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    setUuid(s);
                } else {
                    setUuid("");
                }
                new GetSponsoredStoryTask(adRequest, deviceInfo).execute();
            }
        }.execute();
    }

    /**
     * Uuid setter
     *
     * @param uuid id to be set
     */
    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Sponsored story data setter
     *
     * @param sponsoredStoryData data to be set
     */
    public void setSponsoredStoryData(SponsoredStoryData sponsoredStoryData) {
        this.sponsoredStoryData = sponsoredStoryData;
    }

    /**
     * Sponsored story data getter
     *
     * @return this sponsoredStoryData object
     */
    public SponsoredStoryData getSponsoredStoryData() {
        return sponsoredStoryData;
    }

    /**
     * Sets OnSponsoredStoryDataListener to know when the story is completely fetched and parsed
     *
     * @param onSponsoredStoryDataListener
     */
    public void setOnSponsoredStoryDataListener(OnSponsoredStoryDataListener onSponsoredStoryDataListener) {
        this.onSponsoredStoryDataListener = onSponsoredStoryDataListener;
    }

    /**
     * Fetching SponsoredStory task.
     * Triggers onSponsoredStoryData from {@link OnSponsoredStoryDataListener}
     * if SponsoredStory is completely fetched and data is correctly parsed. If SponsoredStoryData is null
     * nth is going to be triggered.
     */
    private class GetSponsoredStoryTask extends AsyncTask<String, Void, Object> {

        private AdRequest adRequest;
        private DeviceInfo deviceInfo;

        public GetSponsoredStoryTask(AdRequest adRequest, DeviceInfo deviceInfo) {
            this.adRequest = adRequest;
            this.deviceInfo = deviceInfo;
        }

        @Override
        protected Object doInBackground(String... params) {
            GetSponsoredStoryRequest getSponsoredStoryRequest =
                    new GetSponsoredStoryRequest(adRequest, uuid, deviceInfo);
            String json = null;
            try {
                json = getSponsoredStoryRequest.get().body();
            } catch (HttpRequest.HttpRequestException e) {
                Log.e(Constants.ERROR_TAG, e.getMessage());
                return new FailureMessage(e.getMessage());
            }

            if (json != null) {
                SponsoredStoryData sponsoredStoryData = null;
                try {
                    GetSponsoredStoryResponse getSponsoredStoryResponse = new GetSponsoredStoryResponse(json);
                    if (getSponsoredStoryResponse.getStatus().equalsIgnoreCase("OK")) {
                        sponsoredStoryData = getSponsoredStoryResponse.parseJson();
                        
                        if( adRequest.hasToDownloadThumbnailBitmap() )
                            sponsoredStoryData.setThumbnailBitmap(BitmapFactory.decodeStream(new URL(sponsoredStoryData.getThumbnailUrl()).openConnection().getInputStream()));
                    } else if (getSponsoredStoryResponse.getStatus().equalsIgnoreCase("FAIL")) {
                        return getSponsoredStoryResponse.getFailureMessage();
                    }
                } catch (JSONException e) {
                    String message = TextUtils.isEmpty(e.getMessage()) ? "" : e.getMessage();
                    Log.e(Constants.ERROR_TAG, message);
                    return new FailureMessage(e.getMessage());
                } catch (MalformedURLException e) {
                    String message = TextUtils.isEmpty(e.getMessage()) ? "" : e.getMessage();
                    Log.e(Constants.ERROR_TAG, message);;
                    return new FailureMessage(e.getMessage());
                } catch (IOException e) {
                    String message = TextUtils.isEmpty(e.getMessage()) ? "" : e.getMessage();
                    Log.e(Constants.ERROR_TAG, message);
                    return new FailureMessage(e.getMessage());
                } catch (Exception e) {
                    String message = TextUtils.isEmpty(e.getMessage()) ? "" : e.getMessage();
                    Log.e(Constants.ERROR_TAG, message);
                    return new FailureMessage(e.getMessage());
                }
                return sponsoredStoryData;
            }
            return new FailureMessage("Something went wrong");
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if ( result != null && result instanceof SponsoredStoryData ) {
                setSponsoredStoryData((SponsoredStoryData) result);
                onSponsoredStoryDataListener.onSponsoredStoryData();
            } else if( result instanceof FailureMessage ) {
                onSponsoredStoryDataListener.onFailure((FailureMessage) result);
            }
        }
    }
}

