package com.example.qqmusic.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qqmusic.R;
import com.example.qqmusic.adapter.VerticalAdappter;
import com.example.qqmusic.search.searchActivity;

import java.util.ArrayList;
import java.util.List;

public class musicFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<String> mList = new ArrayList<>();
    private String[] singer;
    private Context context;
    private VerticalAdappter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private SearchView sSearch;
    public musicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment weixinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static musicFragment newInstance(String param1, String param2) {
        musicFragment fragment = new musicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.page01, container, false);

        recyclerView=view.findViewById(R.id.verticalview);
        initData();
        initView();

        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sSearch=view.findViewById(R.id.search_sing);

        //设置歌曲搜索栏
        setSearchViewOnClickListener(sSearch,new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), searchActivity.class);
                startActivity(intent);
            }
        });
    }
    //为searchview每个子控件添加点击事件
    public static void setSearchViewOnClickListener(View v, View.OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView)child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }
    private void initData() {
        mList.add("反语  鹿乃");
                mList.add("All Falls Down    Alan Walker");
                mList.add("death bed   Powfu");
                mList.add("Lemon   米津玄师");
                mList.add("打上花火   米津玄师");
                mList.add("跃进人海拥抱你  张杰");
                mList.add("她和她和她  于真");
                mList.add("画心  张靓颖");
                mList.add("可惜没如果  林俊杰");
                mList.add( "尘埃  林小珂");
                mList.add( "后来遇见他  胡66");
                mList.add("最来了不起的你  段奥娟");
                mList.add("巅峰之上  毛不易");
        mList.add("林俊杰");
        mList.add("许嵩");
        mList.add("张杰");
        mList.add("那英");
        mList.add("华晨宇");
        mList.add("周杰伦");
        mList.add("周深");
        mList.add("Taylor swift");
        mList.add("陈奕迅");
        mList.add("李荣浩");
        mList.add("邓紫棋");
        mList.add("Sia");
        mList.add("Alan Walker");
    }
    private void initView() {

        context=this.getActivity();
        adapter=new VerticalAdappter(context,mList);


       LinearLayoutManager manager=new LinearLayoutManager(context);
        //recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }
}
