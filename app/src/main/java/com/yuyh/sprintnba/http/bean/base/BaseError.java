package com.yuyh.sprintnba.http.bean.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public class BaseError implements Serializable {
    @SerializedName("id")
    public int code;
    @SerializedName("text")
    public String msg;
}
