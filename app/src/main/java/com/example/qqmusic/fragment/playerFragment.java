package com.example.qqmusic.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.qqmusic.R;
import com.example.qqmusic.adapter.SingerAdapter;
import com.example.qqmusic.playerfragment.singerActivity;

public class playerFragment extends Fragment {
    private Handler handler;
    private ViewPager tvPager_1;
    private ImageView local_music;
    private ImageButton bn_singer;
    private ImageButton bn_range;
    private ImageButton bn_classic;
    private ImageButton bn_radio;
    private ImageButton bn_liver;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public playerFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static playerFragment newInstance(String param1, String param2) {
        playerFragment fragment = new playerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.page02, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        tvPager_1 = (ViewPager) view.findViewById(R.id.tvPager_1);
        tvPager_1.setOffscreenPageLimit(1);
        //获取控件id
        local_music = view.findViewById(R.id.local_music_bottom_iv_icon);
        bn_singer=view.findViewById(R.id.bn_singer);
        bn_liver=view.findViewById(R.id.bn_liver);
        bn_radio=view.findViewById(R.id.bn_radio);
        bn_range=view.findViewById(R.id.bn_range);
        bn_classic=view.findViewById(R.id.bn_classic);

//自动显示三个推荐
        SingerAdapter myPagerAdapter = new SingerAdapter(getChildFragmentManager(), 1);
        tvPager_1.setAdapter(myPagerAdapter);
        handler = new Handler();
        handler.postDelayed(new TimerRunnable(),5000);


        bn_singer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), singerActivity.class);
                startActivity(intent);
            }
        });
        local_music.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), singerActivity.class);
                startActivity(intent);
            }
        });

    }
    class TimerRunnable implements Runnable{

        @Override
        public void run() {
            int curItem = tvPager_1.getCurrentItem();
            tvPager_1.setCurrentItem(curItem+1);
            if (handler!=null){
                handler.postDelayed(this,5000);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null; //此处在Activity退出时及时 回收
    }




}
