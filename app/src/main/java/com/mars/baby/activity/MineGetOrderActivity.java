package com.mars.baby.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mars.baby.R;
import com.mars.baby.adapter.ContentPagerAdapter;
import com.mars.baby.fragment.getorder.FinishFragment;
import com.mars.baby.fragment.getorder.IngFragment;
import com.mars.baby.fragment.mineorder.WanChengFragment;
import com.mars.baby.fragment.mineorder.WeiJieFragment;
import com.mars.baby.fragment.mineorder.YiJieFragment;
import com.vondear.rxtools.activity.ActivityBase;
import com.vondear.rxtools.view.RxTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineGetOrderActivity extends ActivityBase {

    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.tab_viewpager_tab)
    TabLayout tabViewpagerTab;
    @BindView(R.id.tab_viewpager_viewpager)
    ViewPager tabViewpagerViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_get_order);
        ButterKnife.bind(this);
        rxTitle.setLeftFinish(this);
        List<String> tabIndicators = new ArrayList<>();
        List<Fragment> tabFragments = new ArrayList<>();
        tabIndicators.add("进行中");
        tabIndicators.add("已完成");
        tabFragments.add(new IngFragment());
        tabFragments.add(new FinishFragment());

        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), tabFragments, tabIndicators);
        tabViewpagerViewpager.setAdapter(contentPagerAdapter);
        tabViewpagerTab.setTabMode(TabLayout.MODE_FIXED);
        tabViewpagerTab.setupWithViewPager(tabViewpagerViewpager);
    }
}
