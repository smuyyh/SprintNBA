package com.yuyh.sprintnba.http.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.yuyh.sprintnba.http.bean.match.MatchStat.MaxPlayers.MatchPlayerInfo;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 处理MatchStat里面出现key一样，但是value有时为对象，有时为List引起的问题（腾讯NBA的Json太多坑了）
 *
 * @author yuyh.
 * @date 2016/7/15.
 */
public class ListDefaultAdapter implements JsonDeserializer<List<?>> {
    @Override
    public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            Gson newGson = new GsonBuilder().serializeNulls()
                    .registerTypeAdapter(MatchPlayerInfo.class, new MatchPlayerInfoDefaultAdapter())
                    .create();

            String jsonStr = json.toString();
            if (jsonStr.contains("\"playerStats\":{") && jsonStr.contains("\"type\":\"16\"")) {
                // 不太优雅的方式，如果是160，改为160和场上球员
                jsonStr = jsonStr.replace("\"type\":\"16\"", "\"type\":\"160\"")
                                .replace("\"playerStats\":{", "\"groundStats\":{");
            }

            if (json.isJsonArray()) {
                return newGson.fromJson(jsonStr, typeOfT);
            } else if (json.getAsString() != null && !json.getAsString().equals("")) {
                String newjson = "[" + json.getAsString() + "]";
                return newGson.fromJson(newjson, typeOfT);
            }
        } catch (Exception ignore) {
            Log.e("---", ignore.toString());
        }

        return null;
    }
}
