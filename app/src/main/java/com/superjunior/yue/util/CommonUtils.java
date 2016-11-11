package com.superjunior.yue.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import static com.superjunior.yue.base.YueApplication.getContext;

/**
 * Created by cb8695 on 2016/10/20.
 */

public class CommonUtils {

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static void makeShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        return false;
    }

    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }
}
