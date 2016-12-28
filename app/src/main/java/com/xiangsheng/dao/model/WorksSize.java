package com.xiangsheng.dao.model;

import java.io.Serializable;

/**
 * 作品大小
 * 作品类型
 * Created by wangliang on 2016/12/26.
 */

public class WorksSize implements Serializable {
    private int ID;
    private String size;
    private String fileLayout;
    private String detailUrl;

    public String getFileLayout() {
        return fileLayout;
    }

    public void setFileLayout(String fileLayout) {
        this.fileLayout = fileLayout;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

}
