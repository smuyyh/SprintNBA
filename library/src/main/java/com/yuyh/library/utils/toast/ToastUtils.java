package com.yuyh.library.utils.toast;

import android.content.Context;
import android.widget.Toast;

import com.yuyh.library.AppUtils;

/**
 * Toast工具类，解决多个Toast同时出现的问题
 *
 * @author yuyh.
 * @date 16/4/9.
 */
public class ToastUtils {

    private Toast mToast;
    private Context context = AppUtils.getAppContext();

    /********************** 非连续弹出的Toast ***********************/
    public void showSingleToast(int resId) { //R.string.**
        getSingleToast(resId, Toast.LENGTH_SHORT).show();
    }

    public void showSingleToast(String text) {
        getSingleToast(text, Toast.LENGTH_SHORT).show();
    }

    public void showSingleLongToast(int resId) {
        getSingleToast(resId, Toast.LENGTH_LONG).show();
    }

    public void showSingleLongToast(String text) {
        getSingleToast(text, Toast.LENGTH_LONG).show();
    }

    /*********************** 连续弹出的Toast ************************/
    public void showToast(int resId) {
        getToast(resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        getToast(text, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(int resId) {
        getToast(resId, Toast.LENGTH_LONG).show();
    }

    public void showLongToast(String text) {
        getToast(text, Toast.LENGTH_LONG).show();
    }

    public Toast getSingleToast(int resId, int duration) { // 连续调用不会连续弹出，只是替换文本
        return getSingleToast(context.getResources().getText(resId).toString(), duration);
    }

    public Toast getSingleToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public Toast getToast(int resId, int duration) { // 连续调用会连续弹出
        return getToast(context.getResources().getText(resId).toString(), duration);
    }

    public Toast getToast(String text, int duration) {
        return Toast.makeText(context, text, duration);
    }
}
