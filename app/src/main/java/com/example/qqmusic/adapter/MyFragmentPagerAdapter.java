package com.example.qqmusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Fragment的适配器，提供数据源
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    //定义属性：Fragment列表
    private List<Fragment> fragmentList;
    //构造方法

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    //显示那个页面
    public Fragment getItem(int position) {
        return fragmentList.get(position);

    }

    @Override
    //页面个数
    public int getCount() {
        return fragmentList.size();
    }
}
