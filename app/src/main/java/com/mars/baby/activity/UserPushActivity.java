package com.mars.baby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.adapter.ForumAdapter;
import com.mars.baby.model.ForumModel;
import com.mars.baby.utils.CustomCallback;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPushActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.main_rv)
    RecyclerView mainRv;
    @BindView(R.id.main_sw)
    SwipeRefreshLayout mainSw;
    private ForumAdapter forumAdapter;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_push);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        uid=getIntent().getIntExtra("uid",0);

        initData();


    }

    private void initData() {
        mainRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        forumAdapter = new ForumAdapter(getActivity(), false, R.layout.item_forum, null);
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
                Intent intent = new Intent(getActivity(), ForumDetailActivity.class);
                intent.putExtra("data", new Gson().toJson(adapter.getData().get(position)));
                startActivity(intent);
            }
        });
        getData();
    }

    private void getData() {
        RequestParams requestParams = new RequestParams(MyApplication.BASE_URL + "getUserForum");
        requestParams.addQueryStringParameter("uid", uid+ "");
        x.http().get(requestParams, new CustomCallback<ForumModel>() {
            @Override
            public void doSuccess(ForumModel forumModel) {
                mainSw.setRefreshing(false);
                if (forumModel.getData().size() > 0) {
                    forumAdapter.setNewData(forumModel.getData());
                } else {
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
        forumAdapter.setEmptyView(notDataView);
    }


    public Activity getActivity() {
        return this;
    }
}
