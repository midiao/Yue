package com.superjunior.yue.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superjunior.yue.R;
import com.superjunior.yue.util.CommonUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsFragment extends Fragment implements NewsPagerContract.View {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View contentView;
    private NewsFragmentPagerAdapter mPagerAdapter;
    private NewsPagerContract.Presenter mPresenter;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_news, container, false);
        mViewPager = (ViewPager) contentView.findViewById(R.id.news_viewPager);
        mTabLayout = (TabLayout) contentView.findViewById(R.id.news_tabLayout);
        mPagerAdapter = (NewsFragmentPagerAdapter) mPresenter.initPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return contentView;
    }

    @Override
    public void setPresenter(NewsPagerContract.Presenter presenter) {
        mPresenter = CommonUtils.checkNotNull(presenter);
    }

    @Override
    public void showLoading() {
        CommonUtils.showLoadingProgress("", getString(R.string.loading), false);
    }

    @Override
    public void hideLoading() {
        CommonUtils.hideLoadingProgress();
    }
}
