package com.adsnative.android.sdk.test;

import android.content.Context;

import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.story.SponsoredStory;
import com.adsnative.android.sdk.story.SponsoredStoryController;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
public class SponsoredStoryControllerTest {

    private SponsoredStoryController sponsoredStoryController;
    private SponsoredStory sponsoredStory;

    @Before
    public void setup() throws JSONException, IOException {
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        sponsoredStoryController = new SponsoredStoryController(context);
        sponsoredStory = new SponsoredStory(new AdRequest("test"), context);
    }

    @Test
    public void testGetSponsoredStoryView() {
        Assert.assertNotNull(sponsoredStoryController.getSponsoredStoryView(sponsoredStory));
    }
}
