package com.example.msi.myapp.module;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 文 件 名: AndroidResult
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/5 19:38
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class AndroidResult implements Serializable{

    @SerializedName("_id")
    private String id;
    private String who;
    private String desc;
    private String url;
    private String createdAt;

    public String getId() {
        return id;
    }

    public String getWho() {
        return who;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
