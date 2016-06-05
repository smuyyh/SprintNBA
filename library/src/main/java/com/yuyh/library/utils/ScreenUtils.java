package com.yuyh.library.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

/**
 * 屏幕亮度工具类
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class ScreenUtils {

    /**
     * 当前是否是横屏
     *
     * @param context context
     * @return boolean
     */
    public static final boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 当前是否是竖屏
     *
     * @param context context
     * @return boolean
     */
    public static final boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 调整窗口的透明度  1.0f,0.5f 变暗
     *
     * @param from    from>=0&&from<=1.0f
     * @param to      to>=0&&to<=1.0f
     * @param context 当前的activity
     */
    public static void dimBackground(final float from, final float to, Activity context) {
        final Window window = context.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.alpha = (Float) animation.getAnimatedValue();
                        window.setAttributes(params);
                    }
                });
        valueAnimator.start();
    }

    /**
     * 获得当前屏幕亮度的模式
     *
     * @param mContext
     * @return SCREEN_BRIGHTNESS_MODE_AUTOMATIC 自动调节屏幕亮度
     * *       SCREEN_BRIGHTNESS_MODE_MANUAL 手动调节屏幕亮度
     */
    public static int getScreenMode(Context mContext) {
        int screenMode = 0;
        try {
            screenMode = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception localException) {

        }
        return screenMode;
    }

    /**
     * 获得当前屏幕亮度值
     *
     * @param mContext
     * @return 0~100
     */
    public static float getScreenBrightness(Context mContext) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness / 255.0F * 100;
    }

    /**
     * 设置当前屏幕亮度的模式
     *
     * @param paramInt SCREEN_BRIGHTNESS_MODE_AUTOMATIC 自动调节屏幕亮度
     *                 SCREEN_BRIGHTNESS_MODE_MANUAL 手动调节屏幕亮度
     * @param mContext
     */
    public static void setScreenMode(int paramInt, Context mContext) {
        try {
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置当前屏幕亮度值
     *
     * @param paramInt 0~100
     * @param mContext
     */
    public static void saveScreenBrightness(int paramInt, Context mContext) {
        if (paramInt <= 5) {
            paramInt = 5;
        }
        try {
            float f = paramInt / 100.0F * 255;
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Activity的亮度
     *
     * @param paramInt
     * @param mActivity
     */
    public static void setScreenBrightness(int paramInt, Activity mActivity) {
        if (paramInt <= 5) {
            paramInt = 5;
        }
        Window localWindow = mActivity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 100.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }
}
