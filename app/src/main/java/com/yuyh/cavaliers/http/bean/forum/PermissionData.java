package com.yuyh.cavaliers.http.bean.forum;

import com.yuyh.cavaliers.http.bean.base.BaseError;

public class PermissionData {
  public BaseError error;
  public Exam exam;
  public int result;

  public static class Exam {
    public String title;
    public String url;
  }
}
