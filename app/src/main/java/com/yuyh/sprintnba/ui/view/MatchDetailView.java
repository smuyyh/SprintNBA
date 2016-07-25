package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.match.MatchBaseInfo;
import com.yuyh.sprintnba.ui.view.base.BaseView;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public interface MatchDetailView extends BaseView{

    void showTabViewPager(String names[], boolean isStart);

    void showMatchInfo(MatchBaseInfo.BaseInfo matchBaseInfo);
}
