package com.yuyh.sprintnba.utils;

import android.graphics.Color;
import android.net.Uri;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yuyh.sprintnba.app.Constant;

public class URLSpan extends ClickableSpan implements ParcelableSpan {

    private final String mURL;
    private String mColor = Constant.SPAN_LINK_COLOR;

    public URLSpan(String url) {
        this.mURL = url;
    }

    public URLSpan(Parcel src) {
        this.mURL = src.readString();
    }

    public URLSpan(String url, String color) {
        this.mURL = url;
        if (!TextUtils.isEmpty(color)) {
            this.mColor = color;
        }
    }

    @Override
    public int getSpanTypeId() {
        return 11;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mURL);
    }

    public String getURL() {
        return mURL;
    }

    public void onClick(View widget) {
        if (!TextUtils.isEmpty(getURL())) {
            Uri uri = Uri.parse(getURL());
            if (uri.getScheme().startsWith(TimeLineUtility.LinkPatterns.WEB_COMPARE_HTTP) || uri.getScheme().startsWith(TimeLineUtility.LinkPatterns.WEB_COMPARE_HTTPS)) {
                // TODO: 跳转链接页面
            } else if (uri.getScheme().startsWith(TimeLineUtility.LinkPatterns.TOPIC_COMPARE_SCHEME)) {
                String topic = getURL();
                topic = topic.substring(TimeLineUtility.LinkPatterns.TOPIC_SCHEME.length(), topic.length());
                // TODO: 跳转#字话题页面
            } else if (uri.getScheme().startsWith(TimeLineUtility.LinkPatterns.MENTION_COMPARE_SCHEME)) {
                // TODO: 跳转@人页面
            }
        }
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(Color.parseColor(mColor));
    }
}