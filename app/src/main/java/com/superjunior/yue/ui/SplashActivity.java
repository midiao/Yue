package com.superjunior.yue.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.superjunior.yue.R;
import com.superjunior.yue.base.BaseActivity;
import com.superjunior.yue.ui.news.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by cb8695 on 2016/11/14.
 */

public class SplashActivity extends BaseActivity {

    private final static String TAG = "SplashActivity";
    private String mUrl = "http://p2.zhimg.com/10/7b/107bb4894b46d75a892da6fa80ef504a.jpg";
    private Uri mAdvertiseUri = Uri.parse(mUrl);
    private DonutProgress mDonutProgress;
    private SimpleDraweeView mSimpleDraweeView;
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
        mSimpleDraweeView.setController(mController);
    }

    @Override
    protected void initViews() {
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.advertise_picture);
        mDonutProgress = (DonutProgress) findViewById(R.id.count_progress);
        mDonutProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
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
        mController = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(mAdvertiseUri).build();
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
}
