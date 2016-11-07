package com.superjunior.yue.ui.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.superjunior.yue.net.JuHeNewsAPI;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    public NewsFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        NewsPagerFragment fragment = NewsPagerFragment.newInstance(JuHeNewsAPI.newsTypes[position]);
        new NewsPagerPresenter(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return JuHeNewsAPI.newsTypes.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return JuHeNewsAPI.newsTitles[position];
    }

}
