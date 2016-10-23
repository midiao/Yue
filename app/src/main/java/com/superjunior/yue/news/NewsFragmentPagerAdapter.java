package com.superjunior.yue.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.superjunior.yue.net.NewsAPI;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public NewsFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsPagerFragment.newInstance(NewsAPI.newsTitles[position]);
    }

    @Override
    public int getCount() {
        return NewsAPI.newsTypes.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NewsAPI.newsTitles[position];
    }
}
