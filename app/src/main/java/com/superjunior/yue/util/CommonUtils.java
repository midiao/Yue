package com.superjunior.yue.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.superjunior.yue.R;

import static com.superjunior.yue.base.YueApplication.getContext;

/**
 * Created by cb8695 on 2016/10/20.
 */

public class CommonUtils {
    private static ProgressDialog loadingProgressCircle = null;
    public static final String URI = "uri";
    public static final String POSTION = "position";

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

    public static void showLoadingProgress(final String title, final String msg, final boolean cancelableFlag) {
        if (loadingProgressCircle == null) {
            loadingProgressCircle = ProgressDialog.show(getContext(), title, msg, true, cancelableFlag);
            loadingProgressCircle.setIndeterminateDrawable(getContext().getResources().getDrawable(R.drawable.loading_anim));
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
