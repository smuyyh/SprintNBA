package com.yuyh.sprintnba.widget.calendar;

import java.util.Calendar;

public interface ICalendarView {

    /**
     * only used for MODE_CALENDAR
     */
    void refresh(int year, int month);

    void setSelectedDayTextColor(int color);

    void setSelectedDayBgColor(int color);

    void setTodayBgColor(int color);

    int daysCompleteTheTask();

    /**
     * used for both
     */
    void setWeekTextStyle(int style);

    void setWeekTextColor(int color);

    void setCalendarTextColor(int color);

    void setWeekTextSizeScale(float scale);

    void setTextSizeScale(float scale);

    int getYear();

    int getMonth();

    int daysOfCurrentMonth();

    Calendar getCalendar();

    void setOnItemClickListener(OnItemClickListener onItemClickListener);

    void setOnRefreshListener(OnRefreshListener onRefreshListener);

    interface OnItemClickListener {
        void onItemClick(int day);
    }

    interface OnRefreshListener {
        void onRefresh();
    }
}
