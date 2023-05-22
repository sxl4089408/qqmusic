package com.example.qqmusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.qqmusic.downFragment.Fragment_first;
import com.example.qqmusic.downFragment.Fragment_second;
import com.example.qqmusic.downFragment.Fragment_thrid;


public class ImageAdapter extends FragmentPagerAdapter {

    //构造方法
    private String[] page = {"first","second","thrid"};
    public ImageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @NonNull
    @Override
    //显示那个页面
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment_first();
        position=position%(page.length);
        if (position==0){
            fragment = new Fragment_first();
        }else if (position==1){
            fragment = new Fragment_second();
        }else if (position==2){
            fragment = new Fragment_thrid();
        }

        return fragment;
        //当读取到集合最后一位时又从第一位开始读取。
    }
    @Override
    //页面个数
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
