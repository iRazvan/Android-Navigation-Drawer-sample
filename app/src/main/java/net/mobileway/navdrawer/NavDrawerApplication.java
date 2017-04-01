package net.mobileway.navdrawer;

import android.app.Application;
import android.content.Context;

/**
 * Created by Razvan on 31/03/17.
 */
public class NavDrawerApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

}