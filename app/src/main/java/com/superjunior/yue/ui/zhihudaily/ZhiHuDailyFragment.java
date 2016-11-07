package com.superjunior.yue.ui.zhihudaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.superjunior.yue.R;

/**
 * Created by cb8695 on 2016/11/7.
 */

public class ZhiHuDailyFragment extends Fragment{

    private ConvenientBanner mBanner;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_zhihu_daily, container, false);
        mBanner = (ConvenientBanner) contentView.findViewById(R.id.top_banner);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.daily_recyclerView);
        return contentView;
    }
}
