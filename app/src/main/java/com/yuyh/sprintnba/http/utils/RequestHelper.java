package com.yuyh.sprintnba.http.utils;

import android.content.Context;

import com.yuyh.library.utils.data.safe.MD5;
import com.yuyh.library.utils.log.LogUtils;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.utils.SettingPrefUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public class RequestHelper {


    public Context mContext;

    public RequestHelper(Context mContext) {
        this.mContext = mContext;
    }

    public static HashMap<String, String> getRequsetMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("client", Constant.deviceId);
        map.put("night", "0");
        try {
            map.put("token", URLEncoder.encode(SettingPrefUtils.getToken(), "UTF-8"));
            LogUtils.i("token="+URLEncoder.encode(SettingPrefUtils.getToken(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 虎扑url sign生成
     *
     * @param map url参数，按字典序拼接key和value
     * @return sign值
     */
    public static String getRequestSign(Map<String, String> map) {
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() { // 字典序
            @Override
            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().compareTo(rhs.getKey());
            }
        });
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i = i + 1) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            Map.Entry<String, String> map1 = list.get(i);
            builder.append(map1.getKey()).append("=").append(map1.getValue());
        }
        builder.append("HUPU_SALT_AKJfoiwer394Jeiow4u309");
        return MD5.getMD5(builder.toString());
    }

}
