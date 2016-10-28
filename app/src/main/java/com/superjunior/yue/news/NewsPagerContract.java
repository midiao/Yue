package com.superjunior.yue.news;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.superjunior.yue.BasePresenter;
import com.superjunior.yue.BaseView;
import com.superjunior.yue.model.NewsBean;

import java.util.List;

/**
 * Created by cb8695 on 2016/10/21.
 */

public interface NewsPagerContract {

    interface View extends BaseView<Presenter> {
        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter {
        List<NewsBean> getNewsData(String type);

        NewsItemAdapter initAdapter(String type);
    }
}
