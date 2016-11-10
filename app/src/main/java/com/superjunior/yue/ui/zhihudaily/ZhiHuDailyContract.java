package com.superjunior.yue.ui.zhihudaily;

import com.superjunior.yue.base.BasePresenter;
import com.superjunior.yue.base.BaseView;
import com.superjunior.yue.model.zhihudaily.StoryBean;
import com.superjunior.yue.model.zhihudaily.TopStoryBean;

import java.util.List;

/**
 * Created by cb8695 on 2016/11/7.
 */

public interface ZhiHuDailyContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        List<TopStoryBean> getTopStories();

        List<StoryBean> getStories();

        void onRefresh();

        void initDailyData();
    }
}
