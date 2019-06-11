package com.mars.baby.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.ForumModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class ForumAdapter extends BaseQuickAdapter<ForumModel.DataBean, BaseViewHolder> {
    private boolean isHot;
    private Activity activity;

    public ForumAdapter(Activity activity,boolean isHot, int layoutResId, @Nullable List<ForumModel.DataBean> data) {
        super(layoutResId, data);
        this.isHot = isHot;
        this.activity=activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ForumModel.DataBean item) {
        if (item.getFrelay() == 1) {
            helper.setGone(R.id.forum_zf, true);
            helper.setText(R.id.zf_forum_renzhen, item.getFreUser().getUtype() == 0 ? "普通用户" : "早教师");
            helper.setText(R.id.zf_forum_name, item.getFreUser().getUnickname());
        } else {
            helper.setGone(R.id.forum_zf, false);
        }

        helper.setText(R.id.forum_content, item.getFcontent());
        GlideUtils.loadImageView(item.getFpic(), (ImageView) helper.getView(R.id.forum_pic));
        helper.setText(R.id.forum_renzhen, item.getFuser().getUtype() == 0 ? "普通用户" : "早教师");
        helper.setText(R.id.forum_name, item.getFuser().getUnickname());
        if (item.getFuser().getUid() == StorageConstants.getUser().getData().getUid()) {
            helper.setGone(R.id.forumn_guanzhu, false);
        } else {
            helper.setGone(R.id.forumn_guanzhu, isHot);

        }

        GlideUtils.loadImageView(item.getFuser().getUpic(), (ImageView) helper.getView(R.id.u_pic));
        helper.setOnClickListener(R.id.forumn_guanzhu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isAttent()) {
                    RxToast.info("已关注");
                } else {
                    RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "AddAttent");
                    requestParams.addQueryStringParameter("fuid", StorageConstants.getUser().getData().getUid() + "");
                    requestParams.addQueryStringParameter("tuid", item.getFuser().getUid() + "");
                    x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                        @Override
                        public void doSuccess(SimpleModel simpleModel) {
                            if (simpleModel.getDataCode() == 100) {
                                item.setAttent(true);
                                RxToast.success("关注成功");

                            } else if (simpleModel.getDataCode() == 102) {
                                RxToast.info("已经关注过了");
                            } else {
                                RxToast.error("关注失败");
                            }
                        }

                        @Override
                        public void doError(int code, String msg) {

                        }
                    });

                }
            }
        });
        helper.setOnClickListener(R.id.forum_pinglun, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(activity);
                rxDialogEditSureCancel.setTitle("请输入评论");
                rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogEditSureCancel.cancel();
                    }
                });
                rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = rxDialogEditSureCancel.getEditText().getText().toString();
                        if (StringUtils.isEmpty(content)) {
                            RxToast.error("请输入内容");
                        } else {
                            RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "AddComment");
                            requestParams.addQueryStringParameter("content", content);
                            requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
                            requestParams.addQueryStringParameter("fid", item.getFid() + "");
                            x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                                @Override
                                public void doSuccess(SimpleModel simpleModel) {
                                    rxDialogEditSureCancel.cancel();
                                    if (simpleModel.getDataCode() == 100) {
                                        RxToast.success("评论成功");
                                    } else {
                                        RxToast.error("评论失败");
                                    }
                                }

                                @Override
                                public void doError(int code, String msg) {
                                    rxDialogEditSureCancel.cancel();
                                }
                            });
                        }


                    }
                });
                rxDialogEditSureCancel.show();
            }
        });

        helper.setOnClickListener(R.id.forum_zhuanfa, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "RelayVisit");
                requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
                requestParams.addQueryStringParameter("fid", item.getFid() + "");
                x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                    @Override
                    public void doSuccess(SimpleModel simpleModel) {
                        if (simpleModel.getDataCode() == 100) {
                            RxToast.success("转发成功");
                        } else {
                            RxToast.error("转发失败");
                        }
                    }

                    @Override
                    public void doError(int code, String msg) {

                    }
                });


            }
        });

    }
}
