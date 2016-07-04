package com.example.msi.myapp.Utils;

import com.example.msi.myapp.module.MeiziResult;
import com.example.msi.myapp.module.MeiziResultS;

import java.util.List;

import rx.functions.Func1;

/**
 * 文 件 名: MeiziResultsMapper
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 17:39
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MeiziResultsMapper implements Func1<MeiziResultS,List<MeiziResult>> {
    private static MeiziResultsMapper INSTANCE = new MeiziResultsMapper();

    private MeiziResultsMapper(){

    }

    public static MeiziResultsMapper getINSTANCE(){
        return INSTANCE;
    }

    @Override
    public List<MeiziResult> call(MeiziResultS meiziResultS) {

        return meiziResultS.meiziResults;
    }
}
