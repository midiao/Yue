package com.superjunior.yue.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.lang.ref.WeakReference;

/**
 * Created by cb8695 on 2016/10/24.
 */

public class YueApplication extends Application {

    private static WeakReference<Context> mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<>(getApplicationContext());
        Fresco.initialize(mContext.get());
    }

    public static Context getContext() {
        return mContext.get();
    }
}
