package com.superjunior.yue.news;

import android.content.Context;

import com.superjunior.yue.YueApplication;
import com.superjunior.yue.model.NewsBean;
import com.superjunior.yue.util.CommonUtils;

import java.util.List;

/**
 * Created by cb8695 on 2016/10/24.
 */

public class NewsPagerPresenter implements NewsPagerContract.Presenter {

    private NewsPagerContract.View mView;
    private Context mContext = YueApplication.getContext();

    public NewsPagerPresenter(NewsPagerContract.View view) {
        mView = CommonUtils.checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public List<NewsBean> getNewsData(String type) {
        return null;
    }

    @Override
    public NewsItemAdapter initAdapter(String type) {
        return new NewsItemAdapter(mContext, getNewsData(type), type.equals("top"));
    }

    @Override
    public void start() {

    }
}
