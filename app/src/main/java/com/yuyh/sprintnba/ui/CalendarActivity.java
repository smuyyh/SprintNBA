package com.yuyh.sprintnba.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.http.bean.match.MatchCalendar;
import com.yuyh.sprintnba.ui.presenter.impl.CalendarPagePresenter;
import com.yuyh.sprintnba.ui.view.CalendarPageView;
import com.yuyh.sprintnba.widget.calendar.CalConstant;
import com.yuyh.sprintnba.widget.calendar.CalendarView;
import com.yuyh.sprintnba.widget.calendar.ICalendarView;

import java.lang.reflect.Field;
import java.util.Calendar;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author yuyh.
 * @date 16/6/11.
 */
public class CalendarActivity extends BaseSwipeBackCompatActivity implements CalendarPageView {

    @InjectView(R.id.calendar)
    CalendarView calendar;
    @InjectView(R.id.tvCalendarDate)
    TextView tvCalendarDate;
    @InjectView(R.id.tvMatchNum)
    TextView tvMatchNum;

    private MatchCalendar.MatchCalendarBean.MatchNum matchNum;
    private CalendarPagePresenter presenter;
    public static final String CALENDAR_DATE = "calendar_data";
    private String date;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("日期选择");
        presenter = new CalendarPagePresenter(this, this);
        presenter.getMatchCount(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);

        calendar.setWeekTextStyle(1);
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
                CalendarActivity.this.date = date;
                LogUtils.i("date = " + date);
                showMatchNum(date, day);
            }
        });
    }

    private void showMatchNum(String date, int day) {
        if (matchNum != null) {
            Class numCla = (Class) matchNum.getClass();
            try {
                Field fs = numCla.getField("num" + day);
                LogUtils.i(fs.getName());

                String num = fs.get(matchNum) == null ? "0" : (String) fs.get(matchNum);
                tvMatchNum.setText(date + " 共" + num + "场比赛");
            } catch (Exception e) {
                LogUtils.e(e.toString());
                tvMatchNum.setText("");
            }
        } else {
            tvMatchNum.setText("");
        }
    }

    @OnClick(R.id.btnPrev)
    public void preMonth() {
        Calendar c = calendar.getCalendar();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        calendar.refresh(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        matchNum = null;
        tvMatchNum.setText("");
        presenter.getMatchCount(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    @OnClick(R.id.btnNext)
    public void nextMonth() {
        Calendar c = calendar.getCalendar();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
        calendar.refresh(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
        matchNum = null;
        tvMatchNum.setText("");
        presenter.getMatchCount(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);
    }

    private String getYearMonthText(int year, int month) {
        return new StringBuilder().append(CalConstant.MONTH_NAME[month - 1]).append(", ").append(year).toString();
    }

    @Override
    public void renderMatchCount(MatchCalendar.MatchCalendarBean.MatchNum matchNum) {
        this.matchNum = matchNum;
    }

    @Override
    public void showLoadding() {
        showLoadingDialog();
    }

    @Override
    public void hideLoadding() {
        hideLoadingDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_calendar_ok) {
            Intent intent = new Intent();
            intent.putExtra(CALENDAR_DATE, date);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
