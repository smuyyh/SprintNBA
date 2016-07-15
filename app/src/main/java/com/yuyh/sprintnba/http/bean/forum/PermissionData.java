package com.yuyh.sprintnba.http.bean.forum;

import com.yuyh.sprintnba.http.bean.base.BaseError;

public class PermissionData {
  public BaseError error;
  public Exam exam;
  public int result;

  public static class Exam {
    public String title;
    public String url;
  }
}
