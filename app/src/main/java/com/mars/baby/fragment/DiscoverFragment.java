package com.mars.baby.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mars.baby.R;
import com.mars.baby.activity.AddForumActivity;
import com.mars.baby.adapter.ContentPagerAdapter;
import com.mars.baby.fragment.discover.FollowFragment;
import com.mars.baby.fragment.discover.HotFragment;
import com.vondear.rxtools.view.RxTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {


    @BindView(R.id.rx_title)
    RxTitle rxTitle;
    @BindView(R.id.tab_viewpager_tab)
    TabLayout tabViewpagerTab;
    @BindView(R.id.tab_viewpager_viewpager)
    ViewPager tabViewpagerViewpager;
    Unbinder unbinder;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        rxTitle.setRightText("文章+");
        rxTitle.setRightTextSize(25);
        rxTitle.setRightTextVisibility(true);

        rxTitle.getLlLeft().setVisibility(View.INVISIBLE);
        List<String> tabIndicators = new ArrayList<>();
        List<Fragment> tabFragments = new ArrayList<>();
        tabIndicators.add("关注");
        tabIndicators.add("热门");
        tabFragments.add(new FollowFragment());
        tabFragments.add(new HotFragment());
        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager(), tabFragments, tabIndicators);
        tabViewpagerViewpager.setAdapter(contentPagerAdapter);
        tabViewpagerTab.setTabMode(TabLayout.MODE_FIXED);
        tabViewpagerTab.setupWithViewPager(tabViewpagerViewpager);
        rxTitle.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddForumActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
