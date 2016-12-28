package com.xiangsheng.dao.model;

import java.io.Serializable;

/**
 * Created by wangliang on 2016/12/26.
 */

public class Works implements Serializable {
    private int ID;
    private String name;
    private String authorID;
    private String downloadUrl;
    private String videoUrl;
    private String size;
    private int definition;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDefinition() {
        return definition;
    }

    public void setDefinition(int definition) {
        this.definition = definition;
    }
}
