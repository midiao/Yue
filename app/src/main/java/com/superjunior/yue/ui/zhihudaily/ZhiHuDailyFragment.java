package com.superjunior.yue.ui.zhihudaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.ConvenientBanner.PageIndicatorAlign;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.superjunior.yue.R;
import com.superjunior.yue.model.zhihudaily.TopStoryBean;
import com.superjunior.yue.util.CommonUtils;
import com.superjunior.yue.util.Constants;

import java.util.List;

/**
 * Created by cb8695 on 2016/11/7.
 */

public class ZhiHuDailyFragment extends Fragment implements ZhiHuDailyContract.View, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConvenientBanner mBanner;
    private RecyclerView mRecyclerView;
    private ZhiHuDailyContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_zhihu_daily, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.daily_swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(CommonUtils.getColor(R.color.material_blue_500), CommonUtils.getColor(R.color.material_red_500),
                CommonUtils.getColor(R.color.material_yellow_500));
        mBanner = (ConvenientBanner) contentView.findViewById(R.id.top_banner);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.daily_recyclerView);
        mPresenter.start();
        return contentView;
    }

    @Override
    public void refreshCompleted() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setBannerPages(List<TopStoryBean> topStoryBeanList) {
        mBanner.setPages(new CBViewHolderCreator<TopBannerHolderView>() {
            @Override
            public TopBannerHolderView createHolder() {
                return new TopBannerHolderView();
            }
        }, topStoryBeanList).setPageIndicator(new int[]{R.drawable.banner_indicator_default, R.drawable.banner_indicator_focus})
                .setPageIndicatorAlign(PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new AccordionTransformer())
                .setOnItemClickListener(this);
    }

    @Override
    public void setPresenter(ZhiHuDailyContract.Presenter presenter) {
        mPresenter = CommonUtils.checkNotNull(presenter);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.onRefresh();
            }
        }, Constants.REFRESHING_TIME);
    }
}
