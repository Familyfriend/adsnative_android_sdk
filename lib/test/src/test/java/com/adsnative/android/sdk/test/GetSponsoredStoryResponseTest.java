package com.adsnative.android.sdk.test;

import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.request.GetSponsoredStoryResponse;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GetSponsoredStoryResponseTest {

    private GetSponsoredStoryResponse getSponsoredStoryResponse;
    private GetSponsoredStoryResponse getFailureSponsoredStoryResponse;
    private String json = "{" +
            "   \"ad\": {" +
            "       \"backgroundColor\": \"\"," +
            "       \"brandImageUrl\": \"http://dev-www.adsnative.com/media/brand_images/1/82cc86ad-9070-4b90-9006-1fbca6697694.jpg\"," +
            "       \"embedUrl\": \"http://dev-api.adsnative.com/v1/creative.html?crid=LFGRP9OI&sid=tnqvzhb71xh8da4349n1myyhhkp4k8r3\"," +
            "       \"imageSrc\": \"http://dev-www.adsnative.com/media/1/cfc02947-4103-4f57-ba0d-fda9d8a6c6ff.jpg\"," +
            "       \"promotedBy\": \"RedBull\"," +
            "       \"promotedByTag\": \"\"," +
            "       \"promotedByUrl\": \"http://dev-api.adsnative.com/v1/ad.click?u=http%3A%2F%2Fredbull.com%2F&sid=tnqvzhb71xh8da4349n1myyhhkp4k8r3\"," +
            "       \"summary\": \"Tesla teased electric motorheads earlier this week by announcing an event that would show off its curious battery swapping system...\"," +
            "       \"target\": \"_parent\"," +
            "       \"title\": \"Skateboarders take over a Chicago office space - Red Bull DEMO\"," +
            "       \"trackingTags\": \"<img src=\\\"http://dev-api.adsnative.com/v1/log/impression.gif?crid=LFGRP9OI&sid=tnqvzhb71xh8da4349n1myyhhkp4k8r3\\\" BORDER=0 WIDTH=1 HEIGHT=1>\\n<img src=\\\"\\\">\"," +
            "       \"type\": \"video\"," +
            "       \"url\": \"http://dev-api.adsnative.com/v1/ad.click?u=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DelojrNqYNtY%26amp%3Bfeature%3Dyoutube_gdata&sid=tnqvzhb71xh8da4349n1myyhhkp4k8r3\"" +
            "   }," +
            "   \"cid\": \"S9XANGW9\"," +
            "   \"count\": 1," +
            "   \"crid\": \"LFGRP9OI\"," +
            "   \"sid\": \"tnqvzhb71xh8da4349n1myyhhkp4k8r3\"," +
            "   \"status\": \"OK\"," +
            "   \"uuid\": \"d322cb79-0914-4521-9fb9-f3a4ec4f3c8e\"," +
            "   \"zid\": \"ping\"" +
            "}";

    private String failureJson = "{" +
            "   \"message\": \"no active campaigns found\"," +
            "   \"status\": \"FAIL\"," +
            "   \"uuid\": \"415244eb-569a-42cc-a86b-f02774607128\"," +
            "   \"zid\": \"eLrChVb179ztK7Go3NfWbkZEe4_MRzKGfPVN4Zcs\"" +
            "}";

    @Before
    public void setup() {
        try {
            getSponsoredStoryResponse = new GetSponsoredStoryResponse(json);
            getFailureSponsoredStoryResponse = new GetSponsoredStoryResponse(failureJson);
        } catch (JSONException e) {
            Log.e(Constants.ERROR_TAG, e.getMessage());
        }
    }

    @Test
    public void testParseJson() {
        Assert.assertNotNull(getSponsoredStoryResponse.parseJson());
    }

    @Test
    public void testStatus() {
        Assert.assertEquals("OK", getSponsoredStoryResponse.getStatus());
    }

    @Test
    public void testEmptyBackgroundColour(){
        Assert.assertEquals("#00FFFFFF", getSponsoredStoryResponse.parseJson().getBackgroundColor());
    }

    @Test
    public void testGetFailureMessage() {
        Assert.assertNotNull(getFailureSponsoredStoryResponse.getFailureMessage());
    }

    @Test
    public void testFailureStatus() {
        Assert.assertEquals("FAIL", getFailureSponsoredStoryResponse.getStatus());
    }


}
