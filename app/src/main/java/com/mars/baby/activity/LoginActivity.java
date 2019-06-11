package com.mars.baby.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;

import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.model.UserModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.view.MyTextWatcher;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.RxPermissionsTool;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxToast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActivityBase {


    @BindView(R.id.login_code_il)
    TextInputLayout loginCodeIl;
    @BindView(R.id.login_password_il)
    TextInputLayout loginPasswordIl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StorageConstants.setUser(null);
        RxPermissionsTool.with(this).addPermission(Manifest.permission.ACCESS_FINE_LOCATION).initPermission();
        loginCodeIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    loginCodeIl.setError("");
                    loginCodeIl.setErrorEnabled(false);
                }
            }
        });
        loginPasswordIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    loginPasswordIl.setError("");
                    loginPasswordIl.setErrorEnabled(false);
                }

            }
        });

    }


    @OnClick({R.id.login_forget, R.id.login_submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.login_submit_bt:
                String code = loginCodeIl.getEditText().getText().toString();
                String password = loginPasswordIl.getEditText().getText().toString();
                if (StringUtils.isEmpty(code)) {
                    showError(loginCodeIl, "账号不能为空!");
                } else if (StringUtils.isEmpty(password)) {
                    showError(loginPasswordIl, "密码不能为空");
                } else {
                    RequestParams params = new RequestParams(MyApplication.BASE_URL + "Login");
                    params.addQueryStringParameter("phone", code);
                    params.addQueryStringParameter("password", password);
                    x.http().post(params, new CustomCallback<UserModel>() {
                        @Override
                        public void doSuccess(UserModel simpleModel) {
                            if (simpleModel.getDataCode() == 100) {
                                StorageConstants.setUser(simpleModel);
                                RxToast.success("登录成功");
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                RxToast.error(simpleModel.getMessage());
                            }


                        }

                        @Override
                        public void doError(int code, String msg) {

                        }
                    });

                }
                break;
        }
    }

    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}

