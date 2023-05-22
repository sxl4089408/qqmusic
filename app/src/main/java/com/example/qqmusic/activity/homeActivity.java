package com.example.qqmusic.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.qqmusic.R;
import com.example.qqmusic.adapter.MyFragmentPagerAdapter;
import com.example.qqmusic.fragment.playerFragment;
import com.example.qqmusic.fragment.downFragment;
import com.example.qqmusic.fragment.musicFragment;
import com.example.qqmusic.fragment.settingsFragment;

import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity  implements View.OnClickListener {
    private ImageButton mImgTabmusic;
    private ImageButton mImgTabfrd;
    private ImageButton mImgTabcontact;
    private ImageButton mImgTabsettings;
    private ViewPager myViewPager;
    private List<Fragment> fragmentList;//Fragment列表
    private MyFragmentPagerAdapter fragmentPagerAdapter;//适配器
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        //初始化
        initView();
        initFragment();


    }
    private void initView(){//改变图标颜色
        mImgTabmusic = (ImageButton)findViewById(R.id.id_tab_player_img);
        mImgTabfrd = (ImageButton)findViewById(R.id.id_tab_music_img);
        mImgTabcontact = (ImageButton)findViewById(R.id.id_tab_down_img);
        mImgTabsettings = (ImageButton)findViewById(R.id.id_tab_settings_img);

        mImgTabmusic.setOnClickListener(this);
        mImgTabfrd.setOnClickListener(this);
        mImgTabcontact.setOnClickListener(this);
        mImgTabsettings.setOnClickListener(this);
        myViewPager = (ViewPager) findViewById(R.id.tvPager_1);
        myViewPager.setOffscreenPageLimit(4);
    }

    private void initFragment(){
        playerFragment mTab01 = new playerFragment();
        musicFragment mTab03 = new musicFragment();
        downFragment mTab02 = new downFragment();
        settingsFragment mTab04 = new settingsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        mTab04.setArguments(bundle);
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(mTab01);
        fragmentList.add(mTab02);
        fragmentList.add(mTab03);

        fragmentList.add(mTab04);
        //新建适配器
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        //设置适配器
        myViewPager.setAdapter(fragmentPagerAdapter);

        //设置滑动监听
        myViewPager.addOnPageChangeListener(new MyPageChangeListennr());

        //显示第一个页面
        showFragment(0);
    }
    /**
     * 显示Fragment
     */
    private void showFragment(int num){

        //按索引显示Fragment
        myViewPager.setCurrentItem(num);
        //改变底部标签
        if (num == 0){
            mImgTabmusic.setImageResource(R.drawable.tab_music_pressed1);
            mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
            mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
            mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
        }else if (num == 1){
            mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
            mImgTabfrd.setImageResource(R.drawable.tab_find_frd_pressed1);
            mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
            mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
        }else if (num == 2){
            mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
            mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
            mImgTabcontact.setImageResource(R.drawable.tab_address_pressed1);
            mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
        }else if(num==3){
            mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
            mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
            mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
            mImgTabsettings.setImageResource(R.drawable.tab_settings_pressed1);
        }
    }
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.id_tab_player_img){
            //第一个标签被点击
            showFragment(0);
        }else if (view.getId() == R.id.id_tab_music_img){
            //第二个标签被点击
            showFragment(1);
        }else if (view.getId() == R.id.id_tab_down_img){
            //第二个标签被点击
            showFragment(2);
        }else if(view.getId() == R.id.id_tab_settings_img){
            showFragment(3);
        }
    }




    public void resetimg(){
        //图片变灰
        mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
        mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
        mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
        mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
    }
    public class MyPageChangeListennr implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        //页面选中时调用
        @Override
        public void onPageSelected(int position) {

            //改变底部标签
            if (position == 0){

                mImgTabmusic.setImageResource(R.drawable.tab_music_pressed1);
                mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
                mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
                mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
            }else if (position == 1){
                mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
                mImgTabfrd.setImageResource(R.drawable.tab_find_frd_pressed1);
                mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
                mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
            }else if (position == 2){
                mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
                mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
                mImgTabcontact.setImageResource(R.drawable.tab_address_pressed1);
                mImgTabsettings.setImageResource(R.drawable.tab_settings_normal);
            }else if(position == 3){
                mImgTabmusic.setImageResource(R.drawable.tab_music_normal);
                mImgTabfrd.setImageResource(R.drawable.tab_find_frd_normal);
                mImgTabcontact.setImageResource(R.drawable.tab_address_normal);
                mImgTabsettings.setImageResource(R.drawable.tab_settings_pressed1);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
