package com.superjunior.yue.news;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.race604.flyrefresh.FlyRefreshLayout;
import com.superjunior.yue.R;

/**
 * Created by lqynydyxf on 2016/10/23.
 */

public class NewsPagerFragment extends Fragment implements FlyRefreshLayout.OnPullRefreshListener {
    public static final String TITLE = "title";
    private String title;
    private RecyclerView mRecyclerView;
    private FlyRefreshLayout mFlyRefreshLayout;
    private Handler mHandler;

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
        mFlyRefreshLayout = (FlyRefreshLayout) contentView.findViewById(R.id.news_flyRefreshLayout);
        View actionButton = mFlyRefreshLayout.getHeaderActionButton();
        if (actionButton != null){
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFlyRefreshLayout.startRefresh();
                }
            });
        }
        mFlyRefreshLayout.setOnPullRefreshListener(this);
        return contentView;
    }

    @Override
    public void onRefresh(FlyRefreshLayout view) {
        View child = mRecyclerView.getChildAt(0);
        if (child != null) {
            bounceAnimateView(child.findViewById(R.id.icon));
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFlyRefreshLayout.onRefreshFinish();
            }
        }, 2000);
    }

    @Override
    public void onRefreshAnimationEnd(FlyRefreshLayout view) {

    }

    private void bounceAnimateView(View view) {
        if (view == null) {
            return;
        }
        Animator swing = ObjectAnimator.ofFloat(view, "rotationX", 0, 30, -20, 0);
        swing.setDuration(400);
        swing.setInterpolator(new AccelerateInterpolator());
        swing.start();
    }
}
