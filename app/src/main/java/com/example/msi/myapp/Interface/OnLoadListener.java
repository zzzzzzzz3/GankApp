package com.example.msi.myapp.Interface;

import java.io.File;

/**
 * 文 件 名: OnLoadListener
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/7 11:48
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public interface OnLoadListener {
    void onLoad();
    void compled(File file);
    void error();
}
