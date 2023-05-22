package com.example.qqmusic.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.qqmusic.playerfragment.ad_1;
import com.example.qqmusic.playerfragment.ad_2;
import com.example.qqmusic.playerfragment.ad_3;


public class SingerAdapter extends FragmentPagerAdapter {

    //构造方法
    private String[] page = {"four","five","six"};
    public SingerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @NonNull
    @Override
    //显示那个页面
    public Fragment getItem(int position) {
        Fragment fragment = new ad_1();
        position=position%(page.length);
        if (position==0){
            fragment = new ad_1();
        }else if (position==1){
            fragment = new ad_2();
        }else if (position==2){
            fragment = new ad_3();
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
