package com.example.msi.myapp.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 文 件 名: MeiziResultS
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 17:07
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class Results<T> {
    public boolean error;
    @SerializedName("results")
    public T results;
}
