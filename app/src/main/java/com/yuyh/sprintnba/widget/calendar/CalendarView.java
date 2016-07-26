package com.yuyh.sprintnba.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;

public class CalendarView extends View implements View.OnTouchListener, ICalendarView {

    private int selectedYear;

    private int selectedMonth;

    /**
     * correspond to xxxx(year)-xx(month)-1(day)
     */
    private Calendar calendar;

    /**
     * the calendar needs a 6*7 matrix to store
     * date[i] represents the day of position i
     */
    private int[] date = new int[42];

    /**
     * the width of the telephone screen
     */
    private int screenWidth;

    /**
     * the index in date[] of the first day of current month
     */
    private int curStartIndex;

    /**
     * the index in date[] of the last day of current month
     */
    private int curEndIndex;

    /**
     * the index in date[] of today
     */
    private int todayIndex = -1;

    /**
     * [only used for MODE_SHOW_DATA_OF_THIS_MONTH]
     * data[i] indicates whether completing the plan on the ith day of current month (such as running in JOY RUN)
     */
    private boolean[] data = new boolean[32];

    /**
     * record the index in date[] of the last ACTION_DOWN event
     */
    private int actionDownIndex = -1;

    /**
     * record the selected index in date[]
     */
    private int selectedIndex = -1;

    private OnItemClickListener onItemClickListener;

    private OnRefreshListener onRefreshListener;

    /**
     * following are some parameters for rendering the widget
     */
    private float cellWidth;
    private float cellHeight;
    private String[] weekText;
    private int textColor = CalConstant.TEXT_COLOR;
    private int backgroundColor = CalConstant.BACKGROUND_COLOR;
    private Paint textPaint;
    private Paint weekTextPaint;
    private Paint todayBgPaint;
    private Paint selectedDayBgPaint;
    private Paint selectedDayTextPaint;


    public CalendarView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        /**
         * default choose the current month of real life
         */
        calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        /**
         * get the width of the screen of the phone
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

        /**
         * set the width and height of a cell of the calendar
         */
        cellWidth = screenWidth / 7f;
        cellHeight = cellWidth * 0.7f;

        setBackgroundColor(backgroundColor);

        weekText = getResources().getStringArray(CalConstant.WEEK_TEXT[0]);

        setOnTouchListener(this);

        textPaint = RenderUtil.getPaint(textColor);
        textPaint.setTextSize(cellHeight * 0.4f);

        /**
         * paint for head of the calendar
         */
        weekTextPaint = RenderUtil.getPaint(textColor);
        weekTextPaint.setTextSize(cellHeight * 0.4f);
        weekTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

        todayBgPaint = RenderUtil.getPaint(Color.parseColor("#FF5055"));
        todayBgPaint.setStrokeWidth(3);
        todayBgPaint.setStyle(Paint.Style.STROKE);

        selectedDayBgPaint = RenderUtil.getPaint(Color.parseColor("#FF5055"));

        selectedDayTextPaint = RenderUtil.getPaint(Color.WHITE);
        selectedDayTextPaint.setTextSize(cellHeight * 0.4f);

        initial();

    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight(), MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * calculate the total height of the widget
     */
    private int measureHeight() {
        /**
         * the weekday of the first day of the month, Sunday's result is 1 and Monday 2 and Saturday 7, etc.
         */
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        /**
         * the number of days of current month
         */
        int daysOfMonth = daysOfCurrentMonth();
        /**
         * calculate the total lines, which equals to 1 (head of the calendar) + 1 (the first line) + n/7 + (n%7==0?0:1)
         * and n means number of days except first line of the calendar
         */
        int n = -1;
        if (dayOfWeek >= 2 && dayOfWeek <= 7) {
            n = daysOfMonth - (8 - dayOfWeek + 1);
        } else if (dayOfWeek == 1) {
            n = daysOfMonth - 1;
        }
        int lines = 2 + n / 7 + (n % 7 == 0 ? 0 : 1);
        return (int) (cellHeight * lines);
    }

    /**
     * calculate the values of date[] and the legal range of index of date[]
     */
    private void initial() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int monthStart = -1;
        if (dayOfWeek >= 2 && dayOfWeek <= 7) {
            monthStart = dayOfWeek - 2;
        } else if (dayOfWeek == 1) {
            monthStart = 6;
        }
        curStartIndex = monthStart;
        date[monthStart] = 1;
        int daysOfMonth = daysOfCurrentMonth();
        for (int i = 1; i < daysOfMonth; i++) {
            date[monthStart + i] = i + 1;
        }
        curEndIndex = monthStart + daysOfMonth;
        //the year and month selected is the current year and month
        if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) {
            todayIndex = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + monthStart - 1;
        } else {
            todayIndex = -1;
        }
    }

    /**
     * y is bigger than the head of the calendar, meaning that the coordination may represent a day
     * of the calendar
     */
    private boolean coordIsCalendarCell(float y) {
        return y > cellHeight;
    }

    /**
     * calculate the index of date[] according to the coordination
     */
    private int getIndexByCoordinate(float x, float y) {
        int m = (int) (Math.floor(x / cellWidth) + 1);
        int n = (int) (Math.floor((y - cellHeight) / cellHeight) + 1);
        return (n - 1) * 7 + m - 1;
    }

    /**
     * whether the index is legal
     *
     * @param i the index in date[]
     * @return
     */
    private boolean isLegalIndex(int i) {
        return !isIllegalIndex(i);
    }

    /**
     * whether the index is illegal
     *
     * @param i the index in date[]
     * @return
     */
    private boolean isIllegalIndex(int i) {
        return i < curStartIndex || i >= curEndIndex;
    }

    /**
     * calculate the x position according to the index in date[]
     *
     * @param i the index in date[]
     * @return
     */
    private int getXByIndex(int i) {
        return i % 7 + 1;
    }

    /**
     * calculate the y position according to the index in date[]
     *
     * @param i the index in date[]
     * @return
     */
    private int getYByIndex(int i) {
        return i / 7 + 1;
    }


    /**
     * render
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        /**
         * render the head
         */
        float baseline = RenderUtil.getBaseline(0, cellHeight, weekTextPaint);
        for (int i = 0; i < 7; i++) {
            float weekTextX = RenderUtil.getStartX(cellWidth * i + cellWidth * 0.5f, weekTextPaint, weekText[i]);
            canvas.drawText(weekText[i], weekTextX, baseline, weekTextPaint);
        }

        for (int i = curStartIndex; i < curEndIndex; i++) {
            if (i == todayIndex && i == selectedIndex) {
                drawCircle(canvas, i, selectedDayBgPaint, cellHeight * 0.48f);
                drawText(canvas, i, selectedDayTextPaint, "" + date[i]);
            } else if (i == todayIndex) {
                drawCircle(canvas, i, todayBgPaint, cellHeight * 0.48f);
                drawText(canvas, i, textPaint, "" + date[i]);
            } else if (i == selectedIndex) {
                drawCircle(canvas, i, selectedDayBgPaint, cellHeight * 0.48f);
                drawText(canvas, i, selectedDayTextPaint, "" + date[i]);
            } else {
                drawText(canvas, i, textPaint, "" + date[i]);
            }

        }
    }

    /**
     * draw text, around the middle of the cell decided by the index
     */
    private void drawText(Canvas canvas, int index, Paint paint, String text) {
        if (isIllegalIndex(index)) {
            return;
        }
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        float top = cellHeight + (y - 1) * cellHeight;
        float bottom = top + cellHeight;
        float baseline = RenderUtil.getBaseline(top, bottom, paint);
        float startX = RenderUtil.getStartX(cellWidth * (x - 1) + cellWidth * 0.5f, paint, text);
        canvas.drawText(text, startX, baseline, paint);
    }

    /**
     * draw circle, around the middle of the cell decided by the index
     */
    private void drawCircle(Canvas canvas, int index, Paint paint, float radius) {
        if (isIllegalIndex(index)) {
            return;
        }
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        float centreY = cellHeight + (y - 1) * cellHeight + cellHeight * 0.5f;
        float centreX = cellWidth * (x - 1) + cellWidth * 0.5f;
        canvas.drawCircle(centreX, centreY, radius, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (coordIsCalendarCell(y)) {
                    int index = getIndexByCoordinate(x, y);
                    if (isLegalIndex(index)) {
                        actionDownIndex = index;
                        selectedIndex = -1;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (coordIsCalendarCell(y)) {
                    int actionUpIndex = getIndexByCoordinate(x, y);
                    if (isLegalIndex(actionUpIndex)) {
                        if (actionDownIndex == actionUpIndex) {
                            selectedIndex = actionUpIndex;
                            actionDownIndex = -1;
                            int day = date[actionUpIndex];
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(day);
                            }
                            invalidate();
                        }
                    }
                }
                break;
        }
        return true;
    }

    private static int leap(int year) {
        return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) ? 1 : 0;
    }

    @Override
    public int daysOfCurrentMonth() {
        return CalConstant.DAYS_OF_MONTH[leap(selectedYear)][selectedMonth];
    }

    @Override
    public int getYear() {
        return selectedYear;
    }

    /**
     * legal values : 1-12
     */
    @Override
    public int getMonth() {
        return selectedMonth;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * used for MODE_CALENDAR
     * legal values of month: 1-12
     */
    @Override
    public void refresh(int year, int month) {
        selectedYear = year;
        selectedMonth = month;
        selectedIndex = -1;
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        initial();
        invalidate();
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    /**
     * legal values : 0-3
     */
    @Override
    public void setWeekTextStyle(int style) {
        if (style >= 0 && style <= 3) {
            weekText = getResources().getStringArray(CalConstant.WEEK_TEXT[style]);
        }
    }

    @Override
    public void setWeekTextColor(int color) {
        weekTextPaint.setColor(color);
    }

    @Override
    public void setCalendarTextColor(int color) {
        textPaint.setColor(color);
    }

    /**
     * legal values : 0-1
     */
    @Override
    public void setWeekTextSizeScale(float scale) {
        if (scale >= 0 && scale <= 1) {
            weekTextPaint.setTextSize(cellHeight * 0.5f * scale);
        }
    }

    /**
     * legal values : 0-1
     */
    @Override
    public void setTextSizeScale(float scale) {
        if (scale >= 0 && scale <= 1) {
            textPaint.setTextSize(cellHeight * 0.5f * scale);
            selectedDayTextPaint.setTextSize(cellHeight * 0.5f * scale);
        }
    }


    @Override
    public void setSelectedDayTextColor(int color) {
        selectedDayTextPaint.setColor(color);
    }

    @Override
    public void setSelectedDayBgColor(int color) {
        selectedDayBgPaint.setColor(color);
    }

    @Override
    public void setTodayBgColor(int color) {
        todayBgPaint.setColor(color);
    }

    @Override
    public Calendar getCalendar() {
        return calendar;
    }


    @Override
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /**
     * used for MODE_SHOW_DATA_OF_THIS_MONTH
     */
    @Override
    public int daysCompleteTheTask() {
        int k = 0;
        for (int i = 1; i <= daysOfCurrentMonth(); i++) {
            k += data[i] ? 1 : 0;
        }
        return k;
    }
}
