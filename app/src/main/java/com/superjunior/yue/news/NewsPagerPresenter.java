package com.superjunior.yue.news;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;

import com.superjunior.yue.util.CommonUtils;

/**
 * Created by cb8695 on 2016/10/24.
 */

public class NewsPagerPresenter implements NewsPagerContract.Presenter {

    private NewsPagerContract.View mView;

    public NewsPagerPresenter(NewsPagerContract.View view) {
        mView = CommonUtils.checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void requestData(String url) {

    }

    @Override
    public PagerAdapter initPagerAdapter(FragmentManager fragmentManager) {
        return new NewsFragmentPagerAdapter(fragmentManager);
    }

    @Override
    public void start() {

    }
}
