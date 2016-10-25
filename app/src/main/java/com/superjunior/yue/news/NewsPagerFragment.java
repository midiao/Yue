package com.superjunior.yue.news;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
import com.superjunior.yue.R;
import com.superjunior.yue.YueApplication;
import com.superjunior.yue.util.CommonUtils;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment {
    public static final String TITLE = "title";
    private String title;
    private PullToRefreshRecyclerView mRecyclerView;
    private Handler mHandler;
    private Context mContext;
    private static final int DEFAULT_ITEM_SIZE = 5;
    private static final int ITEM_SIZE_OFFSET = 5;
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    private static final int TIME = 1000;
    private NewsAdapter mAdapter;

    public static NewsPagerFragment newInstance(String page) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, page);
        NewsPagerFragment fragment = new NewsPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = YueApplication.getContext();
        title = getArguments().getString(TITLE);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MSG_CODE_REFRESH) {
                    mAdapter.setCount(DEFAULT_ITEM_SIZE);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setOnRefreshComplete();
                    mRecyclerView.onFinishLoading(true, false);
                } else if (msg.what == MSG_CODE_LOADMORE) {
                    if (mAdapter.getItemCount() == DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET) {
                        CommonUtils.makeShortToast(getString(R.string.nomoredata));
                        mRecyclerView.onFinishLoading(false, false);
                    } else {
                        mAdapter.setCount(DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.onFinishLoading(true, false);
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
        mRecyclerView = (PullToRefreshRecyclerView) contentView.findViewById(R.id.news_refreshRecyclerView);
        initRefreshLayout();
        return contentView;
    }

    private void initRefreshLayout() {
        mRecyclerView.setSwipeEnable(true);
        LoadMoreView loadMoreView = new LoadMoreView(mContext, mRecyclerView.getRecyclerView());
        loadMoreView.setLoadmoreString(getString(R.string.loading));
        loadMoreView.setLoadMorePadding(100);
        mRecyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                if (CommonUtils.isNetworkConnected()) {
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
                } else {
                    CommonUtils.makeShortToast(getString(R.string.network_disable));
                }
            }
        });

        mRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CommonUtils.isNetworkConnected()) {
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
                } else {
                    CommonUtils.makeShortToast(getString(R.string.network_disable));
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addHeaderView(View.inflate(mContext, R.layout.refresh_header, null));
        mRecyclerView.setEmptyView(View.inflate(mContext, R.layout.empty_view, null));
        mRecyclerView.setLoadMoreFooter(loadMoreView);
        mRecyclerView.setLoadmoreString(getString(R.string.loading_more));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.onFinishLoading(true, false);
    }
}
