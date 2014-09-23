package com.adsnative.android.sdk.util;

/**
 * Util class for capitalizing strings
 */
public class StringUtil {
    /**
     * Capitalizes specified String.
     *
     * @param s provided string
     * @return capitalized String
     */
    public String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
