package com.yuyh.sprintnba.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.util.Linkify;

import com.yuyh.sprintnba.app.Constant;

import java.util.regex.Pattern;

/**
 * Created by liuz on 16/6/7.
 */
public class TimeLineUtility {

    private static final String TAG = "TimeLineUtility";

    private static String mColor = Constant.SPAN_LINK_COLOR;

    public static void setSpanColor(String color) {
        mColor = color;
    }

    private TimeLineUtility() {

    }

    public enum TimeLineStatus {
        LINK, FEED
    }

    public static SpannableString convertNormalStringToSpannableString(String txt, TimeLineStatus status) {
        String hackTxt;
        if (txt.startsWith("[") && txt.endsWith("]")) {
            hackTxt = txt + " ";
        } else {
            hackTxt = txt;
        }

        SpannableString value = SpannableString.valueOf(hackTxt);
        switch (status) {
            case LINK: {
                Linkify.addLinks(value, LinkPatterns.WEB_URL, LinkPatterns.WEB_SCHEME);
            }
            break;
            case FEED: {
                Linkify.addLinks(value, LinkPatterns.WEB_URL, LinkPatterns.WEB_SCHEME);
                Linkify.addLinks(value, LinkPatterns.TOPIC_URL, LinkPatterns.TOPIC_SCHEME);
                Linkify.addLinks(value, LinkPatterns.MENTION_URL, LinkPatterns.MENTION_SCHEME);
            }
            break;
        }

        android.text.style.URLSpan[] urlSpans = value.getSpans(0, value.length(), android.text.style.URLSpan.class);
        URLSpan weiboSpan;

        for (android.text.style.URLSpan urlSpan : urlSpans) {
            if (urlSpan.getURL().startsWith(LinkPatterns.TOPIC_SCHEME)) {
                String topic = urlSpan.getURL().substring(LinkPatterns.TOPIC_SCHEME.length(), urlSpan.getURL().length());
                //不识别空格话题和大于30字话题
                String group = topic.substring(1, topic.length() - 1).trim();
                if (1 > group.length() || group.length() > 30) {
                    value.removeSpan(urlSpan);
                    continue;
                }
            }
            weiboSpan = new URLSpan(urlSpan.getURL(), mColor);
            int start = value.getSpanStart(urlSpan);
            int end = value.getSpanEnd(urlSpan);
            value.removeSpan(urlSpan);
            value.setSpan(weiboSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return value;
    }

    public static class LinkPatterns {

        public static final Pattern WEB_URL = Pattern
                .compile("(((http|https)://)|((?<!((http|https)://))www\\.))" + ".*?" + "(?=(&nbsp;|[\\u4e00-\\u9fa5]|\\s|　|<br />|$|[<>]))");
        public static final Pattern TOPIC_URL = Pattern
                .compile("#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#");
        public static final Pattern MENTION_URL = Pattern
                .compile("@[\u4e00-\u9fa5a-zA-Z0-9_-·\\.]+[\u200B]");

        public static final String WEB_SCHEME = "http://";
        public static final String TOPIC_SCHEME = "com.zheblog.weibo.topic://";
        public static final String MENTION_SCHEME = "com.zheblog.weibo.at://";

        public static final String WEB_COMPARE_HTTP = "http";
        public static final String WEB_COMPARE_HTTPS = "https";
        public static final String TOPIC_COMPARE_SCHEME = "com.zheblog.weibo.topic";
        public static final String MENTION_COMPARE_SCHEME = "com.zheblog.weibo.at";

    }

}