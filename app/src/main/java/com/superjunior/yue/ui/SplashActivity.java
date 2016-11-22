package com.superjunior.yue.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.superjunior.yue.R;
import com.superjunior.yue.base.BaseActivity;
import com.superjunior.yue.model.StartImageModel;
import com.superjunior.yue.net.StartImageAPI;
import com.superjunior.yue.net.StartImageService;
import com.superjunior.yue.ui.news.MainActivity;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cb8695 on 2016/11/14.
 */

public class SplashActivity extends BaseActivity {

    private final static String TAG = "SplashActivity";
    private Retrofit mRetrofit;
    private StartImageService mImageService;
    private DonutProgress mDonutProgress;
    private SimpleDraweeView mSimpleDraweeView;
    private TextView mTextView;
    private MyHandler mHandler;
    private DraweeController mController;
    private static final int MSG_PROGRESS_UPDATE = 0x110;
    // 广告页总时长
    private static final int ADVERTISE_TIME = 2000;
    // Progress更新时长
    private static final int PROGRESS_UPDATE_TIME = 40;

    class MyHandler extends Handler {
        private final WeakReference<Activity> weakReference;

        MyHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PROGRESS_UPDATE:
                    final Activity activity = weakReference.get();
                    if (activity != null) {
                        mDonutProgress.setProgress(mDonutProgress.getProgress() + 100 / (ADVERTISE_TIME / PROGRESS_UPDATE_TIME));
                        this.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, PROGRESS_UPDATE_TIME);
                        if (mDonutProgress.getProgress() == 100) {
                            this.removeMessages(MSG_PROGRESS_UPDATE);
                            startMainActivity();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
    }

    @Override
    protected void initViews() {
        mRetrofit = new Retrofit.Builder().client(new OkHttpClient())
                .baseUrl(StartImageAPI.START_IMAGE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mImageService = mRetrofit.create(StartImageService.class);
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.advertise_picture);
        mDonutProgress = (DonutProgress) findViewById(R.id.count_progress);
        mTextView = (TextView) findViewById(R.id.author_name);
        mDonutProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
        final ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                mDonutProgress.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Log.d(TAG, "get advertise picture failed");
            }
        };
        Call<StartImageModel> call = mImageService.getStartImage(getScreenWidth(), getScreenHeight());
        call.enqueue(new retrofit2.Callback<StartImageModel>() {
            @Override
            public void onResponse(Call<StartImageModel> call, Response<StartImageModel> response) {
                StartImageModel model = response.body();
                mController = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                        .setUri(model.getImg()).build();
                mSimpleDraweeView.setController(mController);
                mTextView.setText(model.getText());
            }

            @Override
            public void onFailure(Call<StartImageModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void setStatusBarColor() {
        mStatusBarColor = R.color.colorPrimaryDark;
    }

    @Override
    protected void setLayoutResID() {
        mLayoutResID = R.layout.activity_splash;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    private String getScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return String.valueOf(dm.widthPixels);
    }

    private String getScreenHeight() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return String.valueOf(dm.heightPixels);
    }
}
