package com.mars.baby.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.model.OrderModel;
import com.mars.baby.model.SimpleModel;
import com.mars.baby.utils.CustomCallback;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;
import com.vondear.rxtools.view.RxToast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    private OrderAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        initData();
    }

    private void initData() {
        mainRv.setLayoutManager(new LinearLayoutManager(mContext));
        orderAdapter=new OrderAdapter(R.layout.item_order,null);
        mainRv.setAdapter(orderAdapter);
        EmptyData();
        mainSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainSw.setRefreshing(false);
                getData();

            }
        });
        getData();
    }

    private void getData() {
        mainSw.setRefreshing(true);
        RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"getOrders");
        requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid()+"");
        x.http().get(requestParams, new CustomCallback<OrderModel>() {
            @Override
            public void doSuccess(OrderModel orderModel) {
                mainSw.setRefreshing(false);
                if (orderModel.getData().size()>0){
                    orderAdapter.setNewData(orderModel.getData());
                }else {
                    orderAdapter.setNewData(null);
                    EmptyData();
                }
            }

            @Override
            public void doError(int code, String msg) {
                mainSw.setRefreshing(false);
            }
        });
    }

    private void EmptyData() {
        View notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) mainRv.getParent(), false);
        orderAdapter .setEmptyView(notDataView);
    }


    private class OrderAdapter extends BaseQuickAdapter<OrderModel.DataBean, BaseViewHolder> {
        public OrderAdapter(int layoutResId, @Nullable List<OrderModel.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final OrderModel.DataBean item) {
            helper.setText(R.id.o_name, item.getOuser().getUnickname());
            helper.setText(R.id.o_type,item.getOtype()+"-"+item.getOchildtype());
            helper.setText(R.id.o_time,item.getOtime());
            helper.setText(R.id.o_address,item.getOaddress());
            helper.setOnClickListener(R.id.o_get, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"Receipt");
                    requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid()+"");
                    requestParams.addQueryStringParameter("oid",item.getOid()+"");
                    x.http().post(requestParams, new CustomCallback<SimpleModel>() {
                        @Override
                        public void doSuccess(SimpleModel simpleModel) {
                            if (simpleModel.getDataCode()==100){
                                OrderActivity.this.getData();
                                RxToast.success("接单成功");

                            }else {
                                RxToast.error("接单失败");
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
