package com.example.msi.myapp.Utils;

import com.example.msi.myapp.module.AndroidResult;
import com.example.msi.myapp.module.Results;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class AndroidResultsMapper implements Func1<Results<List<AndroidResult>>, List<AndroidResult>> {
    private static final AndroidResultsMapper INSTANCE = new AndroidResultsMapper();

    private AndroidResultsMapper(){}

    public static AndroidResultsMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public List<AndroidResult> call(Results<List<AndroidResult>> listResults) {

        return listResults.results;
    }
}
