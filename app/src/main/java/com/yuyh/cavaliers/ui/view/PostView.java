package com.yuyh.cavaliers.ui.view;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public interface PostView {

    void postSuccess();

    void postFailure(String msg);

    void checkPermissionSuccess(boolean hasPermission, String msg, boolean retry);
}
