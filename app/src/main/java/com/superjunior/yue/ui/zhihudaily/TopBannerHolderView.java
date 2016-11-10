package com.superjunior.yue.ui.zhihudaily;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.superjunior.yue.R;
import com.superjunior.yue.model.zhihudaily.TopStoryBean;

/**
 * Created by cb8695 on 2016/11/10.
 */

public class TopBannerHolderView implements Holder<TopStoryBean> {
    private SimpleDraweeView mSimpleDraweeView;
    private TextView mTextView;

    @Override
    public View createView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mSimpleDraweeView = (SimpleDraweeView) contentView.findViewById(R.id.top_banner_pic);
        mTextView = (TextView) contentView.findViewById(R.id.top_banner_title);
        return contentView;
    }

    @Override
    public void UpdateUI(Context context, int position, TopStoryBean bean) {
        mSimpleDraweeView.setImageURI(Uri.parse(bean.getImage()));
        mTextView.setText(bean.getTitle());
    }
}
