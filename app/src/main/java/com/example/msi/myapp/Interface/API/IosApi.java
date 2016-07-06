package com.example.msi.myapp.Interface.API;

import com.example.msi.myapp.module.AndroidResult;
import com.example.msi.myapp.module.IosResult;
import com.example.msi.myapp.module.Results;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 文 件 名: AndroiddApi
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/5 20:22
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public interface IosApi {
    @GET("data/iOS/{number}/{page}")
    Observable<Results<List<IosResult>>> getIosResults(@Path("number") int count, @Path("page") int page);
}
