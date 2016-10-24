package com.superjunior.yue.news;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.superjunior.yue.BasePresenter;
import com.superjunior.yue.BaseView;

/**
 * Created by cb8695 on 2016/10/21.
 */

public interface NewsPagerContract {

    interface View extends BaseView<Presenter> {
        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter {
        void requestData(String url);

        PagerAdapter initPagerAdapter(FragmentManager fragmentManager);
    }
}
