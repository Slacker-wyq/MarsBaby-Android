package com.mars.baby.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.model.UserModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.vondear.rxtools.RxPermissionsTool;
import com.vondear.rxtools.RxPhotoTool;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class MineDetailActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.mine_phone)
    TextView minePhone;
    @BindView(R.id.mine_name)
    TextView mineName;
    private String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_detail);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(mContext);
        GlideUtils.loadImageView(StorageConstants.getUser().getData().getUpic(), pic);
        mineName.setText(StorageConstants.getUser().getData().getUnickname());
        minePhone.setText(StorageConstants.getUser().getData().getUphone());
    }


    @OnClick({R.id.pic, R.id.ming_name_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic:
                RxPermissionsTool.with(this).addPermission(Manifest.permission.CAMERA).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).initPermission();
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mContext, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.ming_name_ll:
                final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(MineDetailActivity.this);
                rxDialogEditSureCancel.getEditText().setText(StorageConstants.getUser().getData().getUnickname());
                rxDialogEditSureCancel.setTitle("请输入昵称");
                rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                        final String name = rxDialogEditSureCancel.getEditText().getText().toString();
                        if (StorageConstants.getUser().getData().getUnickname().equals(name)) {
                            RxToast.error("请修改！");
                        } else {
                            RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "UpdateUser");
                            requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
                            requestParams.addQueryStringParameter("name", name);
                            x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                                @Override
                                public void doSuccess(SimpleModel simpleModel) {
                                    if (simpleModel.getDataCode() == 100) {
                                        UserModel userModel=StorageConstants.getUser();
                                        userModel.getData().setUnickname(name);
                                        StorageConstants.setUser(userModel);
                                        mineName.setText(name);
                                        RxToast.success("修改成功");
                                    }else {
                                        RxToast.error("修改失败");
                                    }
                                }

                                @Override
                                public void doError(int code, String msg) {

                                }
                            });

                        }


                    }
                });
                rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                    }
                });
                rxDialogEditSureCancel.show();
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
                  final   BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            imageUrl = bmobFile.getFileUrl();
                            Glide.with(mContext).
                                    load(imageUrl).
                                    diskCacheStrategy(DiskCacheStrategy.RESULT).
                                    placeholder(R.drawable.ic_empty_zhihu).
                                    into(pic);
                            RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "UpdateUser");
                            requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
                            requestParams.addQueryStringParameter("pic", imageUrl);
                            x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                                @Override
                                public void doSuccess(SimpleModel simpleModel) {
                                    if (simpleModel.getDataCode() == 100) {
                                        UserModel userModel=StorageConstants.getUser();
                                        userModel.getData().setUpic(imageUrl);
                                        StorageConstants.setUser(userModel);
                                        RxToast.success("修改成功");
                                    }else {
                                        RxToast.error("修改失败");
                                    }
                                }

                                @Override
                                public void doError(int code, String msg) {

                                }
                            });


                        } else {
                            RxToast.error("上传文件失败：" + e.getMessage());
                        }

                    }

                    @Override
                    public void onProgress(Integer value) {
                        // 返回的上传进度（百分比）
                    }
                });
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
