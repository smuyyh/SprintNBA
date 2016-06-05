package com.yuyh.library.utils.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式验证
 *
 * @author yuyh.
 * @date 16/4/9.
 */
public class PatternUtils {

    /**
     * 匹配全网IP的正则表达式
     */
    public static final String IP_REGEX = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";

    /**
     * 匹配手机号码的正则表达式
     */
    public static final String PHONE_NUMBER_REGEX = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$";

    /**
     * 匹配邮箱的正则表达式
     */
    public static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    /**
     * 匹配汉子的正则表达式，个数限制为一个或多个
     */
    public static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";

    /**
     * 匹配正整数的正则表达式，个数限制为一个或多个
     */
    public static final String POSITIVE_INTEGER_REGEX = "^\\d+$";

    /**
     * 匹配身份证号的正则表达式
     */
    public static final String ID_CARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

    /**
     * 匹配邮编的正则表达式
     */
    public static final String ZIP_CODE = "^\\d{6}$";

    /**
     * 匹配URL的正则表达式
     */
    public static final String URL = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$";

    /**
     * 匹配生日格式
     */
    public static final String BIRTHDAY = "\\d{4}-\\d{2}-\\d{2}";


    /**
     * 是否符合手机号码格式
     *
     * @param phone
     * @return
     */
    public static boolean isMobilePhoneNum(String phone) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 是否符合邮箱地址格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String strPattern = EMAIL_REGEX;
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 是否符合身份证号码格式
     *
     * @param idCard
     * @return
     */
    public static boolean isIdCard(String idCard) {
        String strPattern = ID_CARD;
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(idCard);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 是否符合yyyy-mm-dd的生日格式
     *
     * @param birthday
     * @return
     */
    public static boolean isBirthday(String birthday) {
        String strPattern = BIRTHDAY;
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(birthday);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 格式化手机号码 134*******7790
     *
     * @param phoneNo
     * @return
     */
    public static String formatPhoneStart3End4(String phoneNo) {
        if (null == phoneNo) return "";

        int length = phoneNo.length();
        StringBuilder builder = new StringBuilder();
        builder.append(phoneNo.substring(0, 3));
        for (int i = 0; i < (length - 7); i++) {
            builder.append("*");
        }
        builder.append(phoneNo.substring(length - 4, length));
        return builder.toString();
    }


    /**
     * 手机号格式化,去" " "-" ",";
     *
     * @param phoneNumber
     * @return
     */
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return "";
        String newString = phoneNumber.replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll(",", "");
        return newString;
    }

    /**
     * 从字符串中获取日期
     *
     * @param content
     * @return
     */
    public static String getDateFromString(String content) {
        try {
            String strPattern = "DB_PoliceOfficeWork_(\\d{4}-\\d{2}-\\d{2}).db";
            Pattern pattern = Pattern.compile(strPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据模式从原文中截取满足条件的一部分字符串
     *
     * @param strPattern
     * @param content
     * @return
     */
    public static String getOneStrFromString(String strPattern, String content) {
        try {
            Pattern pattern = Pattern.compile(strPattern);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                return matcher.group(1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
