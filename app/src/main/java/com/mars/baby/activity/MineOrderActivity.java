package com.mars.baby.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mars.baby.R;
import com.mars.baby.adapter.ContentPagerAdapter;
import com.mars.baby.fragment.discover.FollowFragment;
import com.mars.baby.fragment.discover.HotFragment;
import com.mars.baby.fragment.mineorder.WanChengFragment;
import com.mars.baby.fragment.mineorder.WeiJieFragment;
import com.mars.baby.fragment.mineorder.YiJieFragment;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineOrderActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.tab_viewpager_tab)
    TabLayout tabViewpagerTab;
    @BindView(R.id.tab_viewpager_viewpager)
    ViewPager tabViewpagerViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_order);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        List<String> tabIndicators = new ArrayList<>();
        List<Fragment> tabFragments = new ArrayList<>();
        tabIndicators.add("未接");
        tabIndicators.add("已接");
        tabIndicators.add("完成");
        tabFragments.add(new WeiJieFragment());
        tabFragments.add(new YiJieFragment());
        tabFragments.add(new WanChengFragment());
        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), tabFragments, tabIndicators);
        tabViewpagerViewpager.setAdapter(contentPagerAdapter);
        tabViewpagerTab.setTabMode(TabLayout.MODE_FIXED);
        tabViewpagerTab.setupWithViewPager(tabViewpagerViewpager);
    }
}
