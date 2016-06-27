package com.yuyh.cavaliers.http.bean.base;

import com.google.gson.annotations.SerializedName;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public class BaseError {
    @SerializedName("id")
    int code;
    @SerializedName("text")
    String msg;
}
