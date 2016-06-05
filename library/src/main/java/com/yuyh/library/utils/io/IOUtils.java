
package com.yuyh.library.utils.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 *
 * @author yuyh.
 * @date 16/4/9.
 */
public class IOUtils {

    /**
     * 关闭io对象
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            }
        }
    }

    /**
     * 输入流保存到文件
     *
     * @param source     输入流来源
     * @param targetPath 目标文件路径
     * @return 文件路径
     */
    public String stream2file(InputStream source, String targetPath) {
        File target = new File(targetPath);
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            if (!target.exists()) {
                String dir = targetPath.substring(0, targetPath.lastIndexOf("/"));
                new File(dir).mkdirs();
                try {
                    target.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            inBuff = new BufferedInputStream(source);
            outBuff = new BufferedOutputStream(new FileOutputStream(target));
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inBuff != null) {
                    inBuff.close();
                }
                if (outBuff != null) {
                    outBuff.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (target.length() > 0) {
            return target.getAbsolutePath();
        } else {
            target.delete();
            return null;
        }
    }

    /**
     * 字节数组转输入流
     *
     * @param data 字节数组
     * @return 输入流
     */
    public InputStream Byte2InputStream(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return bais;
    }

    /**
     * 输入流转字节数组
     *
     * @param is 输入流
     * @return 字节数组
     */
    public byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        try {
            while (is.read(readByte, 0, 1024) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 输入流转字符串
     *
     * @param inputStream inputStream
     * @return 字符串转换之后的
     */
    public static String streamToString(InputStream inputStream) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }

            String result = out.toString();
            out.close();
            inputStream.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 读文件到字节数组
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getFileBytes(File file) throws IOException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int bytes = (int) file.length();
            byte[] buffer = new byte[bytes];
            int readBytes = bis.read(buffer);
            if (readBytes != buffer.length) {
                throw new IOException("Entire file not read");
            }
            return buffer;
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }

}
