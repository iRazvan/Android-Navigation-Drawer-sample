package net.mobileway.navdrawer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import net.mobileway.navdrawer.NavDrawerApplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Razvan on 13/05/14.
 */
public class Utils {

    private static String uniqueID = null;
    private static String PREFS_UNIQUE_ID = "prefs_unique_id";

    /**
     * Generates or reads an unique device id
     *
     * @return unique string id
     */
    public synchronized static String getUUID() {
        if (uniqueID == null) {
            final SharedPreferences sp = getContext().getSharedPreferences(
                    PREFS_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sp.getString(PREFS_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(PREFS_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }
        return uniqueID;
    }

    /**
     * Generates md5 hash for a given string parameter
     *
     * @param input
     *            string parameter to generate md5 hash
     * @return md5'ed string
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0" + md5;

            return md5;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * Gets application's context
     *
     * @return Application Context
     */
    public static Context getContext() {
        return NavDrawerApplication.getAppContext();
    }

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        return info.isConnected();
    }

    public static boolean isNetworkGood() {
        ConnectivityManager cm = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // TelephonyManager tm = ((TelephonyManager) getContext()
        // .getSystemService(Context.TELEPHONY_SERVICE));
        // Skip if no connection, or background data disabled

        if (cm == null) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }

        int netType = info.getType();
        // int netSubtype = info.getSubtype();
        if (netType == ConnectivityManager.TYPE_WIFI) {
            return info.isConnectedOrConnecting();
            // } else if (netType == ConnectivityManager.TYPE_MOBILE
            // && (netSubtype == TelephonyManager.NETWORK_TYPE_UMTS ||
            // netSubtype == TelephonyManager.NETWORK_TYPE_LTE)
            // && !tm.isNetworkRoaming()) {
            // return info.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

}
