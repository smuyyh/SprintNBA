package com.yuyh.library.utils.io;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.yuyh.library.utils.log.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class StorageUtils {
    private static final String TAG = StorageUtils.class.getSimpleName();

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取Android数据目录
     *
     * @return
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();

    }

    /**
     * 获取SD卡根目录
     *
     * @return
     */
    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 通过CMD获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        String sdcard = null;
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                LogUtils.i(TAG, "proc/mounts:   " + lineStr);
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        sdcard = strArray[1].replace("/.android_secure", "");
                        LogUtils.i(TAG, "find sd card path:   " + sdcard);
                        return sdcard;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    LogUtils.e(TAG, cmd + " 命令执行失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sdcard = Environment.getExternalStorageDirectory().getPath();
        LogUtils.i(TAG, "not find sd card path return default:   " + sdcard);
        return sdcard;
    }

    /**
     * 获取SD卡目录
     *
     * @return
     */
    public static ArrayList<String> getSDCardPathEx() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                LogUtils.i(TAG, "mount:  " + line);
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add("*" + columns[1]);
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        list.add(columns[1]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取SD卡可用内存大小
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableSize(String path) {
        try {
            File base = new File(path);
            StatFs stat = new StatFs(base.getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取SD卡信息
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sd.isExist = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());

                sd.totalBlocks = sf.getBlockCountLong();
                sd.blockByteSize = sf.getBlockSizeLong();

                sd.availableBlocks = sf.getAvailableBlocksLong();
                sd.availableBytes = sf.getAvailableBytes();

                sd.freeBlocks = sf.getFreeBlocksLong();
                sd.freeBytes = sf.getFreeBytes();

                sd.totalBytes = sf.getTotalBytes();
            }
        }
        LogUtils.i(TAG, sd.toString());
        return sd;
    }

    public static class SDCardInfo {
        public boolean isExist;
        public long totalBlocks;
        public long freeBlocks;
        public long availableBlocks;

        public long blockByteSize;

        public long totalBytes;
        public long freeBytes;
        public long availableBytes;

        @Override
        public String toString() {
            return "SDCardInfo{" +
                    "isExist=" + isExist +
                    ", totalBlocks=" + totalBlocks +
                    ", freeBlocks=" + freeBlocks +
                    ", availableBlocks=" + availableBlocks +
                    ", blockByteSize=" + blockByteSize +
                    ", totalBytes=" + totalBytes +
                    ", freeBytes=" + freeBytes +
                    ", availableBytes=" + availableBytes +
                    '}';
        }
    }
}
