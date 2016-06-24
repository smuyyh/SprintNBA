package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.library.utils.DimenUtils;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class BannerAdapter extends HelperAdapter<NewsItem.NewsItemBean> {

    private OnListItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public BannerAdapter(List<NewsItem.NewsItemBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final NewsItem.NewsItemBean item) {
        //viewHolder.setImageUrl(R.id.ivBannerImg, item.getImgurl());
        ImageView iv = viewHolder.getView(R.id.ivBannerImg);
        iv.setTag(item.getImgurl());
        if(iv.getTag()!=null && iv.getTag().equals(item.getImgurl()))
            Picasso.with(mContext).load(item.getImgurl()).into(iv);
        viewHolder.setText(R.id.tvBannerTitle, item.getTitle())
                .setText(R.id.tvBannerTime, item.getPub_time());
        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
        ImageView image = (ImageView) viewHolder.getItemView().findViewById(R.id.ivBannerImg);
        ViewGroup.LayoutParams para = image.getLayoutParams();
        para.height = DimenUtils.getScreenWidth() / 2;
        para.width = ViewGroup.LayoutParams.MATCH_PARENT;
        image.setLayoutParams(para);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
