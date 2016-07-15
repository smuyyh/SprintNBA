package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.news.NewsItem;
import com.yuyh.sprintnba.http.bean.news.VideoRealUrl;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class NewsAdapter extends HelperAdapter<NewsItem.NewsItemBean> {

    private OnListItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public NewsAdapter(List<NewsItem.NewsItemBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final NewsItem.NewsItemBean item) {

        Uri uri = Uri.parse(item.imgurl);

        if (item.atype.equals("2")) {
            final JCVideoPlayerStandard videoPlayer = viewHolder.getView(R.id.vpVideo);
            videoPlayer.setUp("", item.title);
            TencentService.getVideoRealUrl(item.vid, new RequestCallback<VideoRealUrl>() {
                @Override
                public void onSuccess(VideoRealUrl real) {
                    String url = real.url + real.vid + ".mp4?vkey=" + real.fvkey;
                    LogUtils.i("real-url："+url);
                    videoPlayer.setUp(url, item.title);
                }

                @Override
                public void onFailure(String message) {

                }
            });

            videoPlayer.thumbImageView.setController(FrescoUtils.getController(uri, videoPlayer.thumbImageView));
            viewHolder.setText(R.id.tvVideoTitle, item.title).setText(R.id.tvVideoTime, item.pub_time);
            ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
            params.height = DimenUtils.getScreenWidth() / 2;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            videoPlayer.setLayoutParams(params);
        } else {
            SimpleDraweeView iv = viewHolder.getView(R.id.ivBannerImg);
            iv.setController(FrescoUtils.getController(uri, iv));
            ViewGroup.LayoutParams params = iv.getLayoutParams();
            params.height = DimenUtils.getScreenWidth() / 2;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            iv.setLayoutParams(params);
            viewHolder.setText(R.id.tvBannerTitle, item.title)
                    .setText(R.id.tvBannerTime, item.pub_time);

            viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
                }
            });
        }
        showItemAnim(viewHolder.getItemView(), position);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private int mLastPosition = -1;

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public int checkLayoutIndex(NewsItem.NewsItemBean item, int position) {
        if (item.atype.equals("2"))
            return 1;
        return 0;
    }
}
