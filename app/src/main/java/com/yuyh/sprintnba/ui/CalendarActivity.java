package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.view.View;

import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.library.calendarcard.CalendarCardPager;
import com.yuyh.library.calendarcard.CardGridItem;
import com.yuyh.library.calendarcard.OnCellItemClick;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.InjectView;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class CalendarActivity extends BaseSwipeBackCompatActivity {

    @InjectView(R.id.viewPagerCal)
    CalendarCardPager calendar;

    public static final String CALENDAR_DATE = "calendar_data";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("日历");
        calendar.setOnCellItemClick(new OnCellItemClick() {
            @Override
            public void onCellClick(View v, CardGridItem item) {
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.getDate().getTime());
                Intent intent = new Intent();
                intent.putExtra(CALENDAR_DATE, date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
