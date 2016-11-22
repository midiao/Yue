package com.superjunior.yue.net;

import com.superjunior.yue.model.StartImageModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cb8695 on 2016/11/22.
 * 启动页图片
 */

public interface StartImageService {
    @GET("{width}*{height}")
    Call<StartImageModel> getStartImage(@Path("width") String width, @Path("height") String height);
}
