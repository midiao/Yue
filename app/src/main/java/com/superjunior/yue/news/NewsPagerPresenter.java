package com.superjunior.yue.news;

import android.support.v4.view.PagerAdapter;

import com.superjunior.yue.util.CommonUtils;

/**
 * Created by cb8695 on 2016/10/24.
 */

public class NewsPagerPresenter implements NewsPagerContract.Presenter {

    private NewsPagerContract.View mView;

    public NewsPagerPresenter(NewsPagerContract.View view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void requestData(String url) {
        mView.showLoading();
    }

    @Override
    public PagerAdapter initPagerAdapter() {
        return null;
    }

    @Override
    public void start() {

    }
}
