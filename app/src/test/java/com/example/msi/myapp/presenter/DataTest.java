package com.example.msi.myapp.presenter;

import com.example.msi.myapp.Utils.MeiziResultsMapper;
import com.example.msi.myapp.Utils.NetworkUtil;
import com.example.msi.myapp.module.MeiziResult;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * 文 件 名: DataTest
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 21:11
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class DataTest {
    private List<MeiziResult> data;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetData() throws Exception {
        NetworkUtil.getMeiziApi().getMeizi(20,1)
                .map(MeiziResultsMapper.getINSTANCE())
                .subscribeOn(Schedulers.io())
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
                        System.out.print(data.get(0).getUrl());
                    }
                });
    }

}