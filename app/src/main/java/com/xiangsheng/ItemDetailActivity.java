package com.xiangsheng;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xiangsheng.dao.model.Works;
import com.xiangsheng.tool.MusicPlay;

import static com.xiangsheng.ItemDetailActivity.PlayStatus.PAUSE;
import static com.xiangsheng.ItemDetailActivity.PlayStatus.PLAYING;
import static com.xiangsheng.ItemDetailActivity.PlayStatus.STOP;
import static com.xiangsheng.ItemListWorksFragment.ARG_ITEM_ID;

/**
 * Created by wangliang on 2016/12/26.
 */

public class ItemDetailActivity extends AppCompatActivity {
    private TextView tv_name;
    private TextView tv_size;
    private ImageView img_video;
    private ImageView img_re_play;
    private SeekBar seekbar;
    private TextView startTime;
    private TextView endTime;
    private MusicPlay mMusicPlay;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    int progress = mMusicPlay.media.getCurrentPosition();  //当前播放毫秒
                    int allTime = mMusicPlay.media.getDuration();      //总毫秒
                    seekbar.setMax(allTime);//设置进度条
                    startTime.setText(getTimeFormat(progress));
                    endTime.setText(getTimeFormat(allTime));
                    seekbar.setProgress(progress);
                    break;
                }
                case 2: {

                    break;
                }
            }
        }
    };
    private PlayStatus mPlayStatus = STOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        mMusicPlay = MusicPlay.getInstance();
        Intent intent = getIntent();
        final Works mItem = (Works) intent.getSerializableExtra(ARG_ITEM_ID);
        tv_name = findView(R.id.tv_name);
        seekbar = findView(R.id.seekbar);
        startTime = findView(R.id.startTime);
        endTime = findView(R.id.endTime);
        tv_size = findView(R.id.tv_size);
        img_video = findView(R.id.img_video);
        img_re_play = findView(R.id.img_re_play);
        tv_name.setText(mItem.getName());
        tv_size.setText(mItem.getSize());
        img_re_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayStatus = PLAYING;
                mMusicPlay.play(mItem);
                img_video.setImageResource(R.drawable.icon_pause);
            }
        });
        img_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (getPlayStatus()) {
                    case PLAYING: {
                        img_video.setImageResource(R.drawable.icon_video);
                        mMusicPlay.pause();
                        mPlayStatus = PAUSE;
                        break;
                    }
                    case STOP: {
                        mPlayStatus = PLAYING;
                        mMusicPlay.play(mItem);
                        img_video.setImageResource(R.drawable.icon_video);
                        break;
                    }
                    case PAUSE: {
                        mMusicPlay.start();
                        mPlayStatus = PLAYING;
                        img_video.setImageResource(R.drawable.icon_pause);
                        break;
                    }
                }
            }
        });
    }

    protected <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
    }

    PlayStatus getPlayStatus() {
        return mPlayStatus;
    }

    public enum PlayStatus {
        PLAYING, PAUSE, STOP
    }

    public String getTimeFormat(int time) {
        String timeStr = "00:00:00";
        int s = time / 1000;   //秒
        int h = s / 3600;    //求整数部分 ，小时
        int r = s % 3600;    //求余数
        int m = 0;
        if (r > 0) {
            m = r / 60;    //分
            r = r % 60;    //求分后的余数，即为秒
        }

        if (h < 10) {
            timeStr = "0" + h;
        } else {
            timeStr = "" + h;
        }

        if (m < 10) {
            timeStr = timeStr + ":" + "0" + m;
        } else {
            timeStr = timeStr + ":" + m;
        }

        if (r < 10) {
            timeStr = timeStr + ":" + "0" + r;
        } else {
            timeStr = timeStr + ":" + r;
        }

        return timeStr;
    }

}
