package com.example.qqmusic.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;

import com.example.qqmusic.R;
import com.example.qqmusic.adapter.ImageAdapter;
import com.example.qqmusic.downLoad.thirdActivity;
import com.example.qqmusic.playerfragment.singerActivity;

public class  downFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager tvPager;
    private ImageButton downn;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page03, container, false);
    }

    public downFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static downFragment newInstance(String param1, String param2) {
        downFragment fragment = new downFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvPager = (ViewPager) view.findViewById(R.id.tvPager_1);
        downn=(ImageButton) view.findViewById(R.id.downn);
        tvPager.setOffscreenPageLimit(1);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //添加点击事件

        //自动显示三个推荐
        ImageAdapter myPagerAdapter = new ImageAdapter(getChildFragmentManager(), 1);
        tvPager.setAdapter(myPagerAdapter);
       // handler = new Handler();
       // handler.postDelayed(new TimerRunnable(),5000);


        downn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), thirdActivity.class);
                startActivity(intent);
            }
        });



    }


}
