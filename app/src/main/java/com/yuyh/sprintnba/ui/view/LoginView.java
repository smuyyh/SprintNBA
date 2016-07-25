package com.yuyh.sprintnba.ui.view;

/**
 * @author yuyh.
 * @date 16/6/26.
 */
public interface LoginView {

    void showLoading();

    void hideLoading();

    void showUserNameError(String error);

    void showPassWordError(String error);

    void loginSuccess();
}
