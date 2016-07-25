package com.yuyh.sprintnba.http.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.yuyh.sprintnba.http.bean.match.MatchStat.MatchStatInfo.StatsBean.MaxPlayers.MatchPlayerInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 处理MatchPlayerInfo字段本该返回对象，却返回空字符串引起的问题（腾讯NBA的Json太多坑了）
 *
 * @author yuyh.
 * @date 2016/7/15.
 */
public class MatchPlayerInfoDefaultAdapter implements JsonSerializer<MatchPlayerInfo>, JsonDeserializer<MatchPlayerInfo> {
    @Override
    public MatchPlayerInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsString().equals("")) {
                return null;
                //return new MatchPlayerInfo();
            }
        } catch (Exception ignore) {
        }
        try {
            return new Gson().fromJson(json, MatchPlayerInfo.class);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(MatchPlayerInfo src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        Class<?> clz = src.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                jsonObject.addProperty(field.getName(), (String) field.get(src));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
}
