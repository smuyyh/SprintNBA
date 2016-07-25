package com.yuyh.sprintnba.http.bean.bmob;

import cn.bmob.v3.BmobObject;

/**
 * @author yuyh.
 * @date 2016/6/29.
 */
public class Feedback extends BmobObject {

    public String message;
    public String title;

    public Feedback() {
        this.setTableName("feedback");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
