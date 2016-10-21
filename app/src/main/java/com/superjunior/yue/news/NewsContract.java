package com.superjunior.yue.news;

import android.support.v4.view.PagerAdapter;

import com.superjunior.yue.BasePresenter;
import com.superjunior.yue.BaseView;

/**
 * Created by cb8695 on 2016/10/21.
 */

public interface NewsContract {

    interface View extends BaseView<Presenter> {
        void setTitle(String title);
    }

    interface Presenter extends BasePresenter {
        void requestData(String url);

        PagerAdapter initPagerAdapter();
    }
}
