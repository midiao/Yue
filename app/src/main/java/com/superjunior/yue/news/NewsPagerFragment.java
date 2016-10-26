package com.superjunior.yue.news;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.superjunior.yue.YueApplication;
import com.superjunior.yue.util.CommonUtils;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TITLE = "title";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler mHandler;
    private Context mContext;
    private static final int DEFAULT_ITEM_SIZE = 10;
    private static final int ITEM_SIZE_OFFSET = 10;
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    private static final int TIME = 1000;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static NewsPagerFragment newInstance() {
        return new NewsPagerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = YueApplication.getContext();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MSG_CODE_REFRESH) {
                    mAdapter.setCount(DEFAULT_ITEM_SIZE);
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                } else if (msg.what == MSG_CODE_LOADMORE) {
                    if (mAdapter.getItemCount() == DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET) {
                        CommonUtils.makeShortToast(getString(R.string.nomoredata));
                    } else {
                        mAdapter.setCount(DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        mAdapter = new NewsAdapter(mContext, DEFAULT_ITEM_SIZE);
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
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        return contentView;
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
    }

    private int getColor(int id) {
        return getResources().getColor(id);
    }
}
