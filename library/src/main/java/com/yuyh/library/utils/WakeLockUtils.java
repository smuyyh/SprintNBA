package com.yuyh.library.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class WakeLockUtils {
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

    public WakeLockUtils(Context context, String tag) {
        ////获取电源的服务 声明电源管理器
        powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, tag);
    }

    @TargetApi(7)
    public boolean isScreenOn() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR_MR1) {
            return false;
        } else {
            return powerManager.isScreenOn();
        }
    }

    /**
     * 点亮屏幕
     */
    public void turnScreenOn() {
        if (!wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    /**
     * 关闭亮屏
     */
    public void turnScreenOff() {
        if (wakeLock.isHeld()) {
            try {
                wakeLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void release() {
        if (wakeLock != null && wakeLock.isHeld()) {
            try {
                wakeLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
