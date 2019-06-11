package com.mars.baby.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.CommentModel;
import com.mars.baby.model.ForumModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumDetailActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.forum_content)
    TextView forumContent;
    @BindView(R.id.forum_pic)
    ImageView forumPic;
    @BindView(R.id.forum_name)
    TextView forumName;
    @BindView(R.id.forum_renzhen)
    TextView forumRenzhen;
    @BindView(R.id.forumn_guanzhu)
    TextView forumnGuanzhu;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;

    private ForumModel.DataBean item;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);
        ButterKnife.bind(this);
        item = new Gson().fromJson(getIntent().getStringExtra("data"), ForumModel.DataBean.class);
        rxTitle.setLeftFinish(this);
        rxTitle.setRightText("评论");
        rxTitle.setRightTextSize(25);
        rxTitle.setRightTextVisibility(true);
        rxTitle.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(mContext);
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
                                        getComment();
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
        forumContent.setText(item.getFcontent());
        GlideUtils.loadImageView(item.getFpic(), forumPic);
        forumName.setText(item.getFuser().getUnickname());
        forumRenzhen.setText(item.getFuser().getUtype() == 0 ? "普通用户" : "早教师");
        forumnGuanzhu.setOnClickListener(new View.OnClickListener() {
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
        commentAdapter = new CommentAdapter(R.layout.item_comment, null);
        mainRv.setLayoutManager(new LinearLayoutManager(mContext));
        mainRv.setAdapter(commentAdapter);

        getComment();
        AddVisit();
    }

    private void getComment() {
        RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "getComment");
        requestParams.addQueryStringParameter("fid", item.getFid() + "");
        x.http().get(requestParams, new CustomCallback<CommentModel>() {
            @Override
            public void doSuccess(CommentModel commentModel) {
                if (commentModel.getData().size() > 0) {
                    commentAdapter.setNewData(commentModel.getData());
                } else {
                    EmptyData();
                }
            }

            @Override
            public void doError(int code, String msg) {

            }
        });

    }

    private void AddVisit() {
        RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "AddVisit");
        requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
        requestParams.addQueryStringParameter("fid",item.getFid() + "");
        x.http().post(requestParams, new CustomCallback<SimpleModel>() {
            @Override
            public void doSuccess(SimpleModel simpleModel) {

            }

            @Override
            public void doError(int code, String msg) {

            }
        });
    }

    private void EmptyData() {
        View notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) mainRv.getParent(), false);
        commentAdapter.setEmptyView(notDataView);
    }

    private class CommentAdapter extends BaseQuickAdapter<CommentModel.DataBean, BaseViewHolder> {

        public CommentAdapter(int layoutResId, @Nullable List<CommentModel.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CommentModel.DataBean item) {
            GlideUtils.loadImageView(item.getTuserDO().getUpic(), (ImageView) helper.getView(R.id.u_pic));
            helper.setText(R.id.u_name, item.getTuserDO().getUnickname());
            helper.setText(R.id.u_content, item.getCcontent());
        }
    }
}
