package com.yuyh.sprintnba.http.utils;

import android.text.TextUtils;
import android.util.Xml;

import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.http.bean.news.VideoRealUrl;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

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
                        if ((urlbase.contains(".tc.qq.com"))
                                && TextUtils.isEmpty(real.url)) {
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
                    } else if (parser.getName().equals("fn")) { // 目前发现直接用{vid}.mp4 有部分不能播放，用fn下的可以
                        String fn = parser.nextText();
                        if (fn.endsWith(".mp4")) {
                            LogUtils.i("fn = " + fn);
                            real.fn = fn;
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
