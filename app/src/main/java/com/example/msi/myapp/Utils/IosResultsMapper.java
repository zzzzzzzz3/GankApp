package com.example.msi.myapp.Utils;

import com.example.msi.myapp.module.AndroidResult;
import com.example.msi.myapp.module.IosResult;
import com.example.msi.myapp.module.Results;

import java.util.List;

import rx.functions.Func1;

/**
 * 文 件 名: AndroidResultsMapper
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/5 20:32
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class IosResultsMapper implements Func1<Results<List<IosResult>>, List<IosResult>> {
    private static final IosResultsMapper INSTANCE = new IosResultsMapper();

    private IosResultsMapper(){}

    public static IosResultsMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public List<IosResult> call(Results<List<IosResult>> listResults) {

        return listResults.results;
    }
}
