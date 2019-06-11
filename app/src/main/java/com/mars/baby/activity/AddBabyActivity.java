package com.mars.baby.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

public class AddBabyActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.add_name)
    EditText addName;
    @BindView(R.id.add_nickname)
    EditText addNickname;
    @BindView(R.id.add_date)
    TextView addDate;
    @BindView(R.id.add_height)
    EditText addHeight;
    @BindView(R.id.add_weight)
    EditText addWeight;
    @BindView(R.id.register_submit_bt)
    AppCompatButton registerSubmitBt;
    @BindView(R.id.add_sex)
    Spinner addSex;
    private String imageUrl = "";
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
    }

    private void initWheelYearMonthDayDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                // 绑定监听器(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        String strFormat = "yyyy年MM月dd日";  //格式设定
                        SimpleDateFormat sdf = new SimpleDateFormat(strFormat, Locale.CHINA);
                        addDate.setText(sdf.format(c.getTime())); //设置日期

                        datePickerDialog.cancel();
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker dp = datePickerDialog.getDatePicker();
        dp.setMaxDate(calendar.getTime().getTime());
    }

    @OnClick({R.id.pic, R.id.register_submit_bt, R.id.add_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_date:
                if (datePickerDialog == null) {
                    initWheelYearMonthDayDialog();
                }
                datePickerDialog.show();
                break;
            case R.id.pic:
                RxPermissionsTool.with(this).addPermission(Manifest.permission.CAMERA).addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).initPermission();
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mContext, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.register_submit_bt:
                String name = addName.getText().toString();
                String nick = addNickname.getText().toString();
                String date = addDate.getText().toString();
                String height = addHeight.getText().toString();
                String weight = addWeight.getText().toString();
                String sex = (String) addSex.getSelectedItem();
                RequestParams params = new RequestParams(MyApplication.BASE_URL + "AddBaby");
                params.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
                params.addQueryStringParameter("age", date);
                params.addQueryStringParameter("name", name);
                params.addQueryStringParameter("pic", imageUrl);
                params.addQueryStringParameter("sex", sex);
                params.addQueryStringParameter("nick", nick);
                params.addQueryStringParameter("height", height);
                params.addQueryStringParameter("weight", weight);
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
