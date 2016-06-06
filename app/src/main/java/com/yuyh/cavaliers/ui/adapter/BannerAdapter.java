package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class BannerAdapter extends HelperAdapter<NewsItem.NewsItemBean> {

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public BannerAdapter(List<NewsItem.NewsItemBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(HelperViewHolder viewHolder, int position, NewsItem.NewsItemBean item) {
        viewHolder.setImageUrl(R.id.ivBannerImg, item.getImgurl());
        viewHolder.setText(R.id.tvBannerTitle, item.getTitle())
                .setText(R.id.tvBannerTime, item.getPub_time());
    }
}
