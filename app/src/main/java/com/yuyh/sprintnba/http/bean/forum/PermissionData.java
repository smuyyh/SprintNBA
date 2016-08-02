package com.yuyh.sprintnba.http.bean.forum;

import com.yuyh.sprintnba.http.bean.base.BaseError;

import java.io.Serializable;

public class PermissionData implements Serializable {
    public BaseError error;
    public Exam exam;
    public int result;

    public static class Exam implements Serializable {
        public String title;
        public String url;
    }
}
