package com.xiangsheng.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.xiangsheng.dao.model.People;
import com.xiangsheng.dao.model.WorksName;
import com.xiangsheng.dao.model.WorksSize;

/**
 * Created by wangliang on 6/13/16.
 */
public class Controller {
    private static Handler mMessageHandler;
    private final int msg_people_profile_task = 0;
    private final int msg_works_name_task = 1;
    private final int msg_works_size_task = 3;
    private final int msg_works_task = 4;
    private final int msg_letter_spelling_task = 5;
    private String TAG = "Controller";
    private HandlerThread mHandlerThread;
    private volatile Looper mServiceLooper;

    public Controller() {
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread("Controller Message Processing Thread");
            mHandlerThread.start();
        }
        if (mHandlerThread != null && !mHandlerThread.isAlive()) {
            mHandlerThread.start();
        }
        mServiceLooper = mHandlerThread.getLooper();
        mMessageHandler = new Handler(mServiceLooper) {
            @Override
            public void handleMessage(Message msg) {
                Controller.this.handleMessage(msg);
            }
        };
    }

    private void handleMessage(Message msg) {
        LoadDataTask task = null;
        switch (msg.what) {
            case msg_people_profile_task://请求相声专辑
                task = new TaskPeopleProfile();
                break;
            case msg_works_name_task:
                task = new TaskWorksName(msg);
                break;
            case msg_works_size_task:
                task = new TaskWorksSize(msg);
                break;
            case msg_works_task:
                task = new TaskWorks(msg);
                break;
            case msg_letter_spelling_task:
//                task = new TaskSpellingOfLetter();
                break;
            default:
                break;
        }
        if (task != null) {
            task.run();
        }
    }

    /**
     * 请求人物属性
     */
    public void requestPeopleProfile() {
        Message msg = Message.obtain(mMessageHandler, msg_people_profile_task);
        mMessageHandler.sendMessage(msg);
    }

    /**
     * 请求作品名称
     *
     * @param people
     */
    public void requestWorksName(People people) {
        Message msg = Message.obtain(mMessageHandler, msg_works_name_task);
        Bundle bundle = new Bundle();
        bundle.putSerializable("people", people);
        msg.setData(bundle);
        mMessageHandler.sendMessage(msg);
    }

    /**
     * 请求作品大小
     *
     * @param worksName
     */
    public void requestWorksSize(WorksName worksName) {
        Message msg = Message.obtain(mMessageHandler, msg_works_size_task);
        Bundle bundle = new Bundle();
        bundle.putSerializable("worksName", worksName);
        msg.setData(bundle);
        mMessageHandler.sendMessage(msg);
    }


    /**
     * 请求作品下载地址
     *
     * @param worksSize
     */
    public void requestWorks(WorksSize worksSize) {
        Message msg = Message.obtain(mMessageHandler, msg_works_task);
        Bundle bundle = new Bundle();
        bundle.putSerializable("WorksSize", worksSize);
        msg.setData(bundle);
        mMessageHandler.sendMessage(msg);
    }

}
