package com.yuyh.library.utils.data;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 清除应用数据管理器
 *
 * @author yuyh.
 * @date 16/4/11.
 */
public class DataCleanManager {

    /**
     * 清除本应用所有的数据
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public static void cleanApplicationData(Context context, String... filePath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String fp : filePath) {
            cleanCustomCache(fp);
        }
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     *
     * @param context 上下文
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     *
     * @param context 上下文
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File(context.getFilesDir().getPath()
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     *
     * @param context 上下文
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File(context.getFilesDir().getPath()
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     *
     * @param context 上下文
     * @param dbName  数据库名称
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     *
     * @param context 上下文
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context 上下文
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath 文件路径
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }


    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory 文件夹File对象
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
