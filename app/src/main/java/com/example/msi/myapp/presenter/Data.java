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
    private List<MeiziResult> data;
    public Data(){

    }

    public List<MeiziResult> getData(){
        NetworkUtil.getMeiziApi().getMeizi(20,1)
                .map(MeiziResultsMapper.getINSTANCE())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MeiziResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<MeiziResult> meiziResults) {
                        data = meiziResults;
                    }
                });
        return data;
    }
}
