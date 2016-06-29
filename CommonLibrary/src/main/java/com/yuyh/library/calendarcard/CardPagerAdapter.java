package com.yuyh.library.calendarcard;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Calendar;

public class CardPagerAdapter extends PagerAdapter {

    private Context mContext;
    private OnCellItemClick defaultOnCellItemClick;

    private int pre; // 当前月往前显示的月数：eg：pre = 8， 那就往前推8个月开始显示
    private int next;// 往后显示

    public CardPagerAdapter(Context ctx, int pre, int next) {
        mContext = ctx;
        this.pre = pre;
        this.next = next;
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, position - pre);
        CalendarCard card = new CalendarCard(mContext);
        card.setDateDisplay(cal);
        card.notifyChanges();
        if (card.getOnCellItemClick() == null)
            card.setOnCellItemClick(defaultOnCellItemClick);

        ((ViewPager) collection).addView(card, 0);

        return card;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        // TODO almoast ifinite ;-)
        return pre + next;
    }

    public void setDefaultOnCellItemClick(OnCellItemClick defaultOnCellItemClick) {
        this.defaultOnCellItemClick = defaultOnCellItemClick;
    }

}
