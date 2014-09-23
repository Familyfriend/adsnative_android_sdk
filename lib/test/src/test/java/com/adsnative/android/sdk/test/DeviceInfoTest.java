package com.adsnative.android.sdk.test;

import android.content.Context;
import android.os.Build;

import com.adsnative.android.sdk.device.DeviceInfo;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class DeviceInfoTest {

    private DeviceInfo deviceInfo;
    private Context context;

    @Before
    public void setup() {
        context = Robolectric.getShadowApplication().getApplicationContext();
        deviceInfo = new DeviceInfo(context);
    }

    @Test
    public void testGetOsVersion() {
        Assert.assertEquals("Android " + Build.VERSION.RELEASE, deviceInfo.getOsVersion());
    }

    @Test
    public void userAgentShouldNotBeNull() {
        Assert.assertNotNull("User agent is null", deviceInfo.getUserAgent());
    }

    @Test
    public void deviceModelShouldNotBeNull() {
        Assert.assertNotNull("Device model is null", deviceInfo.getDeviceModel());
    }

    @Test
    public void localeShouldNotBeNull() {
        Assert.assertNotNull("Locale is null", deviceInfo.getLocale());
    }

    @Test
    public void timeZoneShouldNotBeNull() {
        Assert.assertNotNull("TimeZone is null", deviceInfo.getTimeZone());
    }

    @Test
    public void connectionTypeShouldNotBeNull() {
        Assert.assertNotNull("Connection type is null", deviceInfo.getConnectionType());
    }

    @Test
    public void identifierForVendorShouldNotBeNull() {
        Assert.assertNotNull("Package name is null", deviceInfo.getIdentifierForVendor());
    }
}