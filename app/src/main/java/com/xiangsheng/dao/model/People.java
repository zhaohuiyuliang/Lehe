package com.xiangsheng.dao.model;

import java.io.Serializable;

/**
 * 人物
 * Created by wangliang on 2016/12/23.
 */

public class People implements Serializable {
    private int ID;
    private String name;
    private String headUrl;
    private String detailUrl;

    public People() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
