package com.superjunior.yue.news;

import com.superjunior.yue.BasePresenter;
import com.superjunior.yue.BaseView;

/**
 * Created by cb8695 on 2016/10/21.
 */

public interface NewsPagerContract {

    interface View extends BaseView<Presenter> {

        void refreshCompleted();

        void setAdapter(NewsItemAdapter adapter);

        String getType();
    }

    interface Presenter extends BasePresenter {
        void initAdapter();

        void onRefresh();
    }
}
