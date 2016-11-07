package com.superjunior.yue.ui.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superjunior.yue.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View contentView;
    private NewsFragmentPagerAdapter mPagerAdapter;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_news, container, false);
        mViewPager = (ViewPager) contentView.findViewById(R.id.news_viewPager);
        mTabLayout = (TabLayout) contentView.findViewById(R.id.news_tabLayout);
        mPagerAdapter = new NewsFragmentPagerAdapter(getFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return contentView;
    }
}
