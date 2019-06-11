package com.mars.baby.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.vondear.rxtools.RxPermissionsTool;
import com.vondear.rxtools.RxPhotoTool;
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
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class AddForumActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.add_content)
    EditText addContent;
    @BindView(R.id.register_submit_bt)
    AppCompatButton registerSubmitBt;
    private String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
    }

    @OnClick({R.id.pic, R.id.register_submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pic:
                RxPermissionsTool.with(this).addPermission(Manifest.permission.CAMERA).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).initPermission();
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mContext, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.register_submit_bt:
                String content=addContent.getText().toString();
                RequestParams params = new RequestParams(MyApplication.BASE_URL + "AddForum");
                params.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid()+ "");
                params.addQueryStringParameter("content", content);
                params.addQueryStringParameter("pic", imageUrl);
                x.http().post(params, new CustomCallback<SimpleModel>() {
                    @Override
                    public void doSuccess(SimpleModel simpleModel) {
                        if (simpleModel.getDataCode() == 100) {
                            RxToast.success("添加成功");
                            finish();
                        } else {
                            RxToast.error(simpleModel.getMessage());
                        }
                    }

                    @Override
                    public void doError(int code, String msg) {

                    }
                });
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
                            LogUtil.e(e.getErrorCode() + ":" + e.getLocalizedMessage());
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
