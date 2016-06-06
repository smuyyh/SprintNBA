package com.yuyh.library.view.button;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.yuyh.library.R;


public class SwitchButton extends View {
    private static final String KEY_INSTANCE_STATE = "SWITCHBUTTONINSTANCE";
    private float radius;
    /**
     * 开启颜色
     */
    private int onColor = Color.parseColor("#4ebb7f");
    /**
     * 关闭颜色
     */
    private int offBorderColor = Color.parseColor("#dadbda");
    /**
     * 灰色带颜色
     */
    private int offColor = Color.parseColor("#e5e5e5");
    /**
     * 手柄颜色
     */
    private int spotColor = Color.parseColor("#ffffff");
    /**
     * 边框颜色
     */
    private int borderColor = offBorderColor;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 开关状态
     */
    private boolean toggleOn = false;
    /**
     * 边框大小
     */
    private int borderWidth = 2;
    /**
     * 垂直中心
     */
    private float centerY;
    /**
     * 按钮的开始和结束位置
     */
    private float startX, endX;
    /**
     * 手柄X位置的最小和最大值
     */
    private float spotMinX, spotMaxX;
    /**
     * 手柄大小
     */
    private int spotSize;
    /**
     * 手柄X位置
     */
    private float spotX;
    /**
     * 关闭时内部灰色带高度
     */
    private float offLineWidth;
    private RectF rect = new RectF();
    /**
     * 默认使用动画
     */
    private boolean defaultAnimate = true;

    private OnToggleChanged listener;

    private int animate_time = 150;
    public int min_width = 50;//dp
    public int min_height = 25;//dp

    private boolean is_touch = false;//判断是否是触摸状态
    private boolean is_rect = false;//判断是否是触摸状态

    private SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Style.FILL);
        paint.setStrokeCap(Cap.ROUND);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        offBorderColor = typedArray.getColor(R.styleable.SwitchButton_offBorderColor, offBorderColor);
        onColor = typedArray.getColor(R.styleable.SwitchButton_onColor, onColor);
        spotColor = typedArray.getColor(R.styleable.SwitchButton_spotColor, spotColor);
        offColor = typedArray.getColor(R.styleable.SwitchButton_offColor, offColor);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.SwitchButton_swBorderWidth, borderWidth);
        defaultAnimate = typedArray.getBoolean(R.styleable.SwitchButton_animate, defaultAnimate);
        is_rect = typedArray.getBoolean(R.styleable.SwitchButton_isRect, is_rect);
        typedArray.recycle();

        borderColor = offBorderColor;
    }

    /**
     * 切换状态
     */
    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean animate) {
        toggleOn = !toggleOn;
        takeEffect(animate);

        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void toggleOn() {
        setToggleOn();
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void toggleOff() {
        setToggleOff();
        if (listener != null) {
            listener.onToggle(toggleOn);
        }
    }

    public void setAnimateTime(int time) {
        this.animate_time = time;
    }

    /**
     * 设置显示成打开样式，不会触发toggle事件
     */
    public void setToggleOn() {
        setToggleOn(true);
    }

    public void setToggleOn(boolean animate) {
        toggleOn = true;
        takeEffect(animate);
    }

    /**
     * 设置显示成关闭样式，不会触发toggle事件
     */
    public void setToggleOff() {
        setToggleOff(true);
    }

    public void setToggleOff(boolean animate) {
        toggleOn = false;
        takeEffect(animate);
    }

    /**
     * 执行效果
     *
     * @param animate true表示有动画效果 false直接执行计算并显示最终打开"1"或者关闭"0"的效果绘制
     */
    private void takeEffect(boolean animate) {
        if (animate) {
            slide();
        } else {
            calculateEffect(toggleOn ? 1 : 0);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;

        Resources r = Resources.getSystem();
        if (widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, min_width, r.getDisplayMetrics());//如果为指定大小宽最小50dp
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, min_height, r.getDisplayMetrics());//如果为指定大小高最小25dp
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final int width = getWidth();
        final int height = getHeight();

        radius = Math.min(width, height) * 0.5f;
        centerY = radius;
        startX = radius;
        endX = width - radius;
        spotMinX = startX + borderWidth;
        spotMaxX = endX - borderWidth;
        spotSize = height - 2 * borderWidth;
        spotX = toggleOn ? spotMaxX : spotMinX;

        offLineWidth = spotSize;
    }

    /**
     * 这里偷个懒，直接使用空的animation，根据当前interpolatedTime（0~1）渐变过程来绘制不同阶段的View，达到动画效果
     * 当然，也可以开启个线程或者定时任务，来实现从0到1的变换，劲儿改变视图绘制过程
     */
    private void slide() {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (toggleOn) {
                    calculateEffect(interpolatedTime);
                } else {
                    calculateEffect(1 - interpolatedTime);
                }
            }
        };
        animation.setDuration(animate_time);
        clearAnimation();
        startAnimation(animation);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }


    @Override
    public void draw(Canvas canvas) {
        rect.set(0, 0, getWidth(), getHeight());
        paint.setColor(borderColor);
        if (is_rect) {
            canvas.drawRoundRect(rect, 0, 0, paint);
        } else {
            canvas.drawRoundRect(rect, radius, radius, paint);
        }


        if (offLineWidth > 0) {
            final float cy = offLineWidth * 0.5f;
            rect.set(spotX - cy - borderWidth, centerY - cy, endX + cy, centerY + cy);
            paint.setColor(offColor);
            if (is_rect) {
                canvas.drawRoundRect(rect, 0, 0, paint);
            } else {
                canvas.drawRoundRect(rect, cy, cy, paint);
            }
        }

        final float spotR = spotSize * 0.5f;
        if (is_touch) {
            if (spotX < getWidth() / 2) {
                rect.set(spotX - spotR - borderWidth, centerY - spotR, spotX + spotR + spotR / 8, centerY + spotR);
            } else {
                rect.set(spotX - spotR - spotR / 8, centerY - spotR, spotX + spotR + borderWidth, centerY + spotR);
            }
        } else {
            if (spotX < getWidth() / 2) {
                rect.set(spotX - spotR - borderWidth, centerY - spotR, spotX + spotR, centerY + spotR);
            } else {
                rect.set(spotX - spotR, centerY - spotR, spotX + spotR + borderWidth, centerY + spotR);
            }
        }
        paint.setColor(spotColor);
        if (is_rect) {
            canvas.drawRoundRect(rect, 0, 0, paint);
        } else {
            canvas.drawRoundRect(rect, spotR, spotR, paint);
        }
    }

    /**
     * 计算绘制位置
     * mapValueFromRangeToRange方法计算从当前位置相对于目标位置所对应的值
     * 通过颜色变化来达到透明度动画效果（颜色渐变）
     */
    private void calculateEffect(final double value) {
        final float mapToggleX = (float) mapValueFromRangeToRange(value, 0, 1, spotMinX, spotMaxX);
        spotX = mapToggleX;

        float mapOffLineWidth = (float) mapValueFromRangeToRange(1 - value, 0, 1, 10, spotSize);

        offLineWidth = mapOffLineWidth;

        final int fb = Color.blue(onColor);
        final int fr = Color.red(onColor);
        final int fg = Color.green(onColor);

        final int tb = Color.blue(offBorderColor);
        final int tr = Color.red(offBorderColor);
        final int tg = Color.green(offBorderColor);

        int sb = (int) mapValueFromRangeToRange(1 - value, 0, 1, fb, tb);
        int sr = (int) mapValueFromRangeToRange(1 - value, 0, 1, fr, tr);
        int sg = (int) mapValueFromRangeToRange(1 - value, 0, 1, fg, tg);

        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);

        borderColor = Color.rgb(sr, sg, sb);

        postInvalidate();
    }

    public interface OnToggleChanged {
        void onToggle(boolean on);
    }


    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        listener = onToggleChanged;
    }

    public boolean isAnimate() {
        return defaultAnimate;
    }

    public void setAnimate(boolean animate) {
        this.defaultAnimate = animate;
    }

    /**
     * Map a value within a given range to another range.
     *
     * @param value    the value to map
     * @param fromLow  the low end of the range the value is within
     * @param fromHigh the high end of the range the value is within
     * @param toLow    the low end of the range to map to
     * @param toHigh   the high end of the range to map to
     * @return the mapped value
     */
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        double fromRangeSize = fromHigh - fromLow;
        double toRangeSize = toHigh - toLow;
        double valueScale = (value - fromLow) / fromRangeSize;
        return toLow + (valueScale * toRangeSize);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putBoolean(KEY_INSTANCE_STATE, toggleOn);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            boolean isChecked = bundle.getBoolean(KEY_INSTANCE_STATE);
            if (isChecked) {
                setToggleOn();
            } else {
                setToggleOff();
            }
            super.onRestoreInstanceState(bundle.getParcelable(KEY_INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                is_touch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                is_touch = false;
                toggle(defaultAnimate);
                break;
        }
        invalidate();
        return true;
    }
}
