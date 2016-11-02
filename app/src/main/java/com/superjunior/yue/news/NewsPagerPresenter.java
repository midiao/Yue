package com.superjunior.yue.news;

import android.content.Context;
import android.util.Log;

import com.superjunior.yue.YueApplication;
import com.superjunior.yue.model.NewsBean;
import com.superjunior.yue.model.NewsResult;
import com.superjunior.yue.net.JuHeNewsAPI;
import com.superjunior.yue.net.JuHeNewsService;
import com.superjunior.yue.util.CommonUtils;

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
    private List<NewsBean> mNewsBeanList;
    private NewsPagerContract.View mView;
    private Context mContext = YueApplication.getContext();
    private NewsItemAdapter mAdapter;

    NewsPagerPresenter(NewsPagerContract.View view) {
        mView = CommonUtils.checkNotNull(view);
        mRetrofit = new Retrofit.Builder().client(new OkHttpClient())
                .baseUrl(JuHeNewsAPI.juheNewsBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mJuHeNewsService = mRetrofit.create(JuHeNewsService.class);
        mView.setPresenter(this);
    }
    @Override
    public void initAdapter() {
        Call<NewsResult> call = mJuHeNewsService.getNewsData(mView.getType(), JuHeNewsAPI.juheKey);
        call.enqueue(new retrofit2.Callback<NewsResult>() {
            @Override
            public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {
                NewsResult result = response.body();
                mNewsBeanList = result.getResult().getData();
                mAdapter = new NewsItemAdapter(mContext, mNewsBeanList, mView.getType().equals("top"));
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
        Call<NewsResult> call = mJuHeNewsService.getNewsData(mView.getType(), JuHeNewsAPI.juheKey);
        call.clone().enqueue(new retrofit2.Callback<NewsResult>() {
            @Override
            public void onResponse(Call<NewsResult> call, Response<NewsResult> response) {
                NewsResult result = response.body();
                mNewsBeanList = result.getResult().getData();
                mAdapter = new NewsItemAdapter(mContext, mNewsBeanList, mView.getType().equals("top"));
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
    public void start() {
        initAdapter();
    }
}
