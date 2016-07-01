package com.yuyh.cavaliers.http.utils;

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
                        String urlbase = parser.nextText();
                        if (urlbase.contains("vlive.qqvideo.tc.qq.com")) {
                            real.url = urlbase;
                            LogUtils.i("url = " + real.url);
                        }
                    } else if (parser.getName().equals("fvkey")) {
                        String vkey = parser.nextText();
                        LogUtils.i("vkey = " + vkey);
                        real.fvkey = vkey;
                    } else if (parser.getName().equals("vid")) {
                        String vid = parser.nextText();
                        LogUtils.i("vid = " + vid);
                        real.vid = vid;
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
