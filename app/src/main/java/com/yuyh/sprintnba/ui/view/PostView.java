package com.yuyh.sprintnba.ui.view;

/**
 * @author yuyh.
 * @date 2016/6/27.
 */
public interface PostView {

    void showLoadding();

    void hideLoadding();

    void postSuccess();

    void postFailure(String msg);

    void feedbackSuccess();

    void checkPermissionSuccess(boolean hasPermission, int code, String msg, boolean retry);
}
