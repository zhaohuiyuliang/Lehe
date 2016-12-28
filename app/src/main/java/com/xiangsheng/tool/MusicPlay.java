package com.xiangsheng.tool;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.xiangsheng.ControlApplication;
import com.xiangsheng.dao.model.Works;
import com.xiangsheng.net.download.DownloadMana;

import java.io.File;
import java.util.TimerTask;

/**
 * 音乐播放器
 * Created by wangliang on 2016/12/27.
 */

public class MusicPlay {

    private static MusicPlay mMusicPlay;
    public MediaPlayer media = null;
    private Context mContext;

    private MusicPlay(Context mContext) {
        this.mContext = mContext;
        this.media = new MediaPlayer();
        this.media.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置播放类型
        this.media.setOnCompletionListener(new MediaCompletionListener());
        this.media.setOnErrorListener(new MediaErrorListener());
    }

    public static MusicPlay getInstance() {
        if (mMusicPlay == null)
            mMusicPlay = new MusicPlay(ControlApplication.getApplication());
        return mMusicPlay;
    }


    //从头开始播放
    private void restart() {
        if (media != null) {
            media.seekTo(0);
        }
    }

    public void start() {
        if (media == null) {
            return;
        }
        media.start();
    }

    //暂停获取继续播放
    public void pause() {
        if (media == null) {
            return;
        }
        if (media.isPlaying()) {
            media.pause();
        }
    }

    //开始播放
    public void play(Works works) {
        if (TextUtils.isEmpty(works.getDownloadUrl())) {
            Toast.makeText(mContext, "请选择播放文件", Toast.LENGTH_SHORT);
            return;
        }
        String path = "/mnt/sdcard/" + works.getAuthorID() + "/" + works.getID() + "/" + works.getName();
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            try {
                this.media.setDataSource(path);
                this.media.prepare();
                this.media.start();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, "播放文件错误", Toast.LENGTH_SHORT).show();
            }

        } else {
            DownloadMana.getInstance().submit(works);
            Toast.makeText(mContext, "正在下载文件，留意通知栏", Toast.LENGTH_SHORT).show();
        }
    }


    private class MyTask extends TimerTask {

        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
        }
    }

    //播放时候错误回调
    private class MediaErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
            MusicPlay.this.media.stop();
            MusicPlay.this.media.release();
            MusicPlay.this.media = null;
            return false;
        }
    }

    //播放完成事件
    private class MediaCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer media) {
            MusicPlay.this.media.stop();
            MusicPlay.this.media.release();
            MusicPlay.this.media = null;
        }

    }
}
