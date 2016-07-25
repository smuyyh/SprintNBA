package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.widget.calendar.CalConstant;
import com.yuyh.sprintnba.widget.calendar.CalendarView;
import com.yuyh.sprintnba.widget.calendar.ICalendarView;

import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class CalendarActivity extends BaseSwipeBackCompatActivity {

    @InjectView(R.id.calendar)
    CalendarView calendar;

    @InjectView(R.id.btnPrev)
    Button btnPre;
    @InjectView(R.id.tvCalendarDate)
    TextView tvCalendarDate;
    @InjectView(R.id.btnNext)
    Button btnNext;

    public static final String CALENDAR_DATE = "calendar_data";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("日历");
        calendar.setWeekTextStyle(3);
        tvCalendarDate.setText(getYearMonthText(calendar.getYear(), calendar.getMonth()));
        calendar.setOnRefreshListener(new ICalendarView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvCalendarDate.setText(getYearMonthText(calendar.getYear(), calendar.getMonth()));
            }
        });
        calendar.setOnItemClickListener(new ICalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(int day) {
                int year = calendar.getYear();
                int month = calendar.getMonth();
                String date = year + "-" + month + "-" + day;
                LogUtils.i("date = " + date);
                Intent intent = new Intent();
                intent.putExtra(CALENDAR_DATE, date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @OnClick(R.id.btnPrev)
    public void preMonth() {
        Calendar c = calendar.getCalendar();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        calendar.refresh(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    @OnClick(R.id.btnNext)
    public void nextMonth() {
        Calendar c = calendar.getCalendar();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        calendar.refresh(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    private String getYearMonthText(int year, int month) {
        return new StringBuilder().append(CalConstant.MONTH_NAME[month - 1]).append(", ").append(year).toString();
    }
}
