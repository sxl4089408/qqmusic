package com.example.qqmusic.playerfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqmusic.R;
import com.example.qqmusic.activity.homeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class singerActivity extends AppCompatActivity {
    public static String[] name={
            "反语  鹿乃",
            "All Falls Down    Alan Walker",
            "death bed   Powfu",
            "Lemon   米津玄师",
            "打上花火   米津玄师",
            "跃进人海拥抱你  张杰",
            "她和她和她  于真",
            "画心  张靓颖",
            "可惜没如果  林俊杰",
            "尘埃  林小珂",
            "后来遇见他  胡66",
            "最来了不起的你  段奥娟",
            "巅峰之上  毛不易"

    };
    public static int[] icons={
            R.mipmap.singer5,
            R.mipmap.singer1,
            R.mipmap.singer2,
            R.mipmap.singer3,
            R.mipmap.singer3,
            R.mipmap.singer6,
            R.mipmap.singer7,
            R.mipmap.singer8,
            R.mipmap.singer9,
            R.mipmap.singer10,
            R.mipmap.singer11,
            R.mipmap.singer12,
            R.mipmap.singer13,

    };
    private String play="0";//默认为未播放
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singer_activity);
        //初始化每日推荐列表
        ListView RS_LV =findViewById(R.id.RSS_LV);
        MyAdapter adapter=new MyAdapter(this,getdata());
        RS_LV.setAdapter(adapter);
        setListViewHeightBasedOnChildren(RS_LV);
        RS_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                play="1";
                Intent intent=new Intent(singerActivity.this, playerActivity.class);//创建Intent对象，启动check
                //将数据存入Intent对象
                intent.putExtra("name",name[position]);
                intent.putExtra("position",String.valueOf(position));
                intent.putExtra("play",play);//将播放状态发给播放器
                startActivity(intent);//进行跳转
            }
        });
    }
    //重新将listview布局
    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
    private List<Map<String,Object>> getdata(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i = 0;i<name.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("image",icons[i]);
            map.put("name", name[i]);
            list.add(map);
        }
        return list;
    }
    //ViewHolder静态类
    public final class ViewHolder{
        public ImageView iv;
        public TextView tv;
    }
    //适配器类
    public void id_bt_back_onclick(View view) {
        Intent intent=new Intent(this, homeActivity.class);
        startActivity(intent);



    }
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Map<String, Object>> mData;
        public MyAdapter(Context context, List<Map<String, Object>> mData){
            //根据context上下文加载布局
            this.inflater = LayoutInflater.from(context);
            //将传入的数据保存在mData中
            this.mData=mData;
        }
        @Override
        public int getCount(){return  name.length;}
        @Override
        public Object getItem(int position){return name[position];}
        @Override
        public long getItemId(int position){return position;}

        @Override
        public View getView(int position ,View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null) {
                //自定义的一个类用来缓存convertview
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = inflater.inflate(R.layout.musicitem, null);
                holder.tv = convertView.findViewById(R.id.music_name);
                holder.iv = convertView.findViewById(R.id.music_iv);
                convertView.setTag(holder);//将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tv.setText((String)mData.get(position).get("name"));
            holder.iv.setImageResource((Integer)mData.get(position).get("image"));
            return convertView;
        }
    }
}