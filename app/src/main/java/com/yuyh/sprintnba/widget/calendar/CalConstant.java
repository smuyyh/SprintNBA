package com.yuyh.sprintnba.widget.calendar;

import android.graphics.Color;

import com.yuyh.sprintnba.R;

public class CalConstant {


    public static final int[] WEEK_TEXT = {
            R.array.calendarview_weektext_0,
            R.array.calendarview_weektext_1,
            R.array.calendarview_weektext_2,
            R.array.calendarview_weektext_3};

    public static final int TEXT_COLOR = Color.BLACK;

    public static final int BACKGROUND_COLOR = Color.WHITE;

    public static final int DAYS_OF_MONTH[][] = new int[][]{
            {-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31},
            {-1, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}};

    public static final int MODE_CALENDAR = 0;

    public static final int MODE_SHOW_DATA_OF_THIS_MONTH = 1;

    public static final String[] MONTH_NAME = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

}
