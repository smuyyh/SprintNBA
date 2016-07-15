package com.yuyh.sprintnba.http.bean.bmob;

import cn.bmob.v3.BmobObject;

/**
 * @author yuyh.
 * @date 2016/6/29.
 */
public class AppCrashMessage extends BmobObject{

    public String message;

    public AppCrashMessage() {
        this.setTableName("crash");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
