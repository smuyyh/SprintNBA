package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.match.LiveDetail;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public interface MatchLiveView {

    void addList(List<LiveDetail.LiveDetailData.LiveContent> detail);
}
