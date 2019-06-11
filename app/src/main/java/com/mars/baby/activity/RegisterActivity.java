package com.mars.baby.activity;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.model.UserModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.view.MyTextWatcher;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.RxConstTool;
import com.vondear.rxtools.RxPermissionsTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.RxRegTool;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class RegisterActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.register_code_il)
    TextInputLayout registerCodeIl;
    @BindView(R.id.register_password_il)
    TextInputLayout registerPasswordIl;
    @BindView(R.id.register_afpassword_il)
    TextInputLayout registerAfpasswordIl;
    @BindView(R.id.forget_msg_il)
    TextInputLayout forgetMsgIl;
    @BindView(R.id.forget_get_msg)
    TextView forgetGetMsg;
    @BindView(R.id.register_submit_bt)
    AppCompatButton registerSubmitBt;
    @BindView(R.id.register_nickname_il)
    TextInputLayout registerNicknameIl;
    @BindView(R.id.pic)
    ImageView pic;
    private String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        rxTitle.setLeftFinish(this);

        rxTitle.setTitle("用户注册");


        registerCodeIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    registerCodeIl.setError("");
                    registerCodeIl.setErrorEnabled(false);
                }

            }
        });
        registerPasswordIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    registerPasswordIl.setError("");
                    registerPasswordIl.setErrorEnabled(false);
                }

            }
        });
        registerAfpasswordIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    registerAfpasswordIl.setError("");
                    registerAfpasswordIl.setErrorEnabled(false);
                }

            }
        });
        forgetMsgIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    forgetMsgIl.setError("");
                    forgetMsgIl.setErrorEnabled(false);
                }

            }
        });
        registerNicknameIl.getEditText().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (s.length() > 0) {
                    registerNicknameIl.setError("");
                    registerNicknameIl.setErrorEnabled(false);
                }

            }
        });

    }


    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }


    @OnClick({R.id.forget_get_msg, R.id.register_submit_bt, R.id.pic,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic:
                RxPermissionsTool.with(this).addPermission(Manifest.permission.CAMERA).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).initPermission();
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mContext, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.forget_get_msg:
                String phone = registerCodeIl.getEditText().getText().toString();
                if (RxRegTool.isMobile(phone)) {
                    BmobSMS.requestSMSCode(phone, "火星宝宝", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                RxToast.success("发送成功,请注意查收");
                                // mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");

                            }else if ( e.getErrorCode()==10010){
                                RxToast.error("手机号发送短信达到限制");
                            }else {
                                RxToast.error("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                            }
                        }
                    });

                } else {
                    RxToast.error("请输入正确手机!");
                }
                break;
            case R.id.register_submit_bt:
                final String username = registerCodeIl.getEditText().getText().toString();
                final String password = registerPasswordIl.getEditText().getText().toString();
                final String afpassword = registerAfpasswordIl.getEditText().getText().toString();
                final String msg = forgetMsgIl.getEditText().getText().toString();
                final String nickName = registerNicknameIl.getEditText().getText().toString();

                if (StringUtils.isEmpty(username)) {
                    showError(registerCodeIl, "账号不能为空!");
                }  else if (StringUtils.isEmpty(password)) {
                    showError(registerPasswordIl, "密码不能为空!");
                } else if (!password.equals(afpassword)) {
                    showError(registerAfpasswordIl, "两次密码不一致!");
                } else if (StringUtils.isEmpty(nickName)) {
                    showError(registerNicknameIl, "昵称不能为空!");
                } else {
                    BmobSMS.verifySmsCode(username, msg, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                RequestParams params = new RequestParams(MyApplication.BASE_URL + "Register");
                                params.addQueryStringParameter("phone", username);
                                params.addQueryStringParameter("password", password);
                                params.addQueryStringParameter("nickname", nickName);
                                params.addQueryStringParameter("pic", imageUrl);
                                x.http().post(params, new CustomCallback<SimpleModel>() {
                                    @Override
                                    public void doSuccess(SimpleModel simpleModel) {
                                        if (simpleModel.getDataCode() == 100) {
                                            RxToast.success("注册成功");
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        } else {
                                            RxToast.error(simpleModel.getMessage());
                                        }


                                    }

                                    @Override
                                    public void doError(int code, String msg) {

                                    }
                                });
                            } else {
                                LogUtil.e(e.getErrorCode() + "-" + e.getMessage() );
                               RxToast.error("验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                            }
                        }
                    });

                }
                break;
        }
    }

    //从Uri中加载图片 并将其转化成File文件返回
    private File roadImageView(Uri uri, ImageView imageView) {
        Glide.with(mContext).
                load(RxPhotoTool.cropImageUri).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                thumbnail(0.5f).
                placeholder(R.drawable.ic_empty_zhihu).
                priority(Priority.LOW).
                into(imageView);
        return (new File(RxPhotoTool.getImageAbsolutePath(this, uri)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    RxPhotoTool.cropImage(this, uri);// 裁剪图片
                }

                break;
            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    /* */
                    RxPhotoTool.cropImage(this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
                }

                break;
            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
                File file = roadImageView(RxPhotoTool.cropImageUri, pic);
                final BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            imageUrl = bmobFile.getFileUrl();
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            RxToast.success("上传文件成功");
                        } else {
                            LogUtil.e(e.getErrorCode()+":"+e.getLocalizedMessage());
                            RxToast.error("上传失败");
                        }
                    }
                });
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
