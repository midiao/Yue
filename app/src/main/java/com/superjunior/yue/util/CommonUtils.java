package com.superjunior.yue.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.superjunior.yue.R;
import com.superjunior.yue.YueApplication;

/**
 * Created by cb8695 on 2016/10/20.
 */

public class CommonUtils {
    private static ProgressDialog loadingProgressCircle = null;
    private static Context mContext = YueApplication.getContext();

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static void makeShortToast(String message) {
        Toast.makeText(YueApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLoadingProgress(final String title, final String msg, final boolean cancelableFlag) {
        if (loadingProgressCircle == null) {
            loadingProgressCircle = ProgressDialog.show(mContext, title, msg, true, cancelableFlag);
            loadingProgressCircle.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.loading_anim));
        } else {
            loadingProgressCircle.setCancelable(cancelableFlag);
            loadingProgressCircle.setMessage(msg);
        }
        loadingProgressCircle.show();
    }

    public static void hideLoadingProgress() {
        if (loadingProgressCircle != null) {
            loadingProgressCircle.dismiss();
        }
    }

    public static boolean isNetworkConnected() {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
