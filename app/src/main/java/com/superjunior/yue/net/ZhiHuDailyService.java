package com.superjunior.yue.net;

import com.superjunior.yue.model.zhihudaily.ZhiHuDailyResult;
import com.superjunior.yue.model.zhihudaily.StoryDetailBean;
import com.superjunior.yue.model.zhihudaily.StoryExtraBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cb8695 on 2016/11/7.
 */

public interface ZhiHuDailyService {
    @GET("news/latest")
    Call<ZhiHuDailyResult> getDailyLatestData();

    @GET("news/{id}")
    Call<StoryDetailBean> getStoryDeatilData(@Path("id") String id);

    @GET("story-extra/{id}")
    Call<StoryExtraBean> getStoryExtraData(@Path("id") String id);
}
