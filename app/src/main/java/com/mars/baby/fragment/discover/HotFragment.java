package com.mars.baby.fragment.discover;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.activity.ForumDetailActivity;
import com.mars.baby.adapter.ForumAdapter;
import com.mars.baby.adapter.RecordAdapter;
import com.mars.baby.model.ForumModel;
import com.mars.baby.utils.CustomCallback;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {


    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    Unbinder unbinder;
    private ForumAdapter forumAdapter;

    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        mainRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        forumAdapter=new ForumAdapter(getActivity(),true,R.layout.item_forum,null);
        mainRv.setAdapter(forumAdapter);
        EmptyData();
        mainSw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    mainSw.setRefreshing(false);
                    getData();

            }
        });
        forumAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getActivity(), ForumDetailActivity.class);
                intent.putExtra("data",new Gson().toJson(adapter.getData().get(position)));
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        RequestParams requestParams=new RequestParams(MyApplication.BASE_URL+"getHotForum");
        requestParams.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid()+"");
        x.http().get(requestParams, new CustomCallback<ForumModel>() {
            @Override
            public void doSuccess(ForumModel forumModel) {
                mainSw.setRefreshing(false);
                if (forumModel.getData().size()>0){
                    forumAdapter.setNewData(forumModel.getData());
                }else {
                    forumAdapter.setNewData(null);
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
        forumAdapter .setEmptyView(notDataView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
