package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.video.VideoLiveInfo;
import com.yuyh.sprintnba.http.bean.video.VideoLiveSource;
import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/12/23.
 */
public interface MatchVideoLiveView extends BaseView {


    void showLiveList(List<VideoLiveInfo> list);

    void showSourceList(List<VideoLiveSource> list);
}
