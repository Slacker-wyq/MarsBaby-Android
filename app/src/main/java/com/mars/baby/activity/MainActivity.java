package com.mars.baby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.mars.baby.MyApplication;
import com.mars.baby.R;
import com.mars.baby.StorageConstants;
import com.mars.baby.fragment.DiscoverFragment;
import com.mars.baby.fragment.HomeFragment;
import com.mars.baby.fragment.MessageFragment;
import com.mars.baby.fragment.MineFragment;
import com.mars.baby.fragment.TimeFragment;
import com.mars.baby.model.UserModel;
import com.mars.baby.utils.CustomCallback;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxToast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ActivityBase implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.main_navigation)
    BottomNavigationView mainNavigation;

    HomeFragment homeFragment;
    DiscoverFragment discoverFragment;
    TimeFragment timeFragment;
    MessageFragment messageFragment;
    MineFragment mineFragment;

    private FragmentManager manager;
    private Fragment mContent;//记录选中的fragment
    private String[] fragmentTag = {"homeFragment", "discoverFragment", "timeFragment", "messageFragment", "mineFragment"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        discoverFragment = new DiscoverFragment();
        timeFragment = new TimeFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();
        manager = getSupportFragmentManager();
        mainNavigation.setOnNavigationItemSelectedListener(this);
        addFragment();
        switchContent(discoverFragment, timeFragment, messageFragment, mineFragment, homeFragment, 0);
    }

    public void addFragment() {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!homeFragment.isAdded())
            transaction.add(R.id.main_frame_cont, homeFragment, fragmentTag[0]);
        if (!discoverFragment.isAdded())
            transaction.add(R.id.main_frame_cont, discoverFragment, fragmentTag[1]);
        if (!timeFragment.isAdded())
            transaction.add(R.id.main_frame_cont, timeFragment, fragmentTag[2]);
        if (!messageFragment.isAdded())
            transaction.add(R.id.main_frame_cont, messageFragment, fragmentTag[3]);
        if (!mineFragment.isAdded())
            transaction.add(R.id.main_frame_cont, mineFragment, fragmentTag[4]);

        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }
    private void getUser(){
        RequestParams params = new RequestParams(MyApplication.BASE_URL + "getUser");
        params.addQueryStringParameter("uid", StorageConstants.getUser().getData().getUid()+"");
        x.http().post(params, new CustomCallback<UserModel>() {
            @Override
            public void doSuccess(UserModel simpleModel) {
                if (simpleModel.getDataCode() == 100) {
                    StorageConstants.setUser(simpleModel);
                } else {
                    RxToast.error(simpleModel.getMessage());
                }


            }

            @Override
            public void doError(int code, String msg) {

            }
        });

    }

    /**
     * fragment 切换
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment from1, Fragment from2, Fragment from3, Fragment to, int index) {
        if (mContent != to) {
            mContent = to;
            if (mContent == null) {
                mContent = manager.findFragmentByTag(fragmentTag[index]);
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(from).hide(from1).hide(from2).hide(from3).show(mContent).commit(); // 隐藏当前的fragment，显示下一个
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.main_home:
                switchContent(discoverFragment, timeFragment, messageFragment, mineFragment, homeFragment, 0);
                return true;
            case R.id.main_discover:
                switchContent(homeFragment, timeFragment, messageFragment, mineFragment, discoverFragment, 1);
                return true;
            case R.id.main_time:
                switchContent(homeFragment, discoverFragment, messageFragment, mineFragment, timeFragment, 2);
                return true;
            case R.id.main_message:
                switchContent(homeFragment, timeFragment, discoverFragment, mineFragment, messageFragment, 3);
                return true;
            case R.id.main_people:
                switchContent(homeFragment, timeFragment, messageFragment, discoverFragment, mineFragment, 4);
                return true;
            default:
                return false;


        }
    }
}
