package com.adsnative.android.sdk.test;

import com.adsnative.android.sdk.request.LogTimeRequest;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LogTimeRequestTest {
    private LogTimeRequest logTimeRequest;

    @Before
    public void setup(){
        long time = 12345;
        String crid = "CAEY18VQ";
        String sid = "dubexbfqin1365vthwxtwq72ouruz5f4";
        logTimeRequest = new LogTimeRequest(time, crid, sid);
    }

    @Test
    public void testTime(){
        Assert.assertEquals(200, logTimeRequest.logTime());
    }
}
