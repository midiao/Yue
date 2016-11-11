package com.superjunior.yue.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superjunior.yue.R;
import com.superjunior.yue.model.news.NewsBean;
import com.superjunior.yue.util.CommonUtils;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsPagerContract.View {
    private static final String TAG = "NewsPagerFragment";
    public static final String TYPE = "type";
    private int DEFAULT_TIME = 2000;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;
    private String type;
    private NewsPagerContract.Presenter mPresenter;

    public static NewsPagerFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        NewsPagerFragment pagerFragment = new NewsPagerFragment();
        pagerFragment.setArguments(args);
        return pagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        type = getArguments().getString(TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_news_pager, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.news_swipeRefreshLayout);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.news_recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(CommonUtils.getColor(R.color.material_blue_500), CommonUtils.getColor(R.color.material_red_500),
                CommonUtils.getColor(R.color.material_yellow_500));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //第一次进入时自动刷新
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        mPresenter.start();
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, DEFAULT_TIME);
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.onRefresh();
            }
        }, DEFAULT_TIME);
    }

    @Override
    public void refreshCompleted() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setAdapter(NewsItemAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(NewsPagerContract.Presenter presenter) {
        mPresenter = CommonUtils.checkNotNull(presenter);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Context getActivityContext() {
        return mContext;
    }

    @Override
    public void startDetailActivity(NewsBean bean, int position) {
        Intent intent = new Intent(mContext, NewsDetailActivity.class);
        intent.putExtra(CommonUtils.URI, bean.getUrl());
        intent.putExtra(CommonUtils.POSTION, position);
        startActivityForResult(intent, NewsDetailActivity.REQUEST_START);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onResult(requestCode, resultCode, data);
    }
}
