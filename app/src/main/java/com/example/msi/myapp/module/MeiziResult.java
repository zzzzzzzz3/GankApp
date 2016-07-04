package com.example.msi.myapp.module;

import java.io.Serializable;

/**
 * 文 件 名: MeiziResult
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 11:50
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MeiziResult implements Serializable{
    private static final String TAG = "MeiziResult";
    public String desc;
    public String url;
    public MeiziResult(){

    }
    public MeiziResult(String url,String desc){
        this.desc= desc;
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
