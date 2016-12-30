package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;
import com.yuyh.library.utils.DimenUtils;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.news.NewsItem;
import com.yuyh.sprintnba.http.bean.video.VideoInfo;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.support.OnListItemClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.utils.ItemAnimHelper;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class NewsAdapter extends EasyRVAdapter<NewsItem.NewsItemBean> {

    private OnListItemClickListener<NewsItem.NewsItemBean> mOnItemClickListener = null;
    private ItemAnimHelper helper = new ItemAnimHelper();

    public NewsAdapter(List<NewsItem.NewsItemBean> data, Context context) {
        super(context, data, R.layout.item_list_news_normal, R.layout.item_list_news_video);
    }

    public void setOnItemClickListener(OnListItemClickListener<NewsItem.NewsItemBean> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getLayoutIndex(int position, NewsItem.NewsItemBean item) {
        if (item.atype.equals("2"))
            return 1;
        return 0;
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final NewsItem.NewsItemBean item) {
        if (item.atype.equals("2")) { // 视频
            final JCVideoPlayerStandard videoPlayer = viewHolder.getView(R.id.vpVideo);
            // 近期腾讯视频真实地址解析后播放 提示“您未获授权，无法查看此网页。 HTTP403” 故同时支持跳转到网页播放
            ImageView ivGoto = viewHolder.getView(R.id.ivGoto);
            ivGoto.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    BaseWebActivity.start(mContext, item.url, "", true, true);
                }
            });
            videoPlayer.setUp("", item.title);
            if (TextUtils.isEmpty(item.realUrl)) {

                TencentService.getVideoRealUrls(item.vid, new RequestCallback<VideoInfo>() {
                    @Override
                    public void onSuccess(VideoInfo real) {
                        if (real.vl.vi != null && real.vl.vi.size() > 0) {
                            String vid = real.vl.vi.get(0).vid;
                            String vkey = real.vl.vi.get(0).fvkey;
                            String url = real.vl.vi.get(0).ul.ui.get(0).url + vid + ".mp4?vkey=" + vkey;
                            item.realUrl = url;
                            LogUtils.i("title：" + item.title);
                            LogUtils.i("real-url：" + url);
                            videoPlayer.setUp(url, item.title);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        LogUtils.i("real-url：" + message);
                    }
                });
            } else {
                videoPlayer.setUp(item.realUrl, item.title);
            }

            videoPlayer.thumbImageView.setController(FrescoUtils.getController(item.imgurl, videoPlayer.thumbImageView));
            viewHolder.setText(R.id.tvVideoTitle, item.title)
                    .setText(R.id.tvVideoTime, item.pub_time);
            ViewGroup.LayoutParams params = videoPlayer.getLayoutParams();
            params.height = DimenUtils.getScreenWidth() / 2;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            videoPlayer.setLayoutParams(params);
        } else {
            SimpleDraweeView iv = viewHolder.getView(R.id.ivBannerImg);
            if (iv != null) { // @bugreport NullPointerException
                iv.setController(FrescoUtils.getController(item.imgurl, iv));
                ViewGroup.LayoutParams params = iv.getLayoutParams();
                params.height = DimenUtils.getScreenWidth() / 2;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                iv.setLayoutParams(params);
            }
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
        helper.showItemAnim(viewHolder.getItemView(), position);
    }
}
