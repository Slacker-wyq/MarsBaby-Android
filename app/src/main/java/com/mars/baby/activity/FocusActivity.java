package com.mars.baby.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.fragment.MessageFragment;
import com.mars.baby.model.AttentModel;
import com.mars.baby.model.FriendModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FocusActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    private AttentAdapter attentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        initData();
    }
    private void initData() {
        attentAdapter=new AttentAdapter(R.layout.item_attent,null);
        mainRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainRv.setAdapter(attentAdapter);
        rxTitle.getLlLeft().setVisibility(View.INVISIBLE);
        mainSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainSw.setRefreshing(false);
                getData();
            }
        });
        attentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(), UserPushActivity.class);
                AttentModel.DataBean dataBean= (AttentModel.DataBean) adapter.getItem(position);
                intent.putExtra("uid",dataBean.getAtuid());
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"getAttent");
        requestParams.addQueryStringParameter("fuid", StorageConstants.getUser().getData().getUid()+"");
        x.http().get(requestParams, new CustomCallback<AttentModel>() {
            @Override
            public void doSuccess(AttentModel friendModel) {
                if (friendModel.getData().size()>0){
                    attentAdapter.setNewData(friendModel.getData());
                }else {
                    attentAdapter.setNewData(null);
                    EmptyData();
                }
            }

            @Override
            public void doError(int code, String msg) {

            }
        });

    }
    private void EmptyData() {
        View notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) mainRv.getParent(), false);
        attentAdapter.setEmptyView(notDataView);
    }

    public Context getActivity() {
        return this;
    }

    private class AttentAdapter extends BaseQuickAdapter<AttentModel.DataBean,BaseViewHolder> {
        public AttentAdapter(int layoutResId, @Nullable List<AttentModel.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper,final AttentModel.DataBean item) {
            helper.setText(R.id.u_name,item.getTuserDO().getUnickname());
            GlideUtils.loadImageView(item.getTuserDO().getUpic(), (ImageView) helper.getView(R.id.u_pic));
            helper.setGone(R.id.u_unfocus,true);
            helper.setOnClickListener(R.id.u_unfocus, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"DeleteAttent");
                    requestParams.addQueryStringParameter("fuid", StorageConstants.getUser().getData().getUid()+"");
                    requestParams.addQueryStringParameter("tuid", item.getAtuid()+"");
                    x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                        @Override
                        public void doSuccess(SimpleModel simpleModel) {
                            if (simpleModel.getDataCode()==100){
                                FocusActivity.this.getData();
                                RxToast.success("取消成功");
                            }else {
                                RxToast.error("失败");
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
}
