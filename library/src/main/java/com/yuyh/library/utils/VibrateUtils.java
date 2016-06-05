package com.yuyh.library.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * 振动器
 *
 * @author yuyh.
 * @date 16/4/9.
 */
public class VibrateUtils {

    /**
     * 控制手机振动的毫秒数
     *
     * @param context
     * @param milliseconds
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    /**
     * 指定手机以pattern模式振动
     *
     * @param context
     * @param pattern new long[]{400,800,1200,1600}，就是指定在400ms、800ms、1200ms、1600ms这些时间点交替启动、关闭手机振动器
     * @param repeat  指定pattern数组的索引，指定pattern数组中从repeat索引开始的振动进行循环。-1表示只振动一次，非-1表示从 pattern的指定下标开始重复振动。
     */
    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }

    public static void cancel(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
    }

}
