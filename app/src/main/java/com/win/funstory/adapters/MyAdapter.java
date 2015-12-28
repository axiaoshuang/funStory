package com.win.funstory.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.win.funstory.TabsFragment;

import java.util.List;

/**
 * author：WangShuang
 * Date: 2015/12/28 19:08
 * email：m15046658245_1@163.com
 */
public class MyAdapter extends FragmentPagerAdapter {

    private List<String> list;

    public MyAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return TabsFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
