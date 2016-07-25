package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.match.MatchCalendar;

/**
 * @author yuyh.
 * @date 2016/7/25.
 */
public interface CalendarPageView {

    void renderMatchCount(MatchCalendar.MatchCalendarBean.MatchNum matchNum);

    void showLoadding();

    void hideLoadding();

}
