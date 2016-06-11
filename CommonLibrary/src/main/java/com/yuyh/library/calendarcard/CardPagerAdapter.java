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

    public CardPagerAdapter(Context ctx) {
        mContext = ctx;
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, position);
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
        return Integer.MAX_VALUE;
    }

    public OnCellItemClick getDefaultOnCellItemClick() {
        return defaultOnCellItemClick;
    }

    public void setDefaultOnCellItemClick(OnCellItemClick defaultOnCellItemClick) {
        this.defaultOnCellItemClick = defaultOnCellItemClick;
    }

}
