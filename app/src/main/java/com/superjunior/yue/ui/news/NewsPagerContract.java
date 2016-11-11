package com.superjunior.yue.ui.news;

import android.content.Context;
import android.content.Intent;

import com.superjunior.yue.base.BasePresenter;
import com.superjunior.yue.base.BaseView;
import com.superjunior.yue.model.news.NewsBean;

/**
 * Created by cb8695 on 2016/10/21.
 */

public interface NewsPagerContract {

    interface View extends BaseView<Presenter> {

        void refreshCompleted();

        void setAdapter(NewsItemAdapter adapter);

        String getType();

        Context getActivityContext();

        void startDetailActivity(NewsBean bean, int position);

    }

    interface Presenter extends BasePresenter {
        void initAdapter();

        void onRefresh();

        void onResult(int request, int result, Intent data);
    }
}
