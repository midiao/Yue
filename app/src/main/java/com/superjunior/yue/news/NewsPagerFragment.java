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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superjunior.yue.R;
import com.superjunior.yue.YueApplication;
import com.superjunior.yue.model.NewsBean;
import com.superjunior.yue.model.NewsResult;
import com.superjunior.yue.net.JuHeNewsService;
import com.superjunior.yue.net.NewsAPI;
import com.superjunior.yue.util.CommonUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "NewsPagerFragment";
    public static final String TYPE = "type";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;
    private JuHeNewsService mJuHeNewsService;
    private static final int DEFAULT_ITEM_SIZE = 10;
    private static final int ITEM_SIZE_OFFSET = 10;
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    private static final int TIME = 1000;
    private NewsItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String type;
    private List<NewsBean> mNewsBeanList;

    public static NewsPagerFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        NewsPagerFragment pagerFragment = new NewsPagerFragment();
        pagerFragment.setArguments(args);
        return pagerFragment;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                Call<NewsResult> call = mJuHeNewsService.getNewsData(type, NewsAPI.juheKey);
                call.enqueue(new retrofit2.Callback<NewsResult>() {
                    @Override
                    public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {
                        NewsResult result = response.body();
                        mNewsBeanList = result.getResult().getData();
                        Log.d(TAG, mNewsBeanList.get(0).getTitle());
                        mAdapter = new NewsItemAdapter(mContext, mNewsBeanList, type.equals("top"));
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<NewsResult> call, Throwable t) {
                        Log.d(TAG, "get data failed");
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if (mAdapter.getItemCount() == DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET) {
                    CommonUtils.makeShortToast(getString(R.string.nomoredata));
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = YueApplication.getContext();
        type = getArguments().getString(TYPE);
        Retrofit juHeRetrofit = new Retrofit.Builder().client(new OkHttpClient())
                .baseUrl(NewsAPI.juheNewsBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mJuHeNewsService = juHeRetrofit.create(JuHeNewsService.class);
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
        //第一次进入时自动刷新
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        Call<NewsResult> call = mJuHeNewsService.getNewsData(type, NewsAPI.juheKey);
        call.enqueue(new retrofit2.Callback<NewsResult>() {
            @Override
            public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {
                NewsResult result = response.body();
                mNewsBeanList = result.getResult().getData();
                mAdapter = new NewsItemAdapter(mContext, mNewsBeanList, type.equals("top"));
                mRecyclerView.setAdapter(mAdapter);
                //两秒后结束刷新动画
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<NewsResult> call, Throwable t) {
                Log.d(TAG, "get data failed");
            }
        });
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
