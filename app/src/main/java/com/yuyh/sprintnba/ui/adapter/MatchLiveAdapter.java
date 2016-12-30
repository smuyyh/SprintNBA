package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseWebActivity;
import com.yuyh.sprintnba.http.bean.match.LiveDetail;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.ui.ImagePreViewActivity;
import com.yuyh.sprintnba.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchLiveAdapter extends EasyLVAdapter<LiveDetail.LiveContent> {

    public MatchLiveAdapter(List<LiveDetail.LiveContent> mList, Context context, int... layoutIds) {
        super(context, mList, layoutIds);
    }

    @Override
    public void convert(EasyLVHolder viewHolder, int position, LiveDetail.LiveContent item) {
        viewHolder.setText(R.id.tvLiveTime, item.time)
                .setText(R.id.tvLiveTeam, item.teamName)
                .setText(R.id.tvLiveContent, item.content);
        if (!(TextUtils.isEmpty(item.leftGoal) || TextUtils.isEmpty(item.rightGoal))) {
            viewHolder.setVisible(R.id.tvLiveScore, true);
            viewHolder.setText(R.id.tvLiveScore, item.leftGoal + ":" + item.rightGoal);
        } else {
            viewHolder.setVisible(R.id.tvLiveScore, View.INVISIBLE);
        }

        SimpleDraweeView image = viewHolder.getView(R.id.ivLiveImage);
        if ("图片".equals(item.time) && item.image != null
                && item.image.urls != null && item.image.urls.size() > 0) {
            final List<LiveDetail.UrlsBean> urls = item.image.urls;
            image.setVisibility(View.VISIBLE);
            image.setController(FrescoUtils.getController(urls.get(0).small, image));
            if (!TextUtils.isEmpty(urls.get(0).large)) {
                image.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        ImagePreViewActivity.start(mContext, new ArrayList<String>() {{
                            add(urls.get(0).large);
                        }}, urls.get(0).large);
                    }
                });
            }
        } else {
            image.setVisibility(View.GONE);
        }

        SimpleDraweeView video = viewHolder.getView(R.id.ivLiveVideo);
        if ("视频".equals(item.time) && item.video != null) {
            final LiveDetail.VideoBean videoBean = item.video;
            if (!TextUtils.isEmpty(videoBean.pic_160x90)) {
                video.setVisibility(View.VISIBLE);
                video.setController(FrescoUtils.getController(videoBean.pic_160x90, video));
                video.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        BaseWebActivity.start(mContext, videoBean.playurl, "", true, true);
                    }
                });
            } else {
                video.setVisibility(View.GONE);
            }
        } else {
            video.setVisibility(View.GONE);
        }

        if ("1".equals(item.ctype) && TextUtils.isEmpty(item.time)) {
            viewHolder.setText(R.id.tvLiveTime, "结束");
        }
    }
}
