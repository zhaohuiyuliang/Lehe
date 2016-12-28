package com.xiangsheng.dao.model;

import java.io.Serializable;

/**
 * 相声作品
 * Created by wangliang on 2016/12/26.
 */

public class WorksName implements Serializable {
    private int ID;
    private String name;

    private String detailUrl;

    private int authorID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

}
