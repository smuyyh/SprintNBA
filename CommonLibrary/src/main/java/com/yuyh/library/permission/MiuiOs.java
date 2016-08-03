package com.yuyh.library.permission;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 参考 http://blog.csdn.net/tgbus18990140382/article/details/47058043
 * Created by hupei on 2016/7/22.
 */
public class MiuiOs {
    public static final String UNKNOWN = "UNKNOWN";

    /**
     * 获取应用权限设置 Intent <br>
     * http://blog.csdn.net/dawanganban/article/details/41749911
     *
     * @param context
     */
    public static Intent getSettingIntent(Context context) {
        // 之兼容miui v5/v6/v7  的应用权限设置页面
        if (getSystemVersionCode() >= 6) {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            return intent;
        }
        return null;
    }

    /**
     * 获取 V5/V6 后面的数字
     *
     * @return
     */
    public static int getSystemVersionCode() {
        String systemProperty = getSystemProperty();
        if (!TextUtils.isEmpty(systemProperty) && !systemProperty.equals(UNKNOWN)
                && systemProperty.length() == 2 && systemProperty.toUpperCase().startsWith("V")) {
            Integer code = 0;
            try {
                code = Integer.valueOf(systemProperty.substring(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return code;
        }
        return 0;
    }

    /**
     * 判断V5/V6
     *
     * @return V5 、V6 、V7 字符
     */
    public static String getSystemProperty() {
        String line = UNKNOWN;
        BufferedReader reader = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = reader.readLine();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return line;
    }

    /**
     * 判断 activity 是否存在
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (intent == null) return false;
        return context.getPackageManager().queryIntentActivities(intent, 0).size() > 0;
    }

    /**
     * 检查手机是否是miui
     *
     * @return
     * @ref http://dev.xiaomi.com/doc/p=254/index.html
     */
    public static boolean isMIUI() {
        String device = Build.MANUFACTURER;
        if (device.equals("Xiaomi")) {
            return true;
        } else {
            return false;
        }
    }
}
