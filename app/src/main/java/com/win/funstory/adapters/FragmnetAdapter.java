package com.win.funstory.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：WangShuang
 * Date: 2015/12/29 15:24
 * email：m15046658245_1@163.com
 */
public class  FragmnetAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragmentList;

    private List<String> list;

    public FragmnetAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        list=new ArrayList<>();
        list.add("专享");
        list.add("视频");
        list.add("纯文");
        list.add("纯图");
        list.add("最新");
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
