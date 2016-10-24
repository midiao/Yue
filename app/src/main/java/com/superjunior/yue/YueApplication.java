package com.superjunior.yue;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by cb8695 on 2016/10/24.
 */

public class YueApplication extends Application {

    private static Context mContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Fresco.initialize(mContext);
    }

    public static Context getContext() {
        return mContext;
    }
}
