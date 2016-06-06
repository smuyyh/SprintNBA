package com.yuyh.library.utils;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

/**
 * 剪贴板工具类
 *
 * @author yuyh.
 * @date 16/4/9.
 */
public class ClipboardUtils {

    /***** API<11 *****/
    /**
     * 复制内容到剪贴板
     *
     * @param context
     * @param text
     */
    public static void copyToClipboardSupport(Context context, String text) {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(text);
    }

    /**
     * 获取剪贴板内容
     *
     * @param context
     */
    public static void getLatestTextSupport(Context context) {
        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.getText();
    }


    /***** API>=11 *****/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static int getItemCount(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = clipboard.getPrimaryClip();
        return data.getItemCount();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static String getText(Context context, int index) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > index) {
            return String.valueOf(clip.getItemAt(0).coerceToText(context));
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static String getLatestText(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return String.valueOf(clip.getItemAt(0).coerceToText(context));
        }
        return null;
    }

}
