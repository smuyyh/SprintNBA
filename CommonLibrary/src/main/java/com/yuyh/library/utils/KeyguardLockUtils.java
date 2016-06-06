package com.yuyh.library.utils;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class KeyguardLockUtils {
    private KeyguardManager keyguardManager;
    private KeyguardManager.KeyguardLock keyguardLock;

    public KeyguardLockUtils(Context context, String tag) {
        //获取系统服务
        keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        //初始化键盘锁，可以锁定或解开键盘锁
        keyguardLock = keyguardManager.newKeyguardLock(tag);
    }

    /**
     * 判断是否锁屏
     *
     * @return
     */
    @TargetApi(16)
    public boolean isKeyguardLocked() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        } else {
            return keyguardManager.isKeyguardLocked();
        }

    }

    /**
     * 是否需要锁屏密码
     *
     * @return
     */
    @TargetApi(16)
    public boolean isKeyguardSecure() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        } else {
            return keyguardManager.isKeyguardSecure();
        }
    }

    /**
     * 检测锁屏状态
     *
     * @return
     */
    public boolean inKeyguardRestrictedInputMode() {
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 解锁屏幕
     */
    public void disableKeyguard() {
        keyguardLock.disableKeyguard();
    }

    /**
     * 反解除锁屏：如果在调用disableKeyguard()函数之前是锁屏的，那么就进行锁屏，否则不进行任何操作。
     */
    public void reenableKeyguard() {
        keyguardLock.reenableKeyguard();
    }

    public void release() {
        if (keyguardLock != null) {
            //禁用显示键盘锁定
            keyguardLock.reenableKeyguard();
        }
    }
}
