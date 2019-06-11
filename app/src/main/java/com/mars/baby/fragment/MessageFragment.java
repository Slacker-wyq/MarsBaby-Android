package com.mars.baby.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.activity.MessageActivity;
import com.mars.baby.model.AttentModel;
import com.mars.baby.model.FriendModel;
import com.mars.baby.utils.CustomCallback;
import com.mars.baby.utils.GlideUtils;
import com.vondear.rxtools.view.RxTitle;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {


    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    Unbinder unbinder;
    private AttentAdapter attentAdapter;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
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
                Intent intent=new Intent(getActivity(), MessageActivity.class);
                FriendModel.DataBean dataBean= (FriendModel.DataBean) adapter.getItem(position);
                intent.putExtra("uid",dataBean.getFtuid());
                intent.putExtra("name",dataBean.getTuserDO().getUnickname());
                startActivity(intent);
            }
        });

        getData();
    }

    private void getData() {
        RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"getFriend");
        requestParams.addQueryStringParameter("fuid", StorageConstants.getUser().getData().getUid()+"");
        x.http().get(requestParams, new CustomCallback<FriendModel>() {
            @Override
            public void doSuccess(FriendModel friendModel) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class AttentAdapter extends BaseQuickAdapter<FriendModel.DataBean,BaseViewHolder> {
        public AttentAdapter(int layoutResId, @Nullable List<FriendModel.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, FriendModel.DataBean item) {
            helper.setText(R.id.u_name,item.getTuserDO().getUnickname());
            GlideUtils.loadImageView(item.getTuserDO().getUpic(), (ImageView) helper.getView(R.id.u_pic));
        }
    }
}
