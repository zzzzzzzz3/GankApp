package com.example.msi.myapp.module;

import java.io.Serializable;

/**
 * 文 件 名: News
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/6/30 16:03
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：消息的模型
 */
public class News implements Serializable {
    private String title;
    private String desc;
    private int photoId;

    public News(String title,String dec,int photoId){
        this.title = title;
        this.desc = dec;
        this.photoId = photoId;
    }

    public String getDec() {
        return desc;
    }

    public void setDec(String dec) {
        this.desc = dec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

}
