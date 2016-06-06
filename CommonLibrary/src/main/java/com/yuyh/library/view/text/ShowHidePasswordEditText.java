package com.yuyh.library.view.text;

import android.annotation.TargetApi;
import android.widget.EditText;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.yuyh.library.R;
import com.yuyh.library.utils.DimenUtils;

/**
 * 可以显示和隐藏密码的EditText
 *
 * @author yuyh.
 * @date 16/4/11.
 */
public class ShowHidePasswordEditText extends EditText {

    private boolean isShowingPassword = false;
    private Drawable drawableEnd;
    private Rect bounds;
    private boolean leftToRight = true;
    private int tintColor = 0;

    @DrawableRes
    private int visiblityIndicatorShow = R.drawable.ic_visibility_off;
    @DrawableRes
    private int visiblityIndicatorHide = R.drawable.ic_visibility_on;
    private boolean monospace;

    public ShowHidePasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShowHidePasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShowHidePasswordEditText);

            visiblityIndicatorShow = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_show, visiblityIndicatorShow);
            visiblityIndicatorHide = attrsArray.getResourceId(R.styleable.ShowHidePasswordEditText_drawable_hide, visiblityIndicatorHide);
            monospace = attrsArray.getBoolean(R.styleable.ShowHidePasswordEditText_monospace, true);
            tintColor = attrsArray.getColor(R.styleable.ShowHidePasswordEditText_tint_color, 0);

            attrsArray.recycle();
        }

        leftToRight = isLeftToRight();

        isShowingPassword = false;
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, true);

        if (!monospace) {
            setTypeface(Typeface.DEFAULT);
        }

        if (!TextUtils.isEmpty(getText())) {
            showPasswordVisibilityIndicator(true);
        }

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showPasswordVisibilityIndicator(true);
                } else {
                    showPasswordVisibilityIndicator(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isLeftToRight() {
        // If we are pre JB assume always LTR
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return true;
        }

        Configuration config = getResources().getConfiguration();
        return !(config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {

        //keep a reference to the right drawable so later on touch we can check if touch is on the drawable
        if (leftToRight && right != null) {
            drawableEnd = right;
        } else if (!leftToRight && left != null) {
            drawableEnd = left;
        }
        if(drawableEnd != null) // 点击范围太小了，容易点不到，故增加drawable大小
            drawableEnd.setBounds(0,0, DimenUtils.dpToPxInt(35),DimenUtils.dpToPxInt(35));
        super.setCompoundDrawables(left, top, right, bottom);
    }

    public void setTintColor(@ColorInt int tintColor) {
        this.tintColor = tintColor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP && drawableEnd != null) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if (rect.contains(eventX, eventY)){ // 判断是否在点击范围之内
                togglePasswordVisibility();
                event.setAction(MotionEvent.ACTION_CANCEL); // 防止弹出软键盘
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 显示或隐藏小眼睛
     *
     * @param show
     */
    private void showPasswordVisibilityIndicator(boolean show) {
        if (show) {
            Drawable original = isShowingPassword ?
                    ContextCompat.getDrawable(getContext(), visiblityIndicatorHide) :
                    ContextCompat.getDrawable(getContext(), visiblityIndicatorShow);
            original.mutate();

            if (tintColor == 0) {
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? null : original, null, leftToRight ? original : null, null);
            } else {
                Drawable wrapper = DrawableCompat.wrap(original);
                DrawableCompat.setTint(wrapper, tintColor);
                setCompoundDrawablesWithIntrinsicBounds(leftToRight ? null : wrapper, null, leftToRight ? wrapper : null, null);
            }
        } else {
            setCompoundDrawables(null, null, null, null);
        }
    }

    private void togglePasswordVisibility() {
        if (isShowingPassword) {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD, true);
        } else {
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, true);
        }
        isShowingPassword = !isShowingPassword;
        showPasswordVisibilityIndicator(true);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableEnd = null;
        bounds = null;
        super.finalize();
    }


    private void setInputType(int inputType, boolean keepState) {
        int selectionStart = -1;
        int selectionEnd = -1;
        if (keepState) {
            selectionStart = getSelectionStart();
            selectionEnd = getSelectionEnd();
        }
        setInputType(inputType);
        if (keepState) {
            setSelection(selectionStart, selectionEnd);
        }
    }


    public
    @DrawableRes
    int getVisiblityIndicatorShow() {
        return visiblityIndicatorShow;
    }

    public void setVisiblityIndicatorShow(@DrawableRes int visiblityIndicatorShow) {
        this.visiblityIndicatorShow = visiblityIndicatorShow;
    }

    public
    @DrawableRes
    int getVisiblityIndicatorHide() {
        return visiblityIndicatorHide;
    }

    public void setVisiblityIndicatorHide(@DrawableRes int visiblityIndicatorHide) {
        this.visiblityIndicatorHide = visiblityIndicatorHide;
    }

    public boolean isShowingPassword() {
        return isShowingPassword;
    }
}
