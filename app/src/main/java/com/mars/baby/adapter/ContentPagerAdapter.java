package com.mars.baby.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> tabFragments;
    private List<String> tabIndicators;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> tabFragments, List<String> tabIndicators) {
        super(fm);
        this.tabFragments = tabFragments;
        this.tabIndicators = tabIndicators;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position);
    }

    @Override
    public int getCount() {
        return tabIndicators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabIndicators.get(position);
    }
}
