package com.superjunior.yue.news;

import android.content.Context;
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
import com.superjunior.yue.base.YueApplication;
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
        mContext = YueApplication.getContext();
        type = getArguments().getString(TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_news_pager, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.news_swipeRefreshLayout);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.news_recyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getColor(R.color.material_blue_500), getColor(R.color.material_red_500),
                getColor(R.color.material_yellow_500));
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
        mPresenter.initAdapter();
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
        mPresenter.start();
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


    private int getColor(int id) {
        return getResources().getColor(id);
    }

}
