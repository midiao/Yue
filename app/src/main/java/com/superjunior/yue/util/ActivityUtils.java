package com.superjunior.yue.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.superjunior.yue.R;

/**
 * Created by cb8695 on 2016/10/20.
 */

public class ActivityUtils {
    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int fragmentId) {

        CommonUtils.checkNotNull(fragmentManager);
        CommonUtils.checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentId, fragment);
        transaction.commit();
    }

    public static void switchFragment(FragmentManager fragmentManager, Fragment oldFragment, Fragment newFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (newFragment.isAdded()) {
            transaction.hide(oldFragment).show(newFragment).commit();
        } else {
            transaction.hide(oldFragment).add(R.id.contentFrame, newFragment).commit();
        }
    }
}
