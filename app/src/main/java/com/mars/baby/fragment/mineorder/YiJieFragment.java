package com.mars.baby.fragment.mineorder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.adapter.MineOrderAdapter;
import com.mars.baby.model.OrderModel;
import com.mars.baby.utils.CustomCallback;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class YiJieFragment extends Fragment {


    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    Unbinder unbinder;
    private MineOrderAdapter mineOrderAdapter;
    public YiJieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yi_jie, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        mainRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mineOrderAdapter = new MineOrderAdapter(getActivity(), R.layout.item_mine_order, null);
        mainRv.setAdapter(mineOrderAdapter);
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
        RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "getUserOrders");
        requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid() + "");
        requestParams.addQueryStringParameter("status", "1");
        x.http().get(requestParams, new CustomCallback<OrderModel>() {
            @Override
            public void doSuccess(OrderModel orderModel) {
                mainSw.setRefreshing(false);
                if (orderModel.getData().size() > 0) {
                    mineOrderAdapter.setNewData(orderModel.getData());
                } else {
                    mineOrderAdapter.setNewData(null);
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
        mineOrderAdapter.setEmptyView(notDataView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
