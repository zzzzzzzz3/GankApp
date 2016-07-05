package com.example.msi.myapp.presenter;

import com.example.msi.myapp.Utils.MeiziResultsMapper;
import com.example.msi.myapp.Utils.NetworkUtil;
import com.example.msi.myapp.module.MeiziResult;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 文 件 名: Data
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 19:53
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class Data {
    private static Data INSTANCE = new Data();
    private Data(){

    }
    public static Data getINSTANCE(){
        return INSTANCE;
    }
    public void getData(Subscriber<List<MeiziResult>> subscriber,int count,int page){
        NetworkUtil.getINSTANCE().getMeiziApi().getMeizi(count,page)
                .map(MeiziResultsMapper.getINSTANCE())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
