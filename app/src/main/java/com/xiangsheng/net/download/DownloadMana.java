package com.xiangsheng.net.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import com.xiangsheng.ControlApplication;
import com.xiangsheng.dao.model.Works;

/**
 * Created by wangliang on 2016/12/27.
 */

public class DownloadMana {
    private static DownloadMana sMDownloadMana;

    public DownloadMana() {
    }

    public static DownloadMana getInstance() {
        if (sMDownloadMana == null)
            sMDownloadMana = new DownloadMana();
        return sMDownloadMana;
    }

    public void submit(Works works) {
        String downloadUrl = "http://www.tingcd.com/" + works.getDownloadUrl();
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        //指定下载路径和下载文件名
        String downloadPath = works.getAuthorID() + "/" + works.getID() +"/";
        request.setDestinationInExternalPublicDir(downloadPath, works.getName());
        //获取下载管理器
        DownloadManager downloadMana = (DownloadManager) ControlApplication.getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadMana.enqueue(request);
    }
}
