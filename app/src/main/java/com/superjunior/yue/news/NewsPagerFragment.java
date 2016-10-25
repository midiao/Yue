package com.superjunior.yue.news;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superjunior.yue.R;
import com.superjunior.yue.YueApplication;
import com.superjunior.yue.util.CommonUtils;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String TITLE = "title";
    private String title;
    private RecyclerView mRecyclerView;
    private BGARefreshLayout mRefreshLayout;
    private Handler mHandler;
    private Context mContext = YueApplication.getContext();

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
        title = getArguments().getString(TITLE);
        mHandler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_news_pager, container, false);
        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.news_recyclerView);
        mRefreshLayout = (BGARefreshLayout) contentView.findViewById(R.id.news_bgaRefreshLayout);
        initRefreshLayout();
        return contentView;
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (CommonUtils.isNetworkConnected()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    mRefreshLayout.endRefreshing();
                }
            }.execute();
        } else {
            CommonUtils.makeShortToast(getString(R.string.network_disable));
            mRefreshLayout.endRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (CommonUtils.isNetworkConnected()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    mRefreshLayout.endLoadingMore();
                }
            }.execute();
            return true;
        } else {
            CommonUtils.makeShortToast(getString(R.string.network_disable));
            return false;
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder refreshViewHolder = new BGAMeiTuanRefreshViewHolder(mContext, true);
        refreshViewHolder.setPullDownImageResource(R.drawable.ic_launcher);
        refreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_loding);
        refreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_loding);
        refreshViewHolder.setLoadingMoreText(getString(R.string.loading));
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }
}
