package com.example.qqmusic.activity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.qqmusic.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class firstActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView songName, singerName;
    private ImageView diskImage;
    private TextView lyricPrev, lyricCurrent, lyricNext;
    private SeekBar musicProgress;
    private TextView currentTime, totalTime;
    private ImageButton prevBtn, playPauseBtn, nextBtn;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ObjectAnimator animator;
    private MediaPlayer player;
    private TextView tv1,tv2;
    private Button back;
    private int currentPlaying = 0; // 用作 ArrayList 下标，当前播放的歌曲
    private ArrayList<Integer> playList = new ArrayList<>();
    private FrameLayout content;
    private boolean isPausing = false, isPlaying = false; // 音乐暂停状态，音乐第一次播放之后变为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.player);

        tv1=(TextView)findViewById(R.id.tv_song_singer);
        tv2=(TextView)findViewById(R.id.tv_song_name);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();

        ft.commit();
        init();
        preparePlaylist();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isPlaying) {
                    updateTimer();
                }
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 0, 500);
    }

    void init() {
        songName = findViewById(R.id.tv_song_name);
        singerName = findViewById(R.id.tv_song_singer);
        diskImage = findViewById(R.id.iv_disk);
        lyricPrev = findViewById(R.id.tv_lyric_previous);
        lyricCurrent = findViewById(R.id.tv_lyric_current);
        lyricNext = findViewById(R.id.tv_lyric_next);
        musicProgress = findViewById(R.id.sb_progress);
        currentTime = findViewById(R.id.tv_progress_current);
        totalTime = findViewById(R.id.tv_progress_total);
        prevBtn = findViewById(R.id.btn_prev);
        playPauseBtn = findViewById(R.id.btn_play_pause);
        nextBtn = findViewById(R.id.btn_next);

        OnClickControl onClick = new OnClickControl();
        prevBtn.setOnClickListener(onClick);
        playPauseBtn.setOnClickListener(onClick);
        nextBtn.setOnClickListener(onClick);

        OnSeekBarChangeControl onSbChange = new OnSeekBarChangeControl();
        musicProgress.setOnSeekBarChangeListener(onSbChange);

        animator = ObjectAnimator.ofFloat(diskImage, "rotation", 0, 360.0F); // 初始化状态
        animator.setDuration(10000); // 转动时长，10秒
        animator.setInterpolator(new LinearInterpolator()); // 时间函数，有很多类型
        animator.setRepeatCount(-1); // 一直旋转
    }

    private void preparePlaylist() {
        Field[] fields = R.raw.class.getFields();
        for (int count = 0; count < fields.length; count++) {
            Log.i("Raw Asset", fields[count].getName());
            try {
                int resId = fields[count].getInt(fields[count]);
                playList.add(resId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareMedia() {
        if (isPlaying) {
            player.stop();
            player.reset();
        }
        player = MediaPlayer.create(getApplicationContext(), playList.get(currentPlaying));
        int musicDuration = player.getDuration();
        musicProgress.setMax(musicDuration);
        int sec = musicDuration / 1000;
        int min = sec / 60;
        sec -= min * 60;
        String musicTime = String.format("%02d:%02d", min, sec);
        totalTime.setText(musicTime);
        player.start();
    }

    private void updateTimer() {
        runOnUiThread(() -> {
            int currentMs = player.getCurrentPosition();
            int sec = currentMs / 1000;
            int min = sec / 60;
            sec -= min * 60;
            String time = String.format("%02d:%02d", min, sec);
            musicProgress.setProgress(currentMs);
            currentTime.setText(time);
        });
    }

    @Override
    public void onClick(View v) {


    }
    public void id_tab_back_onclick(View view) {
        Intent intent=new Intent(this, homeActivity.class);
        startActivity(intent);

    }

    private class OnClickControl implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_prev:
                    // 重播、上切
                    Log.i("INFO", "onClick: 重播按钮被点击！");
                    playPauseBtn.setImageResource(R.drawable.purse); // 切换成暂停键
                    animator.start();
                    if (!player.isPlaying()) {
                        currentPlaying = --currentPlaying % playList.size();
                    }
                    prepareMedia();
                    isPausing = false;
                    isPlaying = true;
                    break;
                case R.id.btn_play_pause:
                    // 开始暂停
                    Log.i("INFO", "onClick: 开始暂停按钮被点击！");
                    if (!isPausing && !isPlaying) { // 暂停状态，且从未被播放
                        // 开始播放
                        playPauseBtn.setImageResource(R.drawable.beginning); // 切换成暂停键
                        animator.start();
                        prepareMedia();
                        isPlaying = true;
                    } else if (isPausing && isPlaying) { // 暂停状态，且被播放过一次
                        // 继续播放
                        playPauseBtn.setImageResource(R.drawable.beginning); // 切换成暂停键
                        animator.resume();
                        player.start();
                    } else { // 播放状态
                        // 暂停播放
                        playPauseBtn.setImageResource(R.drawable.purse); // 切换成播放键
                        animator.pause();
                        player.pause();
                    }
                    isPausing = !isPausing; // 切换状态
                    break;
                case R.id.btn_next:
                    // 切歌
                    Log.i("INFO", "onClick: 切歌按钮被点击！");
                    playPauseBtn.setImageResource(R.drawable.beginning); // 切换成暂停键
                    currentPlaying = ++currentPlaying % playList.size();
                    prepareMedia();
                    animator.start();
                    isPausing = false;
                    isPlaying = true;
                    break;
                default:
                    // 有Bug了
                    Log.i("INFO", "onClick: 按钮点击了，但是有BUG！");
            }
        }
    }

    private class OnSeekBarChangeControl implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                player.seekTo(progress);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            player.pause();
            animator.pause();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.start();
            if (seekBar.getProgress() < 10) {
                animator.start();
            } else {
                animator.resume();
            }
        }
    }
}