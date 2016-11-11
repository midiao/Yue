package com.superjunior.yue.ui.news;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.superjunior.yue.R;
import com.superjunior.yue.model.news.NewsBean;
import com.superjunior.yue.util.CommonUtils;

import java.util.List;

/**
 * Created by cb8695 on 2016/10/25.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {
    private Context mContext;
    private List<NewsBean> mNewsBeanList;
    private boolean mIsTop;
    private onRecyclerViewItemClickListener mOnItemClickListener = null;

    NewsItemAdapter(Context context, List<NewsBean> beanList, boolean isTop) {
        mContext = context;
        mNewsBeanList = beanList;
        mIsTop = isTop;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final NewsBean bean = mNewsBeanList.get(position);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(bean.getThumbnail_pic_s()))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.thumbnail.getController())
                .build();
        holder.parent_iew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setClicked(true);
                holder.title.setTextColor(CommonUtils.getColor(R.color.material_grey_500));
                mOnItemClickListener.onItemClick(v, bean, position);
            }
        });
        holder.thumbnail.setController(controller);
        holder.title.setText(bean.getTitle());
        holder.title.setTextColor(CommonUtils.getColor(bean.isClicked() ? R.color.material_grey_500 : R.color.material_textBlack_black));
        //如果是头条新闻，显示新闻类型；反之，则显示新闻源
        if (mIsTop) {
            holder.info.setText(bean.getRealtype());
        } else {
            holder.info.setText(bean.getAuthor_name());
        }
    }

    @Override
    public int getItemCount() {
        return mNewsBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View parent_iew;
        SimpleDraweeView thumbnail;
        TextView title;
        TextView info;
        ViewHolder(View itemView) {
            super(itemView);
            parent_iew = itemView.findViewById(R.id.news_parent_view);
            thumbnail = (SimpleDraweeView) parent_iew.findViewById(R.id.news_thumbnail);
            title = (TextView) parent_iew.findViewById(R.id.news_title);
            info = (TextView) parent_iew.findViewById(R.id.news_info);
        }
    }

    void setOnItemClickListener(onRecyclerViewItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    interface onRecyclerViewItemClickListener {
        void onItemClick(View view, NewsBean bean, int position);
    }
}
