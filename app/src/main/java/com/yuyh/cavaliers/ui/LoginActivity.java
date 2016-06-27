package com.yuyh.cavaliers.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.base.BaseSwipeBackCompatActivity;
import com.yuyh.cavaliers.http.utils.UserStorage;
import com.yuyh.cavaliers.presenter.impl.LoginPresenterImpl;
import com.yuyh.cavaliers.ui.view.LoginView;
import com.yuyh.library.utils.toast.ToastUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseSwipeBackCompatActivity implements LoginView {

    @InjectView(R.id.etUsername)
    EditText etUsername;
    @InjectView(R.id.etPassword)
    EditText etPassword;

    @InjectView(R.id.textInputUserName)
    TextInputLayout textInputUserName;
    @InjectView(R.id.textInputPassword)
    TextInputLayout textInputPassword;

    @InjectView(R.id.btnLogin)
    Button btnLogin;
    @InjectView(R.id.btnRegist)
    Button btnRegist;

    private LoginPresenterImpl presenter;
    private UserStorage mUserStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("登录");
        etUsername.addTextChangedListener(new MTextWatcher(textInputUserName));
        etPassword.addTextChangedListener(new MTextWatcher(textInputPassword));
        presenter = new LoginPresenterImpl(this, this);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        presenter.login(username, password);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showUserNameError(String error) {
        etUsername.setError(error);
    }

    @Override
    public void showPassWordError(String error) {
        etPassword.setError(error);
    }

    @Override
    public void loginSuccess() {
        ToastUtils.showSingleToast("登录成功");
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    class MTextWatcher implements TextWatcher {

        TextInputLayout textInputLayout;

        public MTextWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            textInputLayout.setErrorEnabled(false);
        }
    }
}
