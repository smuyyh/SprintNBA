package com.yuyh.sprintnba.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.base.BaseSwipeBackCompatActivity;
import com.yuyh.sprintnba.ui.presenter.impl.LoginPresenterImpl;
import com.yuyh.sprintnba.ui.view.LoginView;
import com.yuyh.sprintnba.utils.SettingPrefUtils;
import com.yuyh.library.utils.toast.ToastUtils;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseSwipeBackCompatActivity implements LoginView {

    @InjectView(R.id.etUsername)
    TextInputEditText etUsername;
    @InjectView(R.id.etPassword)
    TextInputEditText etPassword;

    @InjectView(R.id.textInputUserName)
    TextInputLayout textInputUserName;
    @InjectView(R.id.textInputPassword)
    TextInputLayout textInputPassword;

    private LoginPresenterImpl presenter;
    private NormalDialog dialog;

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
        etUsername.setText(SettingPrefUtils.getUsername());
        etPassword.setText(SettingPrefUtils.getPassword());
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        presenter.login(username, password);
    }

    @OnClick(R.id.btnRegist)
    public void register() {
        dialog = new NormalDialog(this).isTitleShow(false)
                .content("暂不支持虎扑账号注册，您可先到虎扑官网注册。期待后续版本更新~")
                .showAnim(new BounceTopEnter()).dismissAnim(new SlideBottomExit())
                .btnNum(1).btnText("确定");
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
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
