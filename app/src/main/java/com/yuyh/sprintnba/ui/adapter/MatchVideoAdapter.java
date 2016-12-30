package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.video.MatchVideo;
import com.yuyh.sprintnba.http.bean.video.VideoInfo;
import com.yuyh.sprintnba.support.NoDoubleClickListener;
import com.yuyh.sprintnba.utils.FrescoUtils;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author yuyh.
 * @date 2016/12/30.
 */
public class MatchVideoAdapter extends EasyRVAdapter<MatchVideo.VideoBean> {

    public MatchVideoAdapter(Context context, List<MatchVideo.VideoBean> list) {
        super(context, list, R.layout.item_list_match_video);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, final MatchVideo.VideoBean item) {
        ImageView ivGoto = viewHolder.getView(R.id.ivGoto);
        ivGoto.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                //BaseWebActivity.start(mContext, item., "", true, true);
            }
        });

        final JCVideoPlayerStandard videoPlayer = viewHolder.getView(R.id.vpVideo);
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
        viewHolder.setText(R.id.tvVideoLength, item.duration);
    }
}
