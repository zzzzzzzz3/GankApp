package com.example.msi.myapp.Interface.API;

import com.example.msi.myapp.module.MeiziResultS;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 文 件 名: MeiziApi
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 17:11
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public interface MeiziApi {
    @GET("\"data/福利/{number}/{page}\"")
    Observable<MeiziResultS> getMeizi(@Path("number") int number, @Path("page") int page);
}
