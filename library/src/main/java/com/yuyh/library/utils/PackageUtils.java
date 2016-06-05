package com.yuyh.library.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.yuyh.library.AppUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class PackageUtils {

    /**
     * 检查当前应用是否有某项权限
     *
     * @param permName 权限名称
     * @return
     */
    public static boolean checkPermission(String permName) {
        return checkPermission(permName, AppUtils.getAppContext().getPackageName());
    }

    /**
     * 检查包名所在的程序是否有某项权限
     *
     * @param permName 权限名称
     * @param pkgName  程序所在的包名
     * @return
     */
    public static boolean checkPermission(String permName, String pkgName) {
        PackageManager pm = AppUtils.getAppContext().getPackageManager();
        try {
            boolean isHave = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permName, pkgName));
            return isHave;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取App包 信息版本号
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        return pi == null ? -1 : pi.versionCode;
    }

    public static String getAppVersionName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        return pi == null ? "" : pi.versionName;
    }


    /**
     * 调用系统安装应用
     */
    public static boolean install(Context context, File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }

    /**
     * 调用系统卸载应用
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 打开已安装应用的详情
     */
    public static void goToInstalledAppDetails(Context context, String packageName) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((sdkVersion == Build.VERSION_CODES.FROYO ? "pkg"
                    : "com.android.settings.ApplicationPkgName"), packageName);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 调用系统分享
     */
    public static void shareToOtherApp(Context context, String title, String content, String dialogTitle) {
        Intent intentItem = new Intent(Intent.ACTION_SEND);
        intentItem.setType("text/plain");
        intentItem.putExtra(Intent.EXTRA_SUBJECT, title);
        intentItem.putExtra(Intent.EXTRA_TEXT, content);
        intentItem.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intentItem, dialogTitle));
    }

    /**
     * 判断是否前台运行
     * 须配置android.permission.GET_TASKS权限
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName componentName = taskList.get(0).topActivity;
            if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是系统应用
     *
     * @param context
     * @return
     */
    public static boolean isSystemApplication(Context context) {
        if (context == null) {
            return false;
        }
        return isSystemApplication(context, context.getPackageName());
    }

    /**
     * 判断是否是系统应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSystemApplication(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null || packageName == null || packageName.length() == 0) {
            return false;
        }
        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取指定程序应用信息
     *
     * @param context
     * @param pkg
     * @return
     */
    public static ApplicationInfo getApplicationInfo(Context context, String pkg) {
        try {
            return context.getPackageManager().getApplicationInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定程序包信息
     *
     * @param context
     * @param pkg
     * @return
     */
    public static PackageInfo getPackageInfo(Context context, String pkg) {
        try {
            return context.getPackageManager().getPackageInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取已安装的全部应用信息
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getInsatalledPackages(Context context) {
        return context.getPackageManager().getInstalledPackages(0);
    }

    /**
     * 检测应用是否安装
     *
     * @param context
     * @param pkg
     * @return
     */
    public static boolean isInsatalled(Context context, String pkg) {
        if (!CheckUtils.isEmpty(pkg)) {
            List<PackageInfo> list = getInsatalledPackages(context);
            if (!CheckUtils.isEmpty(list)) {
                for (PackageInfo pi : list) {
                    if (pkg.equalsIgnoreCase(pi.packageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 通过包名启动某个应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean startAppByPackageName(Context context, String packageName) {
        return startAppByPackageName(context, packageName, null);
    }

    /**
     * 通过包名启动某个应用 带参数
     *
     * @param context
     * @param packageName
     * @param param
     * @return
     */
    public static boolean startAppByPackageName(Context context, String packageName, Map<String, String> param) {
        android.content.pm.PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                resolveIntent.setPackage(pi.packageName);
            }

            List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String packageName1 = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packageName1, className);

                intent.setComponent(cn);
                if (param != null) {
                    for (Map.Entry<String, String> en : param.entrySet()) {
                        intent.putExtra(en.getKey(), en.getValue());
                    }
                }
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "启动失败",
                    Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
