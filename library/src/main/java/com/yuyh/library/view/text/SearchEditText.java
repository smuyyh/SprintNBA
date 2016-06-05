package com.yuyh.library.view.text;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yuyh.library.R;
import com.yuyh.library.utils.DimenUtils;


/**
 * 仿IOS搜索框
 * <p/>
 * Created by fml on 2015/10/29 0029.
 */
public class SearchEditText extends EditText implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    private static final String TAG = "SearchEditText";
    /**
     * 图标是否默认在左边
     */
    private boolean isIconLeft = false;
    /**
     * 是否点击软键盘搜索
     */
    private boolean pressSearch = false;
    /**
     * 软键盘搜索键监听
     */
    private OnSearchClickListener listener;

    private Drawable[] drawables; // 控件的图片资源
    private Drawable drawableLeft, drawableDel; // 搜索图标和删除按钮图标
    private int eventX, eventY; // 记录点击坐标
    private Rect rect; // 控件区域

    public SearchEditText(Context context) {
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }


    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SearchEditText);
        int drawableStart = ta.getResourceId(R.styleable.SearchEditText_drawableStart, R.drawable.search_icon);
        setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart, 0, 0, 0);
        setCompoundDrawablePadding(DimenUtils.dpToPxInt(5));
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
        addTextChangedListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (isIconLeft) { // 如果是默认样式，直接绘制
            if (length() < 1) {
                drawableDel = null;
            }
            this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableDel, null);
            super.onDraw(canvas);
        } else { // 如果不是默认样式，需要将图标绘制在中间
            if (drawables == null) drawables = getCompoundDrawables();
            if (drawableLeft == null) drawableLeft = drawables[0];
            float textWidth = getPaint().measureText(getHint().toString());
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = -1;
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
            super.onDraw(canvas);
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // 被点击时，恢复默认样式
        if (!pressSearch && TextUtils.isEmpty(getText().toString())) {
            isIconLeft = hasFocus;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        pressSearch = (keyCode == KeyEvent.KEYCODE_ENTER);
        if (pressSearch && listener != null) {
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            listener.onSearchClick(v);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 清空edit内容
        if (drawableDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            Log.i(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            if (rect == null) rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        }
        // 删除按钮被按下时改变图标样式
        if (drawableDel != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            Log.i(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            if (rect == null) rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            if (rect.contains(eventX, eventY))
                drawableDel = this.getResources().getDrawable(R.drawable.edit_delete_pressed_icon);
        } else {
            drawableDel = this.getResources().getDrawable(R.drawable.edit_delete_icon);
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void afterTextChanged(Editable arg0) {
        if (this.length() < 1) {
            drawableDel = null;
        } else {
            drawableDel = this.getResources().getDrawable(R.drawable.edit_delete_icon);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchClickListener {
        void onSearchClick(View view);
    }
}
