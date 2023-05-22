package com.example.qqmusic.playerfragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.qqmusic.R;
import com.example.qqmusic.Server.MusicService;
import com.example.qqmusic.downLoad.thirdActivity;

import static java.lang.Integer.parseInt;

public class playerActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView diskImage;

    private static TextView name_song,name_singer;
    private static SeekBar musicProgress;
    private static TextView currentTime, totalTime;
    private ImageButton prevBtn, playPauseBtn, nextBtn;
    private MusicService.MusicControl  musicControl;
    private ObjectAnimator animator;
    String name;
    MyServiceConn conn;
    Intent intent1,intent2;
    private boolean isUnbind =false;//记录服务是否被解绑
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        intent1=getIntent();
        init();
    }

    void init() {
        name_singer=(TextView)findViewById(R.id.tv_song_singer);
        name_song=(TextView)findViewById(R.id.tv_song_name);

        diskImage = findViewById(R.id.iv_disk);

        musicProgress = findViewById(R.id.sb_progress);
        currentTime = findViewById(R.id.tv_progress_current);
        totalTime = findViewById(R.id.tv_progress_total);


        prevBtn = findViewById(R.id.btn_prev);
        playPauseBtn = findViewById(R.id.btn_play_pause);
        nextBtn = findViewById(R.id.btn_next);
        playPauseBtn.setOnClickListener(this);//暂停
        prevBtn.setOnClickListener(this);//上一首歌
        nextBtn.setOnClickListener(this);//下一首歌



        name=intent1.getStringExtra("name");
        name_song.setText(name);
        intent2=new Intent(this,MusicService.class);//创建意图对象
        conn=new MyServiceConn();//创建服务连接对象
        bindService(intent2,conn,BIND_AUTO_CREATE);//绑定服务


        //为滑动条添加事件监听
        musicProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度条改变时，会调用此方法
                if (progress==seekBar.getMax()){//当滑动条到末端时，结束动画
                    animator.pause();//停止播放动画
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {//滑动条开始滑动时调用
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {//滑动条停止滑动时调用
                //根据拖动的进度改变音乐播放进度
                int progress=seekBar.getProgress();//获取seekBar的进度
                musicControl.seekTo(progress);//改变播放进度
            }
        });
        //设置旋转的图片
        String position= intent1.getStringExtra("position");
        int i=parseInt(position);
        diskImage.setImageResource(R.drawable.round1);


        animator = ObjectAnimator.ofFloat(diskImage, "rotation", 0, 360.0F); // 初始化状态
        animator.setDuration(10000); // 转动时长，10秒
        animator.setInterpolator(new LinearInterpolator()); // 时间函数，有很多类型
        animator.setRepeatCount(-1); // 一直旋转
    }

    public static Handler handler=new Handler(){//创建消息处理器对象
        //在主线程中处理从子线程发送过来的消息

        public void handleMessage(Message msg){//设置进度时间
            Bundle bundle=msg.getData();//获取从子线程发送过来的音乐播放进度
            int duration=bundle.getInt("duration");
            int currentPosition=bundle.getInt("currentPosition");
            musicProgress.setMax(duration);
            musicProgress.setProgress(currentPosition);
            //歌曲总时长
            int minute=duration/1000/60;
            int second=duration/1000%60;
            String strMinute=null;
            String strSecond=null;
            if(minute<10){//如果歌曲的时间中的分钟小于10
                strMinute="0"+minute;//在分钟的前面加一个0
            }else{
                strMinute=minute+"";
            }
            if (second<10){//如果歌曲中的秒钟小于10
                strSecond="0"+second;//在秒钟前面加一个0
            }else{
                strSecond=second+"";
            }
            totalTime.setText(strMinute+":"+strSecond);
            //歌曲当前播放时长
            minute=currentPosition/1000/60;
            second=currentPosition/1000%60;
            if(minute<10){//如果歌曲的时间中的分钟小于10
                strMinute="0"+minute;//在分钟的前面加一个0
            }else{
                strMinute=minute+" ";
            }
            if (second<10){//如果歌曲中的秒钟小于10
                strSecond="0"+second;//在秒钟前面加一个0
            }else{
                strSecond=second+" ";
            }
            currentTime.setText(strMinute+":"+strSecond);
        }
    };
    class MyServiceConn implements ServiceConnection {//用于实现连接服务
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            musicControl=(MusicService.MusicControl) service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name){

        }
    }
    private void unbind(boolean isUnbind){
        if(!isUnbind){//判断服务是否被解绑
            musicControl.pausePlay();//暂停播放音乐
            unbindService(conn);//解绑服务
        }
    }




    public void id_tab_back_onclick(View view) {
        Intent intent=new Intent(this, singerActivity.class);
        startActivity(intent);

    }



        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_play_pause:
                    String begin=intent1.getStringExtra("play");
                    if(begin.equals("0")) {//点击后停止播放
                        playPauseBtn.setImageResource(R.drawable.purse);
                        musicControl.pausePlay();
                        animator.pause();
                        intent1.putExtra("play","2");
                    }
                    else if(begin.equals("2")){//点击播放后继续播放
                        playPauseBtn.setImageResource(R.drawable.beginning);
                        musicControl.continuePlay();
                        animator.start();
                        intent1.putExtra("play","0");
                    }
                    else
                    {  //第一次播放
                        playPauseBtn.setImageResource(R.drawable.beginning);
                        String position=intent1.getStringExtra("position");
                        int i=parseInt(position);
                        musicControl.play(i);//播放音乐
                        animator.start();//图片开始旋转
                        intent1.putExtra("play","0");

                    }
                    break;
                case R.id.btn_prev:
                    // 上一首
                    String position1=intent1.getStringExtra("position");
                    int j=parseInt(position1)-1;
                    if(j<0){j=2;}
                    name_song.setText(singerActivity.name[j]);//设置姓名
                    diskImage.setImageResource(R.drawable.round1);//切换旋转的图片
                    playPauseBtn.setImageResource(R.drawable.beginning);
                    intent1.putExtra("play","0");
                    intent1.putExtra("position",String.valueOf(j));
                    musicControl.play(j);//播放音乐
                    animator.start();//图片开始旋转
                    break;
                case R.id.btn_next:
                    // 切歌
                    String position2=intent1.getStringExtra("position");
                    int k=parseInt(position2)+1;
                    if(k>4){k=0;}
                    name_song.setText(singerActivity.name[k]);//设置姓名
                    diskImage.setImageResource(R.drawable.round1);
                    playPauseBtn.setImageResource(R.drawable.beginning);
                    intent1.putExtra("play","0");
                    intent1.putExtra("position",String.valueOf(k));
                    musicControl.play(k);//播放音乐
                    animator.start();//图片开始旋转
                    break;

            }
        }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbind(isUnbind);//解绑服务
    }




    }
