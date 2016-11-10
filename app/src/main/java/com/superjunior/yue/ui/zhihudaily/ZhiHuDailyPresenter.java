package com.superjunior.yue.ui.zhihudaily;

import android.util.Log;

import com.superjunior.yue.model.zhihudaily.StoryBean;
import com.superjunior.yue.model.zhihudaily.TopStoryBean;
import com.superjunior.yue.model.zhihudaily.ZhiHuDailyResult;
import com.superjunior.yue.net.ZhiHuDailyAPI;
import com.superjunior.yue.net.ZhiHuDailyService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cb8695 on 2016/11/10.
 */

public class ZhiHuDailyPresenter implements ZhiHuDailyContract.Presenter {
    private static final String TAG = "ZhiHuDailyPresenter";
    private ZhiHuDailyContract.View mView;
    private List<TopStoryBean> mTopStoryBeanList;
    private List<StoryBean> mStoryBeanList;
    private Retrofit mRetrofit;
    private ZhiHuDailyService mDailyService;
    private ZhiHuDailyResult mDailyResult;

    ZhiHuDailyPresenter(ZhiHuDailyContract.View view) {
        mView = view;
        mRetrofit = new Retrofit.Builder().client(new OkHttpClient())
                .baseUrl(ZhiHuDailyAPI.ZHIHU_DAILY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mDailyService = mRetrofit.create(ZhiHuDailyService.class);
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void initDailyData() {
        Call<ZhiHuDailyResult> call = mDailyService.getDailyLatestData();
        call.enqueue(new Callback<ZhiHuDailyResult>() {
            @Override
            public void onResponse(Call<ZhiHuDailyResult> call, Response<ZhiHuDailyResult> response) {
                mDailyResult = response.body();
                mStoryBeanList = mDailyResult.getStories();
                mTopStoryBeanList = mDailyResult.getTop_stories();
            }

            @Override
            public void onFailure(Call<ZhiHuDailyResult> call, Throwable t) {
                Log.d(TAG, "get daily data failed");
            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public List<TopStoryBean> getTopStories() {
        return mTopStoryBeanList;
    }

    @Override
    public List<StoryBean> getStories() {
        return mStoryBeanList;
    }
}
