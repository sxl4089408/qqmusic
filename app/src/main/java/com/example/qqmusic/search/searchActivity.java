package com.example.qqmusic.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqmusic.R;


public class searchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView sView;
    private ListView lView;
    //自动完成的列表
    private final String[] mStrings = {"反语","All Falls Down","Lemon","Lemon   米津玄师",
            "打上花火   米津玄师",
            "跃进人海拥抱你  张杰",
            "她和她和她  于真",
            "画心  张靓颖",
            "可惜没如果  林俊杰",
            "尘埃  林小珂",
            "后来遇见他  胡66",
            "最来了不起的你  段奥娟",
            "巅峰之上  毛不易"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sView = (SearchView)findViewById(R.id.searchView_id);
        lView = (ListView)findViewById(R.id.listView_id);
        lView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,mStrings));
        lView.setTextFilterEnabled(true);

        //设置该searchView默认是否自动缩小为图标
        sView.setIconified(false);
        //为该设searchView组件设置事件监听器
        sView.setOnQueryTextListener(this);
        //设置该组件显示搜索按钮
        sView.setSubmitButtonEnabled(true);
        //设置该组件默认显示的提示文本
        sView.setQueryHint("查找");
    }
    // 用户输入字符时激发的方法
    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO 自动生成的方法存根
        if(TextUtils.isEmpty(newText)) {
            //清除listview的过滤
            lView.clearTextFilter();
        }
        else {
            //用用户输入的内容对listview的列表项进行过滤
            lView.setFilterText(newText);
        }
        return true;
    }

    //用户单击搜索按钮时激发的方法
    @Override
    public boolean onQueryTextSubmit(String qurery) {
        //这里仅仅是模拟，实际中应该在该方法中进行查询，然后将结果得到
        Toast.makeText(searchActivity.this, "你选择的是："+qurery, 0).show();
        return false;
    }
}