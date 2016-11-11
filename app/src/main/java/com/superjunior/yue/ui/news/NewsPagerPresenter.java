package com.superjunior.yue.ui.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.superjunior.yue.model.news.NewsBean;
import com.superjunior.yue.model.news.NewsResult;
import com.superjunior.yue.net.JuHeNewsAPI;
import com.superjunior.yue.net.JuHeNewsService;
import com.superjunior.yue.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cb8695 on 2016/10/24.
 */

public class NewsPagerPresenter implements NewsPagerContract.Presenter {

    private static final String TAG = "NewsPagerPresenter";
    private JuHeNewsService mJuHeNewsService;
    private Retrofit mRetrofit;
    private List<NewsBean> mNewsBeanList = new ArrayList<>();
    private NewsPagerContract.View mView;
    private NewsItemAdapter mAdapter;
    private Context mContext;

    NewsPagerPresenter(NewsPagerContract.View view) {
        mView = CommonUtils.checkNotNull(view);
        mRetrofit = new Retrofit.Builder().client(new OkHttpClient())
                .baseUrl(JuHeNewsAPI.JUHE_NEWS_BASE_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mJuHeNewsService = mRetrofit.create(JuHeNewsService.class);
        mContext = view.getActivityContext();
        mView.setPresenter(this);
    }
    @Override
    public void initAdapter() {
        Call<NewsResult> call = mJuHeNewsService.getNewsData(mView.getType(), JuHeNewsAPI.JUHE_KEY);
        call.enqueue(new retrofit2.Callback<NewsResult>() {
            @Override
            public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {
                NewsResult result = response.body();
                mNewsBeanList = result.getResult().getData();
                mAdapter = new NewsItemAdapter(mView.getActivityContext(), mNewsBeanList, mView.getType().equals("top"));
                mAdapter.setOnItemClickListener(new NewsItemAdapter.onRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, NewsBean bean, int position) {
                        mView.startDetailActivity(bean, position);
                    }
                });
                mView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<NewsResult> call, Throwable t) {
                Log.d(TAG, "get data failed");
            }
        });
    }

    @Override
    public void onRefresh() {
        Call<NewsResult> call = mJuHeNewsService.getNewsData(mView.getType(), JuHeNewsAPI.JUHE_KEY);
        call.clone().enqueue(new retrofit2.Callback<NewsResult>() {
            @Override
            public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {
                NewsResult result = response.body();
                mNewsBeanList = result.getResult().getData();
                mAdapter = new NewsItemAdapter(mView.getActivityContext(), mNewsBeanList, mView.getType().equals("top"));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsResult> call, Throwable t) {
                Log.d(TAG, "get data failed");
            }
        });
        mView.refreshCompleted();
    }

    @Override
    public void onResult(int request, int result, Intent data) {
        if (NewsDetailActivity.REQUEST_START == request && NewsDetailActivity.RESULT_CODE == result) {
            int position = CommonUtils.checkNotNull(data.getIntExtra(CommonUtils.POSTION, -1));
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void start() {
        initAdapter();
    }
}
