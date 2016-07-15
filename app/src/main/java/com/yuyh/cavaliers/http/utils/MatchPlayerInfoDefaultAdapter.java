package com.yuyh.cavaliers.http.utils;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @author yuyh.
 * @date 2016/7/15.
 */
public class MatchPlayerInfoDefaultAdapter implements JsonSerializer<JsonObject>, JsonDeserializer<JsonObject> {
    @Override
    public JsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsString().equals("")) {
                Log.e("TAG","-----:"+json);
                return null;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsJsonObject();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(JsonObject src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
