package com.yuyh.cavaliers.http.utils;

import android.text.TextUtils;
import android.util.Xml;

import com.yuyh.cavaliers.http.bean.news.VideoRealUrl;
import com.yuyh.library.utils.log.LogUtils;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析xml中的视频网址
 *
 * @author yuyh.
 * @date 16/7/1.
 */
public class PullRealUrlParser implements RealUrlParser {


    @Override
    public VideoRealUrl parse(InputStream is) throws Exception {

        VideoRealUrl real = new VideoRealUrl();
        List<String> list = new ArrayList<>();

        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("url")) {
                        if (TextUtils.isEmpty(real.url))
                            LogUtils.i("url = " + (real.url = parser.nextText()));
                        else {
                            String text = parser.nextText();
                            LogUtils.i("urlbk = " + text);
                            list.add(text);
                        }
                    }

                    break;

                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }
        return real;
    }
}
