package com.yuyh.cavaliers.http.bean.base;

import java.io.Serializable;

/**
 * @author yuyh.
 * @date 16/6/4.
 */
public class Base implements Serializable{

    public int code;
    public String version;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
