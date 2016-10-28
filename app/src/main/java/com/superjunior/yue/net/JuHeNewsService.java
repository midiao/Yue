package com.superjunior.yue.net;

import com.superjunior.yue.model.NewsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cb8695 on 2016/10/28.
 */

public interface JuHeNewsService {
    @GET("index")
    Call<NewsResult> getNewsData(@Query("type") String type, @Query("key") String key);
}
