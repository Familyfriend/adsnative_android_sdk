package com.adsnative.android.sdk.device;

import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.adsnative.android.sdk.util.StringUtil;

import org.odinmobile.android.ODIN;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Class collects and stores device info.
 */

public class DeviceInfo {

    private String osVersion;
    private String userAgent;
    private String deviceModel;
    private String locale;
    private String timeZone;
    private String identifierForVendor;
    private String connectionType;
    private String ODIN1;

    /**
     * Every property is having its value assigned in constructor
     *
     * @param context
     */
    public DeviceInfo(Context context) {
        this.osVersion = "Android " + Build.VERSION.RELEASE;
        this.userAgent = new WebView(context).getSettings().getUserAgentString();

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            this.deviceModel = new StringUtil().capitalize(model);
        } else {
            this.deviceModel = new StringUtil().capitalize(manufacturer) + " " + model;
        }

        this.locale = Locale.getDefault().toString();
        this.timeZone = TimeZone.getDefault().getDisplayName();
        this.identifierForVendor = context.getApplicationContext().getPackageName();

        if (!Connectivity.isConnected(context))
            this.connectionType = "None";

        if (Connectivity.isConnectedMobile(context))
            this.connectionType = "WWAN";

        if (Connectivity.isConnectedWifi(context))
            this.connectionType = "Wifi";

        this.ODIN1 = ODIN.getODIN1(context);
    }

    /**
     * Os version getter
     *
     * @return the os version string in 'Android X.X' format
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * User-agent getter
     *
     * @return the default user-agent string
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Device model getter
     *
     * @return the device model string in 'Manufacturer end-user-visible' format.
     */
    public String getDeviceModel() {
        return deviceModel;
    }

    /**
     * Locale getter
     *
     * @return the locale string in 'xx_XX' format
     */
    public String getLocale() {
        return locale;
    }


    /**
     * Time zone getter
     *
     * @return the time zone string
     */
    public String getTimeZone() {
        return timeZone;
    }


    /**
     * Identifier for vendor getter
     *
     * @return the identifier string
     */
    public String getIdentifierForVendor() {
        return identifierForVendor;
    }


    /**
     * Connection type getter
     * <p/>
     * Possible return values:
     * 'None' - if there is no connection,
     * 'WWAN' - if connection is via Mobile,
     * 'Wifi' - if connection is via Wifi.
     *
     * @return the connection type string
     */
    public String getConnectionType() {
        return connectionType;
    }


    /**
     * ODIN1 getter
     *
     * @return the ODIN1 string
     */
    public String getODIN1() {
        return ODIN1;
    }
}
