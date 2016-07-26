package com.yuyh.library.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import com.yuyh.library.utils.toast.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class StringUtils {

    private static final String PRE_FIX_UTF = "&#x";
    private static final String POS_FIX_UTF = ";";

    private StringUtils() {
        throw new AssertionError();
    }

    public static void copy(Context mContext, String stripped) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("content", stripped);
        clipboard.setPrimaryClip(clip);
        ToastUtils.showToast("复制成功");
    }

    /**
     * 将字符串编码格式转成GB2312
     *
     * @param str
     * @return
     */
    public static String TranEncodeTOGB(String str) {
        try {
            String strEncode = StringUtils.getEncoding(str);
            String temp = new String(str.getBytes(strEncode), "GBK");
            return temp;
        } catch (java.io.IOException ex) {

            return null;
        }
    }

    /**
     * 判断输入字符是否为gb2312的编码格式
     *
     * @param c 输入字符
     * @return 如果是gb2312返回真，否则返回假
     */
    public static boolean isGB2312(char c) {
        Character ch = Character.valueOf(c);
        String sCh = ch.toString();
        try {
            byte[] bb = sCh.getBytes("gb2312");
            if (bb.length > 1) {
                return true;
            }
        } catch (UnsupportedEncodingException ex) {
            return false;
        }
        return false;
    }

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    /**
     * 创建｛文字内容、字体颜色、字体大小｝分段文字集合体
     *
     * @param text
     * @param color
     * @param textSize
     * @return
     */
    public static SpannableStringBuilder creSpanString(String[] text, int[] color, int[] textSize) {
        if (text == null || color == null || textSize == null)
            throw new IllegalArgumentException("参数不能为空");
        if (text.length != color.length || text.length != textSize.length)
            throw new IllegalArgumentException("参数数组长度不一致");
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            for (int i = 0; i < text.length; i++) {
                SpannableString sp = new SpannableString(text[i]);
                sp.setSpan(new ForegroundColorSpan(color[i]), 0, sp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                sp.setSpan(new AbsoluteSizeSpan(textSize[i], true), 0, sp.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                sb.append(sp);
            }
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * 判断字符串是否为空 或者全是空格
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 字符串长度，为空时返回0
     *
     * @param str
     * @return
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * Object 转 String
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length()).append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * String 转 UTF-8编码
     *
     * @param str
     * @return
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * String 转 UTF-8编码 包含默认返回
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * 半角转全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
