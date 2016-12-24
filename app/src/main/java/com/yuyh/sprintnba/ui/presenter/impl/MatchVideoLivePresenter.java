package com.yuyh.sprintnba.ui.presenter.impl;

import android.app.Activity;
import android.content.Context;

import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.http.bean.video.VideoLiveInfo;
import com.yuyh.sprintnba.http.bean.video.VideoLiveSource;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.MatchVideoLiveView;
import com.yuyh.sprintnba.utils.TmiaaoUtils;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/12/23.
 */
public class MatchVideoLivePresenter implements Presenter {

    private Context context;
    private MatchVideoLiveView dataView;

    public MatchVideoLivePresenter(Context context, MatchVideoLiveView dataView) {
        this.context = context;
        this.dataView = dataView;
    }

    @Override
    public void initialized() {

        if (dataView != null)
            dataView.showLoading("");

        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<VideoLiveInfo> list = TmiaaoUtils.getLiveList();

                if (context != null && dataView != null) {

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (list == null || list.size() <= 0) {
                                dataView.showError("暂无直播数据");
                            } else {
                                dataView.showLiveList(list);
                            }

                            dataView.hideLoading();
                        }
                    });
                }

            }
        }).start();
    }

    public void getSourceList(final String link) {

        LogUtils.i(link);

        if (dataView != null)
            dataView.showLoading("");

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<VideoLiveSource> list = TmiaaoUtils.getSourceList(link);

                if (context != null && dataView != null) {

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (list == null || list.size() <= 0) {
                                dataView.showError("暂无直播源");
                            } else {
                                dataView.showSourceList(list);
                            }

                            dataView.hideLoading();
                        }
                    });
                }
            }
        }).start();
    }
}
