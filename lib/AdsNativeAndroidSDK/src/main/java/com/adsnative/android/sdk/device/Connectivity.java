package com.adsnative.android.sdk.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Helper class for determining the connection type.
 */

public class Connectivity {

    /**
     * Returns details about the currently active default data network.
     *
     * @return a {@link NetworkInfo} object for the current default network
     * or {@code null} if no network default network is currently active
     * <p/>
     * <p>This method requires the call to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Indicates whether network connectivity exists and it is possible to establish
     * connections and pass data.
     *
     * @param context
     * @return {@code true} if network connectivity exists, {@code false} otherwise.
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Indicates whether network connectivity exists and if the type of network to which the
     * info in this {@code NetworkInfo} pertains is Wifi.
     *
     * @param context
     * @return {@code true} if is connected via Wifi, {@code false} otherwise
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }


    /**
     * Indicates whether network connectivity exists and if the type of network to which the
     * info in this {@code NetworkInfo} pertains is Mobile.
     *
     * @param context
     * @return {@code true} if is connected via Mobile, {@code false} otherwise
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}