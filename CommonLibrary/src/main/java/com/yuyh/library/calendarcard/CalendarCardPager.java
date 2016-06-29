package com.yuyh.library.calendarcard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class CalendarCardPager extends ViewPager {

    private CardPagerAdapter mCardPagerAdapter;
    private OnCellItemClick mOnCellItemClick;

    private int pre = 6; // 往前显示半年
    private int next = 12; // 往后显示一年

    public CalendarCardPager(Context context) {
        this(context, null);
    }

    public CalendarCardPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarCardPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mCardPagerAdapter = new CardPagerAdapter(context, pre, next);
        setAdapter(mCardPagerAdapter);
        setCurrentItem(pre);
    }

    public CardPagerAdapter getCardPagerAdapter() {
        return mCardPagerAdapter;
    }

    public OnCellItemClick getOnCellItemClick() {
        return mOnCellItemClick;
    }

    public void setOnCellItemClick(OnCellItemClick mOnCellItemClick) {
        this.mOnCellItemClick = mOnCellItemClick;
        mCardPagerAdapter.setDefaultOnCellItemClick(this.mOnCellItemClick);
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View v = getChildAt(i);
                if (v instanceof CalendarCard) {
                    ((CalendarCard) v).setOnCellItemClick(this.mOnCellItemClick);
                }
            }
        }
    }

}
