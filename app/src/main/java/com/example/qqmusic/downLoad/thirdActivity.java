package com.example.qqmusic.downLoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.app.ListActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qqmusic.R;

import static android.widget.Toast.LENGTH_LONG;


public class thirdActivity extends ListActivity {

    // 固定下载的资源路径，这里可以设置网络上的地址
    private static final String URL = "http://sc1.111ttt.cn/2017/1/05/09/298092035545.mp3";
    // 固定存放下载的音乐的路径：SD卡目录下
    private static final String SD_PATH = "/mnt/sdcard/";
    // 存放各个下载器
    private Map<String, Downloader> downloaders = new HashMap<String, Downloader>();
    // 存放与下载器对应的进度条
    private Map<String, ProgressBar> ProgressBars = new HashMap<String, ProgressBar>();
    /**
     * 利用消息处理机制适时更新进度条
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String url = (String) msg.obj;
                int length = msg.arg1;
                ProgressBar bar = ProgressBars.get(url);
                if (bar != null) {
                    // 设置进度条按读取的length长度更新
                    bar.incrementProgressBy(length);
                    if (bar.getProgress() == bar.getMax()) {
                        Toast.makeText(thirdActivity.this, "下载完成！", LENGTH_LONG).show();
                        // 下载完成后清除进度条并将map中的数据清空
                        LinearLayout layout = (LinearLayout) bar.getParent();
                        layout.removeView(bar);
                        ProgressBars.remove(url);
                        downloaders.get(url).delete(url);
                        downloaders.get(url).reset();
                        downloaders.remove(url);
                    }
                }
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.down);
        showListView();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }
    // 显示listView，这里可以随便添加音乐
    private void showListView() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "后来.mp3");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "不再见.mp3");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "光年之外.mp3");
        data.add(map);
        map = new HashMap<String, String>();
        map.put("name", "喜欢你.mp3");
        data.add(map);
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_list, new String[] { "name" },
                new int[] { R.id.tv_resouce_name });
        setListAdapter(adapter);
    }
    /**
     * 响应开始下载按钮的点击事件
     */
    public void startDownload(View v) {
        // 得到textView的内容
        LinearLayout layout = (LinearLayout) v.getParent();
        String musicName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();
        String urlstr = URL + musicName;
        String localfile = SD_PATH + musicName;
        //设置下载线程数为4，这里是我为了方便随便固定的
        int threadcount = 4;
        // 初始化一个downloader下载器
        Downloader downloader = downloaders.get(urlstr);
        if (downloader == null) {
            downloader = new Downloader(urlstr, localfile, threadcount, this, mHandler);
            downloaders.put(urlstr, downloader);
        }
        if (downloader.isdownloading())
            return;
        // 得到下载信息类的个数组成集合
        LoadInfo loadInfo = downloader.getDownloaderInfors();
        // 显示进度条
        showProgress(loadInfo, urlstr, v);
        // 调用方法开始下载
        downloader.download();
    }

    /**
     * 显示进度条
     */
    private void showProgress(LoadInfo loadInfo, String url, View v) {
        ProgressBar bar = ProgressBars.get(url);
        if (bar == null) {
            bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            bar.setMax(loadInfo.getFileSize());
            bar.setProgress(loadInfo.getComplete());
            ProgressBars.put(url, bar);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 5);
            ((LinearLayout) ((LinearLayout) v.getParent()).getParent()).addView(bar, params);
        }
    }
    /**
     * 响应暂停下载按钮的点击事件
     */
    public void pauseDownload(View v) {
        LinearLayout layout = (LinearLayout) v.getParent();
        String musicName = ((TextView) layout.findViewById(R.id.tv_resouce_name)).getText().toString();
        String urlstr = URL + musicName;
        downloaders.get(urlstr).pause();
    }
}