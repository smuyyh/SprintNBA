package com.yuyh.cavaliers.http.bean.forum;

import com.yuyh.cavaliers.http.bean.base.BaseError;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public class AddReplyData {
    public int status;
    public AddReplyResult result;
    public BaseError error;

    public static class AddReplyResult{
        int pid;
        String content;
        String viainfo;
        String postdate;
    }
}
