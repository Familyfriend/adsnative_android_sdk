package com.adsnative.android.sdk.test;

import android.content.Context;

import com.adsnative.android.sdk.device.DeviceInfo;
import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.request.GetSponsoredStoryRequest;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class GetSponsoredStoryRequestTest {

    private GetSponsoredStoryRequest getSponsoredStoryRequest;
    private AdRequest adRequest;
    private Context context;

    @Before
    public void setup() {
        adRequest = new AdRequest("Uw8JRh5gifh9sxZKZ-IRgVC0WNcgOGWxSyEFjObs");

        context = Robolectric.getShadowApplication().getApplicationContext();
        DeviceInfo deviceInfo = new DeviceInfo(context);
        getSponsoredStoryRequest =
                new GetSponsoredStoryRequest(adRequest, "1234", deviceInfo);
    }

    @Test
    public void testGet(){
        Assert.assertEquals(200, getSponsoredStoryRequest.get().code());
    }
}
