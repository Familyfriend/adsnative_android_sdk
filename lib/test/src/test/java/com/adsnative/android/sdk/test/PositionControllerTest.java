package com.adsnative.android.sdk.test;

import android.content.Context;

import com.adsnative.android.sdk.adapter.PositionController;
import com.adsnative.android.sdk.request.AdRequest;
import com.adsnative.android.sdk.story.SponsoredStory;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class PositionControllerTest {

    private PositionController positionController;
    private Context context;

    @Before
    public void setup(){
        context = Robolectric.getShadowApplication().getApplicationContext();
        positionController = new PositionController(10);
    }

    @Test
    public void testGetOriginalPosition(){
        Assert.assertEquals(5, positionController.getOriginalPosition(5));
    }

    @Test
    public void testGetAdjustedCountWithInsertingSingleStory(){
        Assert.assertEquals(10, positionController.getAdjustedCount());
        positionController.insertSponsoredStory(new SponsoredStory(new AdRequest("123"), context), 5);
        positionController.updateLists();
        Assert.assertEquals(11, positionController.getAdjustedCount());
    }

    @Test
    public void testGetAjustedCountWithInsertingMultipleStories(){
        List<SponsoredStory> sponsoredStories = new ArrayList<SponsoredStory>();
        sponsoredStories.add(new SponsoredStory(new AdRequest("123"), context));
        sponsoredStories.add(new SponsoredStory(new AdRequest("123"), context));
        List<Integer> integers = new ArrayList<Integer>();
        integers.add(8);
        integers.add(15);
        positionController.replaceSponsoredStories(sponsoredStories, integers);
        positionController.updateLists();
        Assert.assertEquals(12, positionController.getAdjustedCount());
    }

    @Test
    public void testIsAd(){
        Assert.assertEquals(false, positionController.isAd(5));
        positionController.insertSponsoredStory(new SponsoredStory(new AdRequest("123"), context), 5);
        positionController.updateLists();
        Assert.assertEquals(true, positionController.isAd(5));
    }

    @Test
    public void testGetSponsoredStory(){
        Assert.assertNull(positionController.getSponsoredStory(5));
        positionController.insertSponsoredStory(new SponsoredStory(new AdRequest("123"), context), 5);
        positionController.updateLists();
        Assert.assertNotNull(positionController.getSponsoredStory(5));
    }
}
