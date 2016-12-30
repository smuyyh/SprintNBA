package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.video.MatchVideo;
import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/12/30.
 */

public interface MatchVideoView extends BaseView {

    void showMatchVideo(List<MatchVideo.VideoBean> list);
}
